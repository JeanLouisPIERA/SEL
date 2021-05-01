package com.microselbourse.criteria;

/**
 *  Classe permettant le requêtage de la classe Echange (généré automatiquement à la création d'une réponse à une proposition)
 * @author jeanl
 *
 */
public class EchangeCriteria {

	private Long id;
	private String emetteurId;
	private String recepteurId;
	private String emetteurUsername;
	private String recepteurUsername;
	private String titre;
	private String statutEchange;

	public EchangeCriteria() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(String emetteurId) {
		this.emetteurId = emetteurId;
	}

	public String getRecepteurId() {
		return recepteurId;
	}

	public void setRecepteurId(String recepteurId) {
		this.recepteurId = recepteurId;
	}

	public String getEmetteurUsername() {
		return emetteurUsername;
	}

	public void setEmetteurUsername(String emetteurUsername) {
		this.emetteurUsername = emetteurUsername;
	}

	public String getRecepteurUsername() {
		return recepteurUsername;
	}

	public void setRecepteurUsername(String recepteurUsername) {
		this.recepteurUsername = recepteurUsername;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getStatutEchange() {
		return statutEchange;
	}

	public void setStatutEchange(String statutEchange) {
		this.statutEchange = statutEchange;
	}

}
