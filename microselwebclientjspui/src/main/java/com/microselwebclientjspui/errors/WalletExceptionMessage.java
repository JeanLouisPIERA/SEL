package com.microselwebclientjspui.errors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class WalletExceptionMessage {
	
	public String convertCodeStatusToExceptionMessage(Integer StatusCode) {
		String message ="";
		
		if (StatusCode ==409) {
			message =  "Impossible d'accéder à cette ressource";
			}
		
		return message;
	
	}
	
	public String convertHttpClientErrorExceptionToExceptionMessage (HttpClientErrorException e) {
	
		String message = e.getResponseBodyAsString();
		int start, end;
		start =  message.lastIndexOf(":", message.lastIndexOf(",")-1) + 2;
		end = message.lastIndexOf(",") -1;
		message = message.substring(start, end);
		System.out.println(message);
		
		return message;
		
	}

}
