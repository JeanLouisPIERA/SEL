package com.microselwebclientjspui.objets;

import java.util.List;

public class Wallet {
	
	private Long id;
	
	private Long titulaireId;
	
	private int soldeWallet;
	
	private List<Transaction> transactions; 

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

	public int getSoldeWallet() {
		return soldeWallet;
	}

	public void setSoldeWallet(int soldeWallet) {
		this.soldeWallet = soldeWallet;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	

}
