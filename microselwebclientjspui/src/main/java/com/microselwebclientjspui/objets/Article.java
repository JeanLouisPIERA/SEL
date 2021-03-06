package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Article {

	private Long id;
	private String titre;
	private String auteurId;
	private String auteurUsername;
	private LocalDate dateCreation;
	private LocalDate dateModeration;
	private LocalDate datePublication; 
	private LocalDate dateArchivage;
	private String image;
	private String contenu;
	private String entete;
	private EnumStatutDocument statutDocument;
	private TypeArticle typeArticle;
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

	public LocalDate getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(LocalDate datePublication) {
		this.datePublication = datePublication;
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

	public LocalDate getDateModeration() {
		return dateModeration;
	}

	public void setDateModeration(LocalDate dateModeration) {
		this.dateModeration = dateModeration;
	}
	
	

	
}
