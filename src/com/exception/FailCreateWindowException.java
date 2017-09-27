package com.exception;

public class FailCreateWindowException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	
	public FailCreateWindowException() {}
	public FailCreateWindowException(String msg) { super(msg); }
}
