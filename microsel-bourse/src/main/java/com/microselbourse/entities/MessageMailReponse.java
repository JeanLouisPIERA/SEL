package com.microselbourse.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microselbourse.beans.UserBean;


@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = MessageMailReponse.class)
public class MessageMailReponse {
	
	Reponse reponse;
	UserBean destinataire;
	String subject;
	String microselBourseMailTemplate;
	
	public MessageMailReponse() {
		super();
	}

	public Reponse getReponse() {
		return reponse;
	}

	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}

	public UserBean getDestinataire() {
		return destinataire;
	}

	public void setDestinataire(UserBean destinataire) {
		this.destinataire = destinataire;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMicroselBourseMailTemplate() {
		return microselBourseMailTemplate;
	}

	public void setMicroselBourseMailTemplate(String microselBourseMailTemplate) {
		this.microselBourseMailTemplate = microselBourseMailTemplate;
	}

	@Override
	public String toString() {
		return "MessageMailReponse [reponse=" + reponse + ", destinataire=" + destinataire + ", subject=" + subject
				+ ", microselBourseMailTemplate=" + microselBourseMailTemplate + "]";
	}
	
	

}
