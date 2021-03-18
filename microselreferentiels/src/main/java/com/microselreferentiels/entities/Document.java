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
@Table(name="documents")
public class Document implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "document_id", length=5)
	private Long id;
	
	@Column(name = "titre", length = 25, nullable=false)
	private String titre;
	
	@Column(name = "auteur_id", length = 5, nullable=false)
	private Long auteurId; 
	
	@Column(name = "auteur_username", length = 25, nullable=false)
	private String auteurUsername;
	
	@Column(name = "date_creation")
	private LocalDate dateCreation; 
	
	@Column(name = "date_last_update")
	private LocalDate dateLastUpdate;
	
	@Column(name = "image", length = 25)
	private String image;
	
	@Column(name = "contenu", length = 255, nullable=false)
	private String contenu;
	
	@Column(name = "statut_document", length = 3)
	private EnumStatutDocument statutDocument;
	
	@ManyToOne 
	@JoinColumn(name="typedocument_id")
	private TypeDocument typeDocument;

	public Document() {
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

	public Long getAuteurId() {
		return auteurId;
	}

	public void setAuteurId(Long auteurId) {
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

	public LocalDate getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(LocalDate dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
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

	public EnumStatutDocument getStatutDocument() {
		return statutDocument;
	}

	public void setStatutDocument(EnumStatutDocument statutDocument) {
		this.statutDocument = statutDocument;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}
	
	

}
