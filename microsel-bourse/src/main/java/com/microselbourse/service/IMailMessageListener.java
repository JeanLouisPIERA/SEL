package com.microselbourse.service;

import com.microselbourse.entities.Reponse;

public interface IMailMessageListener {

	public void receiveMessage(Reponse reponse, String destinataireId, String subject,
			String microselBourseMailTemplate);

}
