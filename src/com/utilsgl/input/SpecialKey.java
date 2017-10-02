package com.utilsgl.input;

import static org.lwjgl.glfw.GLFW.GLFW_MOD_ALT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOD_SUPER;


public class SpecialKey 
{
	//atributos
	private int mod;

	
	//constructor
	public SpecialKey(int mod) 
	{
		this.mod = mod;
	}
	
	
	//consultores
	public boolean isShift()
	{
		return (mod&GLFW_MOD_SHIFT) > 0;
	}
	
	public boolean isControl()
	{
		return (mod&GLFW_MOD_CONTROL) > 0;
	}
	
	public boolean isAlt()
	{
		return (mod&GLFW_MOD_ALT) > 0;
	}
	
	public boolean isSuper()
	{
		return (mod&GLFW_MOD_SUPER) > 0;
	}
}
