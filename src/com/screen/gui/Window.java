package com.screen.gui;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_FLOATING;
import static org.lwjgl.glfw.GLFW.GLFW_FOCUSED;
import static org.lwjgl.glfw.GLFW.GLFW_ICONIFIED;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickAxes;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickButtons;
import static org.lwjgl.glfw.GLFW.glfwGetJoystickName;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwIconifyWindow;
import static org.lwjgl.glfw.GLFW.glfwJoystickPresent;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwRestoreWindow;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetDropCallback;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowCloseCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowRefreshCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWDropCallbackI;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowCloseCallback;
import org.lwjgl.glfw.GLFWWindowCloseCallbackI;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowFocusCallbackI;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallbackI;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowPosCallbackI;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.event.WindowEvent;
import com.exception.FailCreateWindowException;
import com.input.Cursor;
import com.input.Joystick;
import com.input.Key;
import com.input.SpecialKey;
import com.screen.Context;
import com.screen.Renderable;
import com.screen.Monitor;
import com.screen.VideoMode;

public class Window implements Renderable
{
	private long win;
	private Context context;
	
	private List<WindowEvent> events;
	private boolean closed;
	
	private boolean hidden;
	
	
	//constructores
	public Window()
	{
		context = new Context();
		events = new LinkedList<WindowEvent>();
		closed = false;
		hidden = false;
		
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
	}
	
	public Window(Context contx)
	{
		this.context = contx;
		events = new LinkedList<WindowEvent>();
		closed = false;
		hidden = false;
		
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
	}
	
	
	//create
	public void create(int x, int y, String title)
	{
		create(x, y, title, true);
	}
	
	public void create(int x, int y, String title, boolean show)
	{
		context.createContext();
		
		win = glfwCreateWindow(x, y, title, NULL, NULL);
		
		if(win == NULL)
			throw new FailCreateWindowException();
		
		applyEvent();
		
		if(!hidden)
			this.show(show);
	}
	
	public void createFullScreen(Monitor monitor, String title)
	{
		VideoMode vm = monitor.getVideoMode();
		context.createContext();

		this.setDecorated(false);
		win = glfwCreateWindow(vm.getWidth(), vm.getHeight(), title, NULL, NULL);
		
		if(win == NULL)
			throw new FailCreateWindowException();
		
		applyEvent();
		
		if(!hidden)
			this.show(true);
	}
	
	public void createFullScreen(Monitor monitor, int x, int y, String title)
	{
		VideoMode vm = monitor.getVideoMode();
		
		monitor.createMonitor(vm);
		context.createContext();
		
		win = glfwCreateWindow(x, y, title, monitor.get(), NULL);
		
		if(win == NULL)
			throw new FailCreateWindowException();
		
		applyEvent();
		
		if(!hidden)
			this.show(true);
	}
	
	public void createFullScreen(int x, int y, String title)
	{
		createFullScreen(Monitor.getPrimaryMonitor(), x, y, title);
	}
	
	public void createFullScreen(String title)
	{
		createFullScreen(Monitor.getPrimaryMonitor(), title);
	}
	
	
	//destroy
	public void destroy()
	{
		glfwDestroyWindow(win);
		win = NULL;
		closed = false;
	}
	
	
	//setters
	public void setSizeX(int x)
	{
		glfwSetWindowSize(win, x, getHeight());
	}
	
	public void setSizeY(int y)
	{
		glfwSetWindowSize(win, getWidth(), y);
	}
	
	public void setPosX(int x)
	{
		glfwSetWindowPos(win, x, getPosY());
	}
	
	public void setPosY(int y)
	{
		glfwSetWindowPos(win, getPosX(), y);
	}
	
	public void setTitle(String title)
	{
		glfwSetWindowTitle(win, title);
	}
	
	
	//getters
	public int getWidth()
	{
		IntBuffer x = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(win, x, null);
		return x.get(0);
	}
	
	public int getHeight()
	{
		IntBuffer y = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(win, null, y);
		return y.get(0);
	}
	
	public int getPosX()
	{
		IntBuffer x = BufferUtils.createIntBuffer(1);
		glfwGetWindowPos(win, x, null);
		return x.get(0);
	}
	
	public int getPosY()
	{
		IntBuffer y = BufferUtils.createIntBuffer(1);
		glfwGetWindowPos(win, null, y);
		return y.get(0);
	}
	
	public long get() { return win; }
	
	public boolean isClosed()
	{
		return closed;
	}
	
	public Context getContext() {
		return context;
	}
	
	
	//minimizar y restaurar
	public void minimize()
	{
		glfwIconifyWindow(win);
	}
	
	public void restore()
	{
		glfwRestoreWindow(win);
	}
	
