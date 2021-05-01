package com.microselreferentiels.entities;

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

@Entity
@Table(name = "articles")
public class Article implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", length = 5)
	private Long id;

	@Column(name = "titre", length = 25, nullable = false)
	private String titre;

	@Column(name = "auteur_id", length = 255)
	private String auteurId;

	@Column(name = "auteur_username", length = 25, nullable = false)
	private String auteurUsername;

	@Column(name = "date_creation")
	private LocalDate dateCreation;

	@Column(name = "date_publication")
	private LocalDate datePublication;

	@Column(name = "date_moderation")
	private LocalDate dateModeration;

	@Column(name = "date_archivage")
	private LocalDate dateArchivage;

	@Column(name = "image", length = 25)
	private String image;

	@Column(name = "contenu", length = 255, nullable = false)
	private String contenu;

	@Column(name = "entete", length = 50, nullable = false)
	private String entete;

	@Column(name = "statut_document", length = 3)
	private EnumStatutDocument statutDocument;

	@ManyToOne
	@JoinColumn(name = "typearticle_id")
	private TypeArticle typeArticle;

	@Column(name = "is_moderated")
	private Boolean isModerated;

	public Article() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
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

	public LocalDate getDateModeration() {
		return dateModeration;
	}

	public void setDateModeration(LocalDate dateModeration) {
		this.dateModeration = dateModeration;
	}

	public LocalDate getDateArchivage() {
		return dateArchivage;
	}

	public void setDateArchivage(LocalDate dateArchivage) {
		this.dateArchivage = dateArchivage;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getEntete() {
		return entete;
	}

	public void setEntete(String entete) {
		this.entete = entete;
	}

	public EnumStatutDocument getStatutDocument() {
		return statutDocument;
	}

	public void setStatutDocument(EnumStatutDocument statutDocument) {
		this.statutDocument = statutDocument;
	}

	public TypeArticle getTypeArticle() {
		return typeArticle;
	}

	public void setTypeArticle(TypeArticle typeArticle) {
		this.typeArticle = typeArticle;
	}

	public Boolean getIsModerated() {
		return isModerated;
	}

	public void setIsModerated(Boolean isModerated) {
		this.isModerated = isModerated;
	}

	public LocalDate getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(LocalDate datePublication) {
		this.datePublication = datePublication;
	}

}
