package com.microselwebclientjspui.criteria;

public class WalletCriteria {
	
	private Long id;
	
	private Long titulaireId;
	
	private int soldeWallet;

	public WalletCriteria() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTitulaireId() {
		return titulaireId;
	}

	public void setTitulaireId(Long titulaireId) {
		this.titulaireId = titulaireId;
	}

	public int getSoldeWallet() {
		return soldeWallet;
	}

	public void setSoldeWallet(int soldeWallet) {
		this.soldeWallet = soldeWallet;
	}
	
	

}
