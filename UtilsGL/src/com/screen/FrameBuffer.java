package com.screen;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.exception.FailCreateWindowException;

public class FrameBuffer implements Renderable {
	
	private int w;
	private int h;
	private long win;
	private Context context;
	
	
	//constructor

	public FrameBuffer(int w, int h, Context context) {
		this.w = w;
		this.h = h;
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		
		context.createContext();
		this.context = context;
		
		win = glfwCreateWindow(w, h, "hidden", NULL, NULL);
		
		if(win == NULL)
			throw new FailCreateWindowException();
		
//		applyEvent();	//TODO eventos de buffer
	}
	
	public FrameBuffer(int w, int h, boolean alphaChannel) {
		this.w = w;
		this.h = h;
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		
		Context context = new Context().setDephtBits(alphaChannel ? 32 : 24);
		context.createContext();
		this.context = context;
		
		win = glfwCreateWindow(w, h, "hidden", NULL, NULL);
		
		if(win == NULL)
			throw new FailCreateWindowException();
		
//		applyEvent();	//TODO eventos de buffer
	}
	
	
	//consultores
	
	public Context getContext() {
		return context;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}

	
	//contextable
	
	public void swapBuffers() {
		glfwSwapBuffers(win);
	}
	
	public void swapInterval(int i) {
		glfwSwapInterval(i);
	}
	
	public boolean makeCurrentContext() {
		glfwMakeContextCurrent(win);
		
		try {
			GL.getCapabilities();
		} catch(Exception e) {
			GL.setCapabilities(GL.createCapabilities());
		}
		
		return true;
	}
	
	public ByteBuffer getAsByteBuffer() {
		
		GL11.glReadBuffer(GL11.GL_FRONT);
		
		int width = getWidth();
		int height = getHeight();
		int bpp = context.getDephtBits()/8;
		int format = bpp == 4 ? GL11.GL_RGBA : GL11.GL_RGB;
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, format, GL11.GL_UNSIGNED_BYTE, buffer);
		
		return buffer;
	}
	
	
	//destroy
	
	public void destroy() {
		glfwDestroyWindow(win);
		win = NULL;
	}
}
