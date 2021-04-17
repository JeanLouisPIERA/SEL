package com.microselwebclientjspui.criteria;

import java.time.LocalDate;

public class DocumentCriteria {

	private String auteurId;
	private String auteurUsername;
	private LocalDate dateCreation;
	private LocalDate dateLastUpdate;
	private String enumStatutDocument;
	private String nomTypeDocument;

	public DocumentCriteria() {
		super();
	}

	public String getAuteurId() {
		return auteurId;
	}

	public void setAuteurId(String auteurId) {
		this.auteurId = auteurId;
	}

	public String getAuteurUsername() {
		return auteurUsername;
	}

	public void setAuteurUsername(String auteurUsername) {
		this.auteurUsername = auteurUsername;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(LocalDate dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	public String getEnumStatutDocument() {
		return enumStatutDocument;
	}

	public void setEnumStatutDocument(String enumStatutDocument) {
		this.enumStatutDocument = enumStatutDocument;
	}

	public String getNomTypeDocument() {
		return nomTypeDocument;
	}

	public void setNomTypeDocument(String nomTypeDocument) {
		this.nomTypeDocument = nomTypeDocument;
	}

}
