package com.microselbourse.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.microselbourse.beans.UserBean;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id1", scope = MessageMailEchange.class)
public class MessageMailEchange {

	Echange echange;
	UserBean destinataire;
	String subject;
	String microselBourseMailTemplate;

	public MessageMailEchange() {
		super();
	}

	public Echange getEchange() {
		return echange;
	}

	public void setEchange(Echange echange) {
		this.echange = echange;
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

}
