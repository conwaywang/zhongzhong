package com.fumuquan.exception;

public class ApkEmptyException extends Exception{
	
	private static final long serialVersionUID = 1393067616788353770L;
	private String message;
	
	public ApkEmptyException(String message)
	{
		super();
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
