package com.microselwebclient_ui.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class DeniedAccessException extends Exception{
	
	public DeniedAccessException(String message) {
        super(message);
    }

}