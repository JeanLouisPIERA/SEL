package com.microselwebclientjspui.dto;

public class ArticleDTO {

	private String titre;
	private String auteurId;
	private String auteurUsername;
	private String image;
	private String contenu;
	private String entete;
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
	
	

	public String getAuteurUsername() {
		return auteurUsername;
	}

	public void setAuteurUsername(String auteurUsername) {
		this.auteurUsername = auteurUsername;
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