	public boolean isMinimized()
	{
		return glfwGetWindowAttrib(win, GLFW_ICONIFIED) > 0;
	}
	
	
	//visibilidad
	public void show(boolean show)
	{
		if(show)
			glfwShowWindow(win);
		else
			glfwHideWindow(win);
	}
	
	public boolean isVisible()
	{
		return glfwGetWindowAttrib(win, GLFW_VISIBLE) > 0;
	}
	
	
	//foco
	public boolean isFocused()
	{
		return glfwGetWindowAttrib(win, GLFW_FOCUSED) > 0;
	}
	
	
	//estilos
	public void setResizable(boolean rz)
	{
		glfwWindowHint(GLFW_RESIZABLE, rz ? GL_TRUE : GL_FALSE);
	}
	
	public void setDecorated(boolean dec)
	{
		glfwWindowHint(GLFW_DECORATED, dec ? GL_TRUE : GL_FALSE);
	}
	
	public void setFloating(boolean dec)
	{
		glfwWindowHint(GLFW_FLOATING, dec ? GL_TRUE : GL_FALSE);
	}
	
	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
	
	
	//eventos
	public void registerEvent(WindowEvent event)
	{
		events.add(event);
	}
	
	public void unregisterEvent(WindowEvent event)
	{
		events.remove(event);
	}
	
	public void pollEvent()
	{
		glfwPollEvents();
	}
	
	public void waitEvent()
	{
		glfwWaitEvents();
	}
	
	
	private void applyEvent()
	{
		glfwSetWindowCloseCallback(win, GLFWWindowCloseCallback.create(new GLFWWindowCloseCallbackI()
		{
			public void invoke(long arg0)
			{
				closed = true;
				for(WindowEvent e : events)
					closed &= e.onClose();
			}
		}));
		
		glfwSetWindowSizeCallback(win, GLFWWindowSizeCallback.create(new GLFWWindowSizeCallbackI()
		{
			public void invoke(long arg0, int x, int y)
			{
				for(WindowEvent e : events)
					e.onResize(x, y);
			}
		}));
		
		glfwSetWindowPosCallback(win, GLFWWindowPosCallback.create(new GLFWWindowPosCallbackI()
		{
			public void invoke(long arg0, int x, int y)
			{
				for(WindowEvent e : events)
					e.onMove(x, y);
			}
		}));
		
		glfwSetWindowIconifyCallback(win, GLFWWindowIconifyCallback.create(new GLFWWindowIconifyCallbackI()
		{
			public void invoke(long arg0, boolean arg1) 
			{
				for(WindowEvent e : events)
				{
					if(!arg1)
						e.onRestore();
					else
						e.onMinimize();
				}
			}
		}));
		
		glfwSetWindowFocusCallback(win, GLFWWindowFocusCallback.create(new GLFWWindowFocusCallbackI()
		{
			public void invoke(long arg0, boolean arg1) 
			{
				for(WindowEvent e : events)
				{
					if(!arg1)
						e.onLeaveFocus();
					else
						e.onFocus();;
				}
			}
		}));
		
		glfwSetWindowRefreshCallback(win, GLFWWindowRefreshCallback.create(new GLFWWindowRefreshCallbackI()
		{
			public void invoke(long arg0)
			{
				for(WindowEvent e : events)
					e.onRender();
			}
		}));
		
		glfwSetKeyCallback(win, GLFWKeyCallback.create(new GLFWKeyCallbackI()
		{
			public void invoke(long win, int key, int scan, int act, int mods) 
			{
				for(WindowEvent e : events)
				{
					if(act == GLFW_PRESS)
						e.onKeyDown(key, new SpecialKey(mods));
					
					if(act == GLFW_RELEASE)
						e.onKeyRelease(key, new SpecialKey(mods));
				}
			}
		}));
		
		glfwSetCursorPosCallback(win, GLFWCursorPosCallback.create(new GLFWCursorPosCallbackI()
		{
			public void invoke(long win, double x, double y)
			{
				for(WindowEvent e : events)
					e.onMouseMove(x, y);
			}
		}));
		
		glfwSetCursorEnterCallback(win, GLFWCursorEnterCallback.create(new GLFWCursorEnterCallbackI()
		{
			public void invoke(long win, boolean act)
			{
				for(WindowEvent e : events)
				{
					if(!act)
						e.onLeave();
					else
						e.onHover();
				}
			}
		}));
		
		glfwSetMouseButtonCallback(win, GLFWMouseButtonCallback.create(new GLFWMouseButtonCallbackI()
		{
			public void invoke(long win, int key, int act, int mods) 
			{
				for(WindowEvent e : events)
				{
					if(act == GLFW_PRESS)
						e.onClickDown(key);
					
					if(act == GLFW_RELEASE)
						e.onClickRelease(key);
				}
			}
		}));
		
		glfwSetScrollCallback(win, GLFWScrollCallback.create(new GLFWScrollCallbackI()
		{
			public void invoke(long win, double xoffset, double yoffset)
			{
				for(WindowEvent e : events)
					e.onMouseWheel(yoffset);
			}
		}));
		
		glfwSetDropCallback(win, GLFWDropCallback.create(new GLFWDropCallbackI()
		{
			public void invoke(long win, int count, long names) 
			{
				List<String> aux = new LinkedList<String>();
				for(int i=0; i<count; i++)
					aux.add(GLFWDropCallback.getName(names, i));
				
				for(WindowEvent e : events)
					e.onDrop(aux);
			}
		}));
	}
	
	
	public void live()
	{
		while(!this.isClosed())
		{
			for(WindowEvent e : events)
				e.onIdle();
			
			this.swapBuffers();
			this.pollEvent();
		}
		
		this.destroy();
	}
	
