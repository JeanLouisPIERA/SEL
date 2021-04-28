package com.microselreferentiels.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.microselreferentiels.entities.TypeDocument;

public class ArticleDTO {
	
	@NotEmpty(message="Merci de saisir le titre de votre article")
	private String titre;
	
	@NotNull(message="Merci d'indiquer l'identifiant de l'adhérent auteur de ce document")
	private String auteurId; 
	
	private String auteurUsername;
	
	private String image;
	
	@NotEmpty(message="Merci de saisir l'entete de ce document")
	@Size(min = 0, max = 255, message = "La taille de votre texte doit être au maximum de 255 caractères")
	private String entete;


	@NotEmpty(message="Merci de saisir le contenu de ce document")
	@Size(min = 0, max = 10000, message = "La taille de votre texte doit être au maximum de 10000 caractères")
	private String contenu;
	
	@NotEmpty(message="Merci d'indiquer le type de votre document")
	private String typeArticle;
	
	public ArticleDTO() {
		super();
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


	public String getTypeArticle() {
		return typeArticle;
	}


	public void setTypeArticle(String typeArticle) {
		this.typeArticle = typeArticle;
	}

	

	
	
	
	
	
	
	
	
	

}
