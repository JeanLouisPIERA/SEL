package com.microselbourse.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;



public interface IMailService {
	
	void sendMessageUsingThymeleafTemplate(
	        String to, String name, String subject, Map<String, Object> templateModel, String mailTemplate)
	            throws MessagingException, UnsupportedEncodingException ;
	
	void sendMessageUsingThymeleafTemplateSuppress(
	        String to, String name, String subject, Map<String, Object> templateModel)
	            throws MessagingException, UnsupportedEncodingException ;
	
	void sendHtmlMessage(
			String to, String name, String subject, String htmlBody) 
					throws MessagingException, UnsupportedEncodingException;
	
	

}
