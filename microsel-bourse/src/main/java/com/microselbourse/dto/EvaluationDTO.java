package com.microselbourse.dto;

import com.microselbourse.entities.EnumNoteEchange;

public class EvaluationDTO {
	
	private Long adherentId;

	private String commentaire;
	
	private EnumNoteEchange enumNoteEchange;

	public EvaluationDTO() {
		super();
	}

	
	public Long getAdherentId() {
		return adherentId;
	}



	public void setAdherentId(Long adherentId) {
		this.adherentId = adherentId;
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
