package com.exception;

public class FailInitLibraryException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	
	public FailInitLibraryException() {}
	public FailInitLibraryException(String msg) { super(msg); }
}
