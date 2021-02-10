package com.microselbourse.exceptions;

public class BourseException extends Exception{

	private String message;

	public BourseException() {
		super();
	}

	public BourseException(String message) {
		super(message);
	}
	
}
