package com.microseluser.exceptions;

public class AdherentException extends Exception {

	private String message;

	public AdherentException() {
		super();
	}

	public AdherentException(String message) {
		super(message);
	}

}
