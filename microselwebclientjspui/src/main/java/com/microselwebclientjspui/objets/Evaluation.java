package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Evaluation {
	
	private Long id;
	
	private Long adherentId;
	
	private String adherentUsername;
	
	private String commentaire;
	
	private EnumNoteEchange enumNoteEchange;
	
	private LocalDate dateEvaluation;
	
	private Echange echange;

	public Evaluation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdherentId() {
		return adherentId;
	}

	public void setAdherentId(Long adherentId) {
		this.adherentId = adherentId;
	}

	public String getAdherentUsername() {
		return adherentUsername;
	}

	public void setAdherentUsername(String adherentUsername) {
		this.adherentUsername = adherentUsername;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	

	public EnumNoteEchange getEnumNoteEchange() {
		return enumNoteEchange;
	}

	public void setEnumNoteEchange(EnumNoteEchange enumNoteEchange) {
		this.enumNoteEchange = enumNoteEchange;
	}

	public LocalDate getDateEvaluation() {
		return dateEvaluation;
	}

	public void setDateEvaluation(LocalDate dateEvaluation) {
		this.dateEvaluation = dateEvaluation;
	}

	public Echange getEchange() {
		return echange;
	}

	public void setEchange(Echange echange) {
		this.echange = echange;
	}
	
	
	
}
