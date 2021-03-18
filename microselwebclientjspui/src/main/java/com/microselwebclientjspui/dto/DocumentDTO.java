package com.microselwebclientjspui.dto;

import java.time.LocalDate;

public class DocumentDTO {
	
	private String titre;
	private Long auteurId; 
	private String image;
	private String contenu;
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
