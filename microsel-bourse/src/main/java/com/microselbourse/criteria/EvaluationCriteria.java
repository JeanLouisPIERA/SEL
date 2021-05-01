package com.microselbourse.criteria;

/**
 *  Classe permettant le requêtage de la classe Evaluations = commentaires déposés par les adhérents au moment de leur validation ou 
 *  de leur refus de validation d'un échange
 * @author jeanl
 *
 */
public class EvaluationCriteria {

	private Long id;

	private String adherentUsername;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdherentUsername() {
		return adherentUsername;
	}

	public void setAdherentUsername(String adherentUsername) {
		this.adherentUsername = adherentUsername;
	}

}
