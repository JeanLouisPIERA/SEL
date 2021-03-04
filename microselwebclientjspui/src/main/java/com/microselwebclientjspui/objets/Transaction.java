package com.microselwebclientjspui.objets;

import java.time.LocalDate;
import java.util.List;

public class Transaction {
	
	private Long id;
	private int montant;
	private LocalDate dateTransaction;
	private List<Wallet> wallets;
	
	
	public Transaction() {
		super();
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public int getMontant() {
		return montant;
	}


	public void setMontant(int montant) {
		this.montant = montant;
	}


	public LocalDate getDateTransaction() {
		return dateTransaction;
	}


	public void setDateTransaction(LocalDate dateTransaction) {
		this.dateTransaction = dateTransaction;
	}


	public List<Wallet> getWallets() {
		return wallets;
	}


	public void setWallets(List<Wallet> wallets) {
		this.wallets = wallets;
	}


	
	
	
	

}
