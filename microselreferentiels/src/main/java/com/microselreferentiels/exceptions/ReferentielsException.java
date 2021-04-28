package com.microselreferentiels.exceptions;

public class ReferentielsException extends Exception{

	private String message;

	public ReferentielsException() {
		super();
	}

	public ReferentielsException(String message) {
		super(message);
	}
	
}
