package com.microselwebclientjspui.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends Exception{
	
	public NotAuthorizedException(String message) {
        super(message);
    }

}
