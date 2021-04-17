package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Reponse {

	private Long id;

	private String recepteurId;

	private String recepteurUsername;

	private EnumTradeType enumTradeType;

	private String titre;

	private String description;

	private String image;

	private String ville;

	private Integer codePostal;

	private Integer valeur;

	private LocalDate dateEcheance;

	private LocalDate dateReponse;

	private Proposition proposition;

	private Echange echange;

	public Reponse() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecepteurId() {
		return recepteurId;
	}

	public void setRecepteurId(String recepteurId) {
		this.recepteurId = recepteurId;
	}

	public String getRecepteurUsername() {
		return recepteurUsername;
	}

	public void setRecepteurUsername(String recepteurUsername) {
		this.recepteurUsername = recepteurUsername;
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

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public LocalDate getDateReponse() {
		return dateReponse;
	}

	public void setDateReponse(LocalDate dateReponse) {
		this.dateReponse = dateReponse;
	}

	public Proposition getProposition() {
		return proposition;
	}

	public void setProposition(Proposition proposition) {
		this.proposition = proposition;
	}

	public Echange getEchange() {
		return echange;
	}

	public void setEchange(Echange echange) {
		this.echange = echange;
	}

}
