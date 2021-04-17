package com.microselwebclientjspui.criteria;

public class PropositionCriteria {

	private String emetteurUsername;

	private String enumTradeType;

	private String titre;

	private String ville;

	private Integer codePostal;

	private String enumCategorie;

	private String enumStatutProposition;

	public PropositionCriteria() {
		super();
	}

	public String getEmetteurUsername() {
		return emetteurUsername;
	}

	public void setEmetteurUsername(String emetteurUsername) {
		this.emetteurUsername = emetteurUsername;
	}

	public String getEnumTradeType() {
		return enumTradeType;
	}

	public void setEnumTradeType(String enumTradeType) {
		this.enumTradeType = enumTradeType;
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

	public String getEnumCategorie() {
		return enumCategorie;
	}

	public void setEnumCategorie(String enumCategorie) {
		this.enumCategorie = enumCategorie;
	}

	public String getEnumStatutProposition() {
		return enumStatutProposition;
	}

	public void setEnumStatutProposition(String enumStatutProposition) {
		this.enumStatutProposition = enumStatutProposition;
	}

	/*
	 * public String convertEnumTradeTypeToString() {
	 * if(this.getEnumTradeType()==null) { return null; } else { return
	 * this.getEnumTradeType().getCode(); } }
	 * 
	 * public String convertEnumStatutPropositionToString() {
	 * if(this.getEnumStatutProposition()==null) { return null; } else { return
	 * this.getEnumStatutProposition().getCode(); } }
	 * 
	 * public String convertEnumCategorieToString() {
	 * if(this.getEnumCategorie()==null) { return null; } else { return
	 * this.getEnumCategorie().getCode(); } }
	 */

}
