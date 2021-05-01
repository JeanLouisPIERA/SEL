package com.microselbourse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "wallets")
public class Wallet implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id", length = 5)
	private Long id;

	/*
	 * Un wallet est rattaché à un seul adhérent et chaque adhérent n'a qu'un
	 * portefeuille Il existe un seul wallet COUNTERPART ouvert pour le compte du
	 * SEL pour enregistrer la contrepartie d'échanges en conflit
	 */
	@Column(name = "titulaire_id", length = 5, nullable = false, unique = true)
	private String titulaireId;

	@Column(name = "titulaire_username", length = 25, nullable = false)
	private String titulaireUsername;

	/*
	 * Un wallet se définit comme un solde modifié par des opérations au débit ou au
	 * crédit
	 */

	@Column(name = "solde_wallet", length = 6)
	private Integer soldeWallet;

	/*
	 * Un wallet enregistre les opérations de multiples transactions et une
	 * transaction se réalise toujours sur 2 portefeuilles @ManyToMany
	 */

	@JsonIgnore
	@ManyToMany(mappedBy = "wallets")
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public Wallet() {
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

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

}
