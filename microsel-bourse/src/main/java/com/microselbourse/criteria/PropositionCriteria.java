package com.microselbourse.criteria;

/**
 *  Classe permettant le requêtage de la classe Proposition : ce sont les offres ou les demandes déposées dans la Bourse d'échanges
 * @author jeanl
 *
 */
public class PropositionCriteria {

	private String emetteurId;

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

	public String getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(String emetteurId) {
		this.emetteurId = emetteurId;
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
