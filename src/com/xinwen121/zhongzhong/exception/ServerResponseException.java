package com.xinwen121.zhongzhong.exception;



public class ServerResponseException extends Exception
{
	private static final long serialVersionUID = 7562744606398560213L;
	
	private String message;
	
	public ServerResponseException(String message)
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
