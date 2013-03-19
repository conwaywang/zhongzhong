package com.fumuquan.exception;


public class OperateStreamException extends Exception{
	private static final long serialVersionUID = -5902021590165090986L;
	private String message;
	
	public OperateStreamException(String message)
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
