package com.microselbourse.criteria;

import java.time.LocalDate;

public class PropositionCriteria {
	
	
	private String codeEnumTradeType;
	
	private String titre;
	
	private String ville;
	
	private Integer codePostal;
	
	private LocalDate dateDebut;
	
	private LocalDate dateFin; 
	
	private LocalDate dateEcheance;
	
	private String nomCategorie;

	public PropositionCriteria() {
		super();
	}

	public PropositionCriteria(String codeEnumTradeType, String titre, String ville, Integer codePostal,
			LocalDate dateDebut, LocalDate dateFin, LocalDate dateEcheance, String nomCategorie) {
		super();
		this.codeEnumTradeType = codeEnumTradeType;
		this.titre = titre;
		this.ville = ville;
		this.codePostal = codePostal;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.dateEcheance = dateEcheance;
		this.nomCategorie = nomCategorie;
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

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	
	

	public LocalDate getDateDebut() {
		return dateDebut;
	}



	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	@Override
	public String toString() {
		return "PropositionCriteria [codeEnumTradeType=" + codeEnumTradeType + ", titre=" + titre + ", ville=" + ville
				+ ", codePostal=" + codePostal + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", dateEcheance="
				+ dateEcheance + ", nomCategorie=" + nomCategorie + "]";
	}



	

}
