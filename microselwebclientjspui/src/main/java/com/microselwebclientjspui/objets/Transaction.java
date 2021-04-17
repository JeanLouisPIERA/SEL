package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Transaction {

	private Long id;
	private int montant;
	private LocalDate dateTransaction;
	private String titreEchange;
	private String emetteurUsername;
	private String recepteurUsername;

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

	public String getTitreEchange() {
		return titreEchange;
	}

	public void setTitreEchange(String titreEchange) {
		this.titreEchange = titreEchange;
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

}
