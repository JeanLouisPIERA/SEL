package com.microselwebclientjspui.objets;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
	
	private Long id;
	
	private Long titulaireId;
	
	private String titulaireUsername;
	
	private Integer soldeWallet;
	

	public Wallet() {
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
