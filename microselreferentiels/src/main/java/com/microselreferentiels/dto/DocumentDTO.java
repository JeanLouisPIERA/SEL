package com.microselreferentiels.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.microselreferentiels.entities.TypeDocument;

public class DocumentDTO {
	
	@NotEmpty(message="Merci de saisir le titre de votre article")
	private String titre;
	
	@NotNull(message="Merci d'indiquer l'identifiant de l'adhérent auteur de ce document")
	private Long auteurId; 
	
	private String image;
	
	@NotEmpty(message="Merci de saisir le contenu de ce document")
	@Size(min = 0, max = 10000, message = "La taille de votre texte doit être au maximum de 10000 caractères")
	private String contenu;
	
	@NotEmpty(message="Merci d'indiquer le type de votre document")
	private String typeDocument;
	
	public DocumentDTO() {
		super();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Long getAuteurId() {
		return auteurId;
	}

	public void setAuteurId(Long auteurId) {
		this.auteurId = auteurId;
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

	public String getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}

	
	
	
	
	
	
	
	
	

}
