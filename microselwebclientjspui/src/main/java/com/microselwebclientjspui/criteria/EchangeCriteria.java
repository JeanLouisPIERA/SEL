package com.microselwebclientjspui.criteria;

public class EchangeCriteria {
	
	private Long id;
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
