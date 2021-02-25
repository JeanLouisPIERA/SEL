package com.microselwebclientjspui.objets;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;



public class Proposition {
	
	private Long id;
	
	private Long emetteurId;
	
	private EnumTradeType enumTradeType; 
	
	private String titre; 
	
	private String description; 
	
	private String image; 
	
	private String ville;
	
	private Integer codePostal; 
	
	private Integer valeur; 
	
	private LocalDate dateFin; 
	
	private LocalDate dateEcheance; 
	
	private EnumStatutProposition statut;
	
	private Categorie categorie;
	
	
	private LocalDate dateDebut;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(Long emetteurId) {
		this.emetteurId = emetteurId;
	}

	public EnumTradeType getEnumTradeType() {
		return enumTradeType;
	}

	public void setEnumTradeType(EnumTradeType enumTradeType) {
		this.enumTradeType = enumTradeType;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public EnumStatutProposition getStatut() {
		return statut;
	}

	public void setStatut(EnumStatutProposition statut) {
		this.statut = statut;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	@Override
	public String toString() {
		return "Proposition [id=" + id + ", emetteurId=" + emetteurId + ", enumTradeType=" + enumTradeType
				+ ", titre=" + titre + ", description=" + description + ", image=" + image + ", ville=" + ville
				+ ", codePostal=" + codePostal + ", valeur=" + valeur + ", dateFin=" + dateFin + ", dateEcheance="
				+ dateEcheance + ", statut=" + statut + ", categorie=" + categorie + ", dateDebut=" + dateDebut + "]";
	}
	
	
	

}
