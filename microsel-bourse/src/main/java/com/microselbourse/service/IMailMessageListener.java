package com.microselbourse.service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Reponse;

public interface IMailMessageListener {
	
	public void receiveMessage(Reponse reponse, Long destinataireId, String subject, String microselBourseMailTemplate);

}
