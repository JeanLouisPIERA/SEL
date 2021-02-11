package com.microselwebui.microselwebui.errors;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder{
	
	private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response reponse) {

        if(reponse.status() == 413 ) {
        	String entityNotFoundExceptionMessage = reponse.body().toString();
            return new EntityNotFoundException( entityNotFoundExceptionMessage
            );
        }
        
        if(reponse.status() == 409 ) {
        	String entityAlreadyExistsExceptionMessage = reponse.body().toString();
            return new EntityNotFoundException( entityAlreadyExistsExceptionMessage
            );
        }
        
        if(reponse.status() == 423 ) {
        	String entityDeniedAccessExceptionMessage = reponse.body().toString();
            return new EntityNotFoundException( entityDeniedAccessExceptionMessage
            );
        }
        

        return defaultErrorDecoder.decode(methodKey, reponse);
    }

	

}
