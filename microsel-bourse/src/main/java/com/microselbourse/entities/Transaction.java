package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="transactions")
public class Transaction implements Serializable {
	
	@Id
	@Column(name = "transaction_id", length=5)
	private Long id;
	
	/*
	 * Une transaction se définit comme une opération double du même montant : 
	 * une opération au crédit du compte de celui qui encaisse 
	 * et une opération au débit de celui qui paie. 
	 * On retient donc le montant comme attribut de la transaction : ce montant est obligatoirement celui de la réponse
	 * ***************************************************************************************************************************
	 */
	
	@Column(name="montant", length = 3)
	private Integer montant;
	
	/*
	 * Le système crée la transaction quand l'échange passe à certains statuts avant ou après l'échéance
	 * La date de création de la transaction est enregistrée
	 * ****************************************************************************************************************************
	 */
	
	@Column(name="date_transaction")
	private LocalDate dateTransaction;
	
	/*
	 * Il n'y a qu'une transaction au maximum par échange (certains échanges en anomalie n'ont pas de transaction) @OneToOne
	 * L'Id du SEL auquel est rattachée la transction qui conclut un échange INTERSEL est donc celui de l'émetteur de la réponse 
	 * (le Récepteur) et pas l'Id du SEL de l'émetteur de la proposition (l'Emetteur)
	 * Une transaction inscrit une opération au crédit du portefeuille monétaire (WALLET) d'une des 2 parties et une au débit 
	 * du wallet de l'autre partie @OneToMany
	 */
	
	/*
	 * @OneToOne(cascade = CascadeType.ALL)
	 * 
	 * @JoinColumn(name = "echange_id", referencedColumnName = "echange_id") private
	 * Echange echange;
	 */
	
	@JsonIgnore 
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "transactions_wallets", 
        joinColumns = { @JoinColumn(name = "wallet_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "transaction_id") }
    )
    private Collection<Wallet> wallets;
}
