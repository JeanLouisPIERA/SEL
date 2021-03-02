package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;

public interface IMailSenderService {
	
	void sendMailEchangeCreation(Reponse reponse, UserBean destinataire, String subject, String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException;
	    
	void sendMailEchangeConfirmation(Echange echange, UserBean destinataire, String subject, String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException;
	 
	void sendMessageMailEchangeCreation(Reponse reponse, Long destinataireId, String subject, String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException;
	
	
}
