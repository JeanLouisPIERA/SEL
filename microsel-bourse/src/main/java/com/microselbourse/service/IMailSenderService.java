package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.Reponse;

public interface IMailSenderService {

	void sendMailEchangeCreation(Reponse reponse, UserBean destinataire, String subject,
			String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException;

	void sendMailEchangeConfirmation(Echange echange, UserBean destinataire, String subject,
			String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException;

	void sendMailEchangeEvaluation(Evaluation evaluation, UserBean destinataire, String subject,
			String microselBourseMailTemplate) throws UnsupportedEncodingException, MessagingException;

	void sendMailAnnulationBlocage(Blocage blocage, UserBean destinataire, String subject,
			String microselBourseMailTemplate) throws UnsupportedEncodingException, MessagingException;

	/*
	 * void sendMessageMailEchangeCreation(Reponse reponse, Long destinataireId,
	 * String subject, String microselBourseMailTemplate) throws MessagingException,
	 * UnsupportedEncodingException;
	 */

}
