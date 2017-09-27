package com.utils;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

import java.io.File;
import java.io.IOException;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.exception.FailInitLibraryException;
import com.exception.GLFWErrorException;

public class UtilsGL 
{
	private static int delta = 0;
	private static long lastTime = 0;
	private static int fps = 0;
	private static int frame = 0;
	private static int lapse = 0;
	
	
	public static void init(String natives) {
		init(natives, null);
	}
	
	public static void init(String natives, String libs)
	{
		try {
			if(libs != null)
				System.setProperty ( "java.library.path", libs);
			
			System.setProperty("org.lwjgl.librarypath", new File(natives).getCanonicalPath());
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		if(!glfwInit())
			throw new FailInitLibraryException();
		
		
		glfwSetErrorCallback(new GLFWErrorCallback()
		{
			public void invoke(int arg0, long arg1) 
			{
				String res = "";
				
				switch(arg0)
				{
					case 0x00010001:
						res += "GLFW_NOT_INITIALIZED: ";
						break;
					case 0x00010002:
						res += "GLFW_NO_CURRENT_CONTEXT: ";
						break;
					case 0x00010003:
						res += "GLFW_INVALID_ENUM: ";
						break;
					case 0x00010004:
						res += "GLFW_INVALID_VALUE: ";
						break;
					case 0x00010005:
						res += "GLFW_OUT_OF_MEMORY: ";
						break;
					case 0x00010006:
						res += "GLFW_API_UNAVAILABLE: ";
						break;
					case 0x00010007:
						res += "GLFW_VERSION_UNAVAILABLE: ";
						break;
					case 0x00010008:
						res += "GLFW_PLATFORM_ERROR: ";
						break;
					case 0x00010009:
						res += "GLFW_FORMAT_UNAVAILABLE: ";
						break;
				}
				
				res += getDescription(arg1);
				
				throw new GLFWErrorException(res);
			}
		});
	}
	
	
	public static void calculeDelta()
	{
		delta = (int) (System.currentTimeMillis() - lastTime);
		lastTime = System.currentTimeMillis();
		
		lapse += delta;
		if(lapse >= 1000)
		{
			fps = frame;
			frame = 0;
			lapse = 0;
		}
		frame++;
	}
	
	public static int getDelta()
	{
		return delta;
	}
	
	public static int getFPS()
	{
		return fps;
	}
}
