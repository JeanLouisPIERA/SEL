package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Blocage {
	
	String id;
	String adherentId;
	String adherentUsername;
	LocalDate dateDebutBlocage;
	LocalDate dateFinBlocage;
	EnumStatutBlocage statutBlocage;
	Echange echange;
	
	public Blocage() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdherentId() {
		return adherentId;
	}

	public void setAdherentId(String adherentId) {
		this.adherentId = adherentId;
	}

	public String getAdherentUsername() {
		return adherentUsername;
	}

	public void setAdherentUsername(String adherentUsername) {
		this.adherentUsername = adherentUsername;
	}

	public LocalDate getDateDebutBlocage() {
		return dateDebutBlocage;
	}

	public void setDateDebutBlocage(LocalDate dateDebutBlocage) {
		this.dateDebutBlocage = dateDebutBlocage;
	}

	public LocalDate getDateFinBlocage() {
		return dateFinBlocage;
	}

	public void setDateFinBlocage(LocalDate dateFinBlocage) {
		this.dateFinBlocage = dateFinBlocage;
	}

	public EnumStatutBlocage getStatutBlocage() {
		return statutBlocage;
	}

	public void setStatutBlocage(EnumStatutBlocage statutBlocage) {
		this.statutBlocage = statutBlocage;
	}

	public Echange getEchange() {
		return echange;
	}

	public void setEchange(Echange echange) {
		this.echange = echange;
	}
	
	
	
	

}
