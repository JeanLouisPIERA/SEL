package com.microselbourse.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.microselbourse.beans.UserBean;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id1", scope = MessageMailEchangeEvaluation.class)
public class MessageMailEchangeEvaluation {

	Evaluation evaluation;
	UserBean destinataire;
	String subject;
	String microselBourseMailTemplate;

	public MessageMailEchangeEvaluation() {
		super();
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
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
