package com.fumuquan.exception;

public class DataNotFitException extends Exception{
	private static final long serialVersionUID = 4514448644015786982L;
	private String message;

	public DataNotFitException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
