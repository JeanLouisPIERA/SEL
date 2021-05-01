package com.microselbourse.dto;

import com.microselbourse.entities.EnumNoteEchange;

/**
 * Wrapper pour le transfert des données dans les opérations de création de la classe Evaluation
 * @author jeanl
 *
 */
public class EvaluationDTO {

	private String adherentId;

	private String adherentUsername;

	private String commentaire;

	private EnumNoteEchange enumNoteEchange;

	public EvaluationDTO() {
		super();
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

}
