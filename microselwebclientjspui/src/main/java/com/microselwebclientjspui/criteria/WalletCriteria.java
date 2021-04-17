package com.microselwebclientjspui.criteria;

public class WalletCriteria {

	private Long id;

	private String titulaireId;

	private String titulaireUsername;

	private Integer soldeWallet;

	public WalletCriteria() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulaireId() {
		return titulaireId;
	}

	public void setTitulaireId(String titulaireId) {
		this.titulaireId = titulaireId;
	}

	public String getTitulaireUsername() {
		return titulaireUsername;
	}

	public void setTitulaireUsername(String titulaireUsername) {
		this.titulaireUsername = titulaireUsername;
	}

	public Integer getSoldeWallet() {
		return soldeWallet;
	}

	public void setSoldeWallet(Integer soldeWallet) {
		this.soldeWallet = soldeWallet;
	}

}
