package com.microselbourse.criteria;

import java.time.LocalDate;

public class PropositionCriteria {

	private String emetteurUsername;

	private String codeEnumTradeType;

	private String titre;

	private String ville;

	private Integer codePostal;

	private String nomCategorie;

	private String statut;

	public PropositionCriteria() {
		super();
	}

	public String getEmetteurUsername() {
		return emetteurUsername;
	}

	public void setEmetteurUsername(String emetteurUsername) {
		this.emetteurUsername = emetteurUsername;
	}

	public String getCodeEnumTradeType() {
		return codeEnumTradeType;
	}

	public void setCodeEnumTradeType(String codeEnumTradeType) {
		this.codeEnumTradeType = codeEnumTradeType;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
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

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}