	public void liveWait()
	{
		while(!this.isClosed())
		{
			for(WindowEvent e : events)
				e.onIdle();
			
			this.waitEvent();
		}
		
		this.destroy();
	}
	
	
	//buffer
	public void swapBuffers()
	{
		glfwSwapBuffers(win);
	}
	
	public void swapInterval(int i)
	{
		glfwSwapInterval(i);
	}
	
	
	//contexto
	public boolean makeCurrentContext()
	{
		glfwMakeContextCurrent(win);
		
		try
		{
			GL.getCapabilities();
		}
		catch(Exception e)
		{
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
	
	
	//teclas
	public boolean isPressed(int key)
	{
		return glfwGetKey(win, key) == GLFW_PRESS ? true : false;
	}
	
	public boolean isPressed(Key key)
	{
		return glfwGetKey(win, key.getValue()) == GLFW_PRESS ? true : false;
	}
	
	
	//raton
	public double getCursorPosX()
	{
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(win, x, null);
		return x.get(0);
	}
	
	public double getCursorPosY()
	{
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(win, null, y);
		return y.get(0);
	}
	
	
	
	public boolean isClickedLeft()
	{
		return glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS ? true : false;
	}
	
	public boolean isClickedRight()
	{
		return glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_RIGHT) == GLFW_PRESS ? true : false;
	}
	
	public boolean isClickedMiddle()
	{
		return glfwGetMouseButton(win, GLFW_MOUSE_BUTTON_MIDDLE) == GLFW_PRESS ? true : false;
	}
	
	public boolean isClicked(int click)
	{
		return glfwGetMouseButton(win, click) == GLFW_PRESS ? true : false;
	}
	
	
	//mando
	public boolean isJoystickPresent(int joy)
	{
		return glfwJoystickPresent(joy) ? true : false;
	}
	
	public String getJoystickName(int joy)
	{
		return glfwGetJoystickName(joy);
	}
	
	
	
	public boolean isJoystickKeyPress(int joy, int key)
	{
		return glfwGetJoystickButtons(joy).get(key) > 0 ? true : false;
	}
	
	public boolean isJoystickKeyPress(int joy, Joystick key)
	{
		return glfwGetJoystickButtons(joy).get(key.getValue()) > 0 ? true : false;
	}
	
	
	
	public double getLeftAxisX(int joy)
	{
		return glfwGetJoystickAxes(joy).get(0);
	}
	
	public double getLeftAxisY(int joy)
	{
		return glfwGetJoystickAxes(joy).get(1);
	}
	
	public double getRightAxisX(int joy)
	{
		return glfwGetJoystickAxes(joy).get(4);
	}
	
	public double getRightAxisY(int joy)
	{
		return glfwGetJoystickAxes(joy).get(3);
	}
	
	public double getLeftTrigger(int joy)
	{
		double res = glfwGetJoystickAxes(joy).get(2);
		return res < 0.0 ? 0.0 : res;
	}
	
	public double getRightTrigger(int joy)
	{
		double res = glfwGetJoystickAxes(joy).get(2);
		return res > 0.0 ? 0.0 : res;
	}
	
	
	
	//portapapeles
	public String getClipboardString()
	{
		return glfwGetClipboardString(win);
	}
	
	public void setClipboardString(String clip)
	{
		glfwSetClipboardString(win, clip);
	}
	
	
	//cursor
	public void disableCursor()
	{
		glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	public void hiddenCursor()
	{
		glfwSetInputMode(win, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}
	
	
	public void setCursor(Cursor cur)
	{
		glfwSetCursor(win, cur == null ? NULL : cur.get());
	}
}
