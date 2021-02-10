package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="echanges")
public class Echange implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "echange_id", length=5)
	private Long id;
	
	/*
	 * Lorsque un adhérent publie répond à une proposition, le système génère automatiquement un échange
	 * Le système enregistre alors la date d'enregistrement**********************************************************************
	 */
	
	@Column(name="date_enregistrement")
	private LocalDate dateEnregistrement;
	
	/*
	 * A sa création un échange est établi sur la base de la réponse du récepteur et son statut est ENREGISTRE
	 * L'émetteur de la proposition est informé par mail de la réponse et il peut l'accepter (statut CONFIRME) ou la refuser (statut 
	 * ANNULE). Le système renseigne la date de confirmation ou la date de refus
	 * Pour que le statut change avant la date d'échéance, il faut que l'émetteur et le récepteur donnent leur avis (voir ci-dessous)
	 * Après la date d'échéance, le système change le statut si aucun avis n'a été enregistré (statut SUPPRIME), si un seul avis 
	 * a été enregistré et qu'il est VALID (statut échange = FORCEVALID), si un seul avis a été enregistré et qu'il est REFUS
	 *  (statut échange = FORCEREFUS) ou si le solde minimum du compte du payeur est atteint (CONFLIT) 
	 */
	
	@Column(name="statut")
	private EnumStatutEchange statutEchange;
	
	@Column(name="date_confirmation")
	private LocalDate dateConfirmation;
	
	@Column(name="date_annulation")
	private LocalDate dateAnnulation;
	
	/*
	 * Au moment de la création de l'échange, le système renseigne les avis émetteur et récepteur au statut SANS.
	 * Avant la date d'échéance de l'échange, le statut peut évoluer si l'avis émetteur et l'avis récepteur ont été renseignés 
	 * par l'émetteur et le récepteur : si les 2 avis sont VALID (statut échange : CLOTURE), si un avis est VALID et l'autre REFUS 
	 * ou si les 2 avis sont REFUS (statut échange : LITIGE). 
	 * Ces 2 avis passent en ANOMALIE après la date de clôture pour les statuts échange suivants : SUPPRIME, FORCEVALID, FORCEREFUS 
	 * et CONFLIT 
	 * Le système renseigne la dateFin uniquement dans le cas du statut CLOTURE
	 * 
	 */
	
	@Column(name="avis_emetteur")
	private EnumEchangeAvis avisEmetteur;
	
	@Column(name="avis_recepteur")
	private EnumEchangeAvis avisRecepteur;
	
	@Column(name="date_fin")
	private LocalDate dateFin;
	
	/*
	 * L'émetteur et le récepteur peuvent apporter chacun un commentaire et noter la qualité de l'échange au moment où ils 
	 * enregistrent leur avis (VALID ou REFUSE)
	 */
	
	@Column(name="comment_emetteur")
	private String commentaireEmetteur;
	
	@Column(name="comment_recepteur")
	private String commentaireRecepteur;
	
	@Column(name="note_emetteur")
	private EnumEchangeAvis noteEmetteur;
	
	@Column(name="note_recepteur")
	private EnumEchangeAvis noteRecepteur;
	
	/*
	 * La réponse du récepteur déclenche la création de l'échange par le système donc 1 REPONSE = 1 ECHANGE @OneToOne
	 * L'Id du SEL auquel est rattaché un échange INTERSEL est donc celui de l'émetteur de la réponse (le Récepteur) 
	 * et pas l'Id du SEL de l'émetteur de la proposition (l'Emetteur)
	 * Certains statuts avant ou après la date d'échéance déclenchent la création d'une transaction qui débite le compte de l'un et 
	 * crédite le compte de l'autre
	 * Il y donc une relation 1 ECHANGE = 1 TRANSACTION @OneToOne 
	 * Certains échanges n'ont aboutis ne donnent pas lieu à la création d'une transaction
	 */
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reponse_id", referencedColumnName = "reponse_id")
    private Reponse reponse;
	
	@OneToOne(mappedBy = "echanges")
    private Transaction transaction;
	
	
	
	
	

}
