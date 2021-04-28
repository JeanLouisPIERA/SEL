package com.microselbourse.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.microselbourse.beans.UserBean;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id1", scope = MessageMailDeblocage.class)
public class MessageMailDeblocage {

	Blocage blocage;
	UserBean adherent;
	String subject;
	String microselBourseMailTemplate;

	public MessageMailDeblocage() {
		super();
	}

	public Blocage getBlocage() {
		return blocage;
	}

	public void setBlocage(Blocage blocage) {
		this.blocage = blocage;
	}
	
	

	public UserBean getAdherent() {
		return adherent;
	}

	public void setAdherent(UserBean adherent) {
		this.adherent = adherent;
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
