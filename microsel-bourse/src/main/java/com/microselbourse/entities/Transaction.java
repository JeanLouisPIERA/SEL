package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

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
	
	@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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
	
	/*
	 * On rajoute ces 3 attributs pour conserver une partie de l'info sur l'échange @OneToOne unidirectionnel
	 */
	
	@Column(name="titre_echange", length = 100, nullable=false)
	private String titreEchange;
	
	@Column(name="emetteur_username")
	private String emetteurUsername;
	
	@Column(name="recepteur_username")
	private String recepteurUsername;
	
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "transactions_wallets", 
        joinColumns = { @JoinColumn(name = "transaction_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "wallet_id") }
    )
    private List<Wallet> wallets = new ArrayList<Wallet>();

	public Transaction() {
		super();
		
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMontant() {
		return montant;
	}

	public void setMontant(Integer montant) {
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



	public List<Wallet> getWallets() {
		return wallets;
	}

	public void setWallets(List<Wallet> wallets) {
		this.wallets = wallets;
	}





	

	
	
	
	
	
}
