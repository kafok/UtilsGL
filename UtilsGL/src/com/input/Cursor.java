package com.input;

import org.lwjgl.glfw.GLFW;

public class Cursor 
{
	private long cur;

	public Cursor(long cur) 
	{
		this.cur = cur;
	}
	
	public long get()
	{
		return cur;
	}
	
	
	//estaticos
	public final static Cursor ARROW_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_ARROW_CURSOR));
	public final static Cursor IBEAM_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_IBEAM_CURSOR));
	public final static Cursor CROSSHAIR_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_CROSSHAIR_CURSOR));
	public final static Cursor HAND_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_HAND_CURSOR));
	public final static Cursor HRESIZE_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_HRESIZE_CURSOR));
	public final static Cursor VRESIZE_CURSOR = new Cursor(GLFW.glfwCreateStandardCursor(GLFW.GLFW_VRESIZE_CURSOR));
}
