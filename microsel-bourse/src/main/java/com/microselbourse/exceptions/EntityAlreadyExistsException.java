package com.microselbourse.exceptions;

public class EntityAlreadyExistsException extends BourseException{
	
	public EntityAlreadyExistsException(){
		super();
	}
 
	public EntityAlreadyExistsException(String message) {
		super(message);
	}

}
