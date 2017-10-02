package com.utilsgl.secreen.gui;

import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.WGL.wglCreateContext;
import static org.lwjgl.opengl.WGL.wglDeleteContext;
import static org.lwjgl.opengl.WGL.wglMakeCurrent;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_DrawingSurface_FreeDrawingSurfaceInfo;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_DrawingSurface_GetDrawingSurfaceInfo;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_DrawingSurface_Lock;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_DrawingSurface_Unlock;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_FreeDrawingSurface;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_GetAWT;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_GetDrawingSurface;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_LOCK_ERROR;
import static org.lwjgl.system.jawt.JAWTFunctions.JAWT_VERSION_1_4;
import static org.lwjgl.system.windows.GDI32.ChoosePixelFormat;
import static org.lwjgl.system.windows.GDI32.DescribePixelFormat;
import static org.lwjgl.system.windows.GDI32.GetPixelFormat;
import static org.lwjgl.system.windows.GDI32.PFD_DOUBLEBUFFER;
import static org.lwjgl.system.windows.GDI32.PFD_DRAW_TO_WINDOW;
import static org.lwjgl.system.windows.GDI32.PFD_MAIN_PLANE;
import static org.lwjgl.system.windows.GDI32.PFD_SUPPORT_OPENGL;
import static org.lwjgl.system.windows.GDI32.PFD_TYPE_RGBA;
import static org.lwjgl.system.windows.GDI32.SetPixelFormat;
import static org.lwjgl.system.windows.GDI32.SwapBuffers;

import java.awt.Canvas;
import java.awt.Graphics;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.jawt.JAWT;
import org.lwjgl.system.jawt.JAWTDrawingSurface;
import org.lwjgl.system.jawt.JAWTDrawingSurfaceInfo;
import org.lwjgl.system.jawt.JAWTWin32DrawingSurfaceInfo;
import org.lwjgl.system.windows.PIXELFORMATDESCRIPTOR;
import org.lwjgl.system.windows.WinBase;

@SuppressWarnings("serial")
public class GLCanvas extends Canvas {
	
	private final JAWT awt;
	private long hglrc;
	private GLCapabilities caps;

	public GLCanvas() {
		awt = JAWT.calloc();
		awt.version(JAWT_VERSION_1_4);
		if ( !JAWT_GetAWT(awt) )
			throw new RuntimeException("GetAWT failed");
	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		// Get the drawing surface
		JAWTDrawingSurface ds = JAWT_GetDrawingSurface(awt.GetDrawingSurface(), this);
		if(ds == null)
			throw new RuntimeException("awt->GetDrawingSurface() failed");

		try {
			// Lock the drawing surface
			int lock = JAWT_DrawingSurface_Lock(ds.Lock(), ds);
			if((lock & JAWT_LOCK_ERROR) != 0)
				throw new RuntimeException("ds->Lock() failed");

			try {
				// Get the drawing surface info
				JAWTDrawingSurfaceInfo dsi = JAWT_DrawingSurface_GetDrawingSurfaceInfo(ds.GetDrawingSurfaceInfo(), ds);

				try {
					// Get the platform-specific drawing info
					JAWTWin32DrawingSurfaceInfo dsi_win = JAWTWin32DrawingSurfaceInfo.create(dsi.platformInfo());
					long hdc = dsi_win.hdc();
					if(hdc != NULL) {
						if (hglrc == NULL) {
							createContext(dsi_win);
//							gears.initGLState();	//TODO evento create
						} else {
							if (!wglMakeCurrent(hdc, hglrc))
								throw new IllegalStateException("wglMakeCurrent() failed");

							GL.setCapabilities(caps);
						}

						glViewport(0, 0, getWidth(), getHeight());
//						gears.renderLoop();	//TODO Evento render
						SwapBuffers(hdc);

						wglMakeCurrent(NULL, NULL);
						GL.setCapabilities(null);
					}
				} finally {
					// Free the drawing surface info
					JAWT_DrawingSurface_FreeDrawingSurfaceInfo(ds.FreeDrawingSurfaceInfo(), dsi);
				}
			} finally {
				// Unlock the drawing surface
				JAWT_DrawingSurface_Unlock(ds.Unlock(), ds);
			}
		} finally {
			// Free the drawing surface
			JAWT_FreeDrawingSurface(awt.FreeDrawingSurface(), ds);
		}

		repaint();
	}

	// Simplest possible context creation.
	private void createContext(JAWTWin32DrawingSurfaceInfo dsi_win) {
		long hdc = dsi_win.hdc();

		try (
			PIXELFORMATDESCRIPTOR pfd = PIXELFORMATDESCRIPTOR.calloc()
				.nSize((byte)PIXELFORMATDESCRIPTOR.SIZEOF)
				.nVersion((short)1)
				.dwFlags(PFD_SUPPORT_OPENGL | PFD_DRAW_TO_WINDOW | PFD_DOUBLEBUFFER)
				.iPixelType(PFD_TYPE_RGBA)
				.cColorBits((byte)32)
				.cAlphaBits((byte)8)
				.cDepthBits((byte)24)
				.iLayerType(PFD_MAIN_PLANE)
		) {
			int pixelFormat = GetPixelFormat(hdc);
			if ( pixelFormat != 0 ) {
				if ( DescribePixelFormat(hdc, pixelFormat, pfd) == 0 )
					throw new IllegalStateException("DescribePixelFormat() failed: " + WinBase.getLastError());
			} else {
				pixelFormat = ChoosePixelFormat(hdc, pfd);
				if ( pixelFormat < 1 )
					throw new IllegalStateException("ChoosePixelFormat() failed: " + WinBase.getLastError());

				if ( !SetPixelFormat(hdc, pixelFormat, null) )
					throw new IllegalStateException("SetPixelFormat() failed: " + WinBase.getLastError());
			}
		}

		hglrc = wglCreateContext(hdc);

		if ( hglrc == NULL )
			throw new IllegalStateException("wglCreateContext() failed");

		if ( !wglMakeCurrent(hdc, hglrc) )
			throw new IllegalStateException("wglMakeCurrent() failed");

		caps = GL.createCapabilities();
	}

	public void destroy() {
		awt.free();

		if ( hglrc != NULL )
			wglDeleteContext(hglrc);
	}

}
