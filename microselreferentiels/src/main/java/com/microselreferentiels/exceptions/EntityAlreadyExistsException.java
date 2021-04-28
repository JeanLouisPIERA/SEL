package com.microselreferentiels.exceptions;

public class EntityAlreadyExistsException extends ReferentielsException{
	
	public EntityAlreadyExistsException(){
		super();
	}
 
	public EntityAlreadyExistsException(String message) {
		super(message);
	}

}
