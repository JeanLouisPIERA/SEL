package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Table(name = "evaluations")
public class Evaluation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluation_id", length = 5)
	private Long id;

	@Column(name = "adherent_id", length = 5, nullable = false)
	private String adherentId;

	@Column(name = "adherent_username", length = 25, nullable = false)
	private String adherentUsername;

	@Column(name = "commentaire", length = 255)
	private String commentaire;

	@Column(name = "note", length = 25, nullable = false)
	private EnumNoteEchange enumNoteEchange;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_evaluation", length = 25, nullable = false)
	private LocalDate dateEvaluation;

	@Column(name = "is_moderated")
	private Boolean isModerated;

	@ManyToOne
	@JoinColumn(name = "echange_id")
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

	public Boolean getIsModerated() {
		return isModerated;
	}

	public void setIsModerated(Boolean isModerated) {
		this.isModerated = isModerated;
	}

}
