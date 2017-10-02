package com.utilsgl.exception;

public class GLFWErrorException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	
	public GLFWErrorException() {}
	public GLFWErrorException(String msg) { super(msg); }
}
