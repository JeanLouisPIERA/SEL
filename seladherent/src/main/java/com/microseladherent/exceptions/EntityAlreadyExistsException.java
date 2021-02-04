package com.microseladherent.exceptions;

public class EntityAlreadyExistsException extends AdherentException{
	
	public EntityAlreadyExistsException(){
		super();
	}
 
	public EntityAlreadyExistsException(String message) {
		super(message);
	}

}
