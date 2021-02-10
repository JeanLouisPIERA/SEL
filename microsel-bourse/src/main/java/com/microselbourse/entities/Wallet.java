package com.microselbourse.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microselbourse.beans.UserBean;

@Entity
@Table(name="wallets")
public class Wallet implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wallet_id", length=5)
	private Long id;
	
	/*
	 * Un wallet est rattaché à un seul adhérent et chaque adhérent n'a qu'un portefeuille
	 * Son accès au portefeuille est sécurisé par son adresse mail identique à celui de son compte adhérent
	 * et un mot de passe qui peut être différent de celui de son compte adhérent
	 * Il existe un seul wallet COUNTERPART ouvert pour le compte du SEL pour enregistrer la contrepartie d'échanges en anomalie
	 */
	@Column(name="titulaire_email")
	private String titulaire_email;
	
	@Column(name="titulaire_password")
	private String titulaire_password;
	
	/*
	 * Un wallet se définit comme un solde modifié par des opérations au débit ou au crédit
	 */
	
	@Column(name="soldeWallet")
	private Integer soldeWallet;
	
	/*
	 * Un wallet enregistre les opérations de multiples transactions 
	 * et une transaction se réalise toujours sur 2 portefeuilles @ManyToMany
	 */
	
	@JsonIgnore 
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "wallets_transactions", 
        joinColumns = { @JoinColumn(name = "wallet_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "transaction_id") }
    )
    private Collection<Transaction> transactions;
	
	
	

}
