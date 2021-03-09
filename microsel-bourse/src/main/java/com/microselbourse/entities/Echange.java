package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="echanges")
public class Echange implements Serializable {
	
	@Id
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
	
	@Column(name="emetteur_id")
	private Long emetteurId;
	
	@Column(name="recepteur_id")
	private Long recepteurId;
	
	@Column(name="emetteur_username")
	private String emetteurUsername;
	
	@Column(name="recepteur_username")
	private String recepteurUsername;
	
	@Column(name="date_echeance")
	private LocalDate dateEcheance;
	
	@Column(name="titre")
	private String titre;
	
	@Column(name="emetteur_mail")
	private String emetteurMail;
	
	@Column(name="recepteur_mail")
	private String recepteurMail;
	
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
	
	@Column(name="comment_emetteur", length= 130)
	private String commentaireEmetteur;
	
	@Column(name="comment_recepteur", length = 130)
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
	
	
	/*
	 * @OneToOne
	 * 
	 * @MapsId
	 * 
	 * @JoinColumn(name = "reponse_id") private Reponse reponse;
	 */
	 
	
	  @OneToOne(cascade = CascadeType.ALL)
	  @PrimaryKeyJoinColumn	
	  /* @OneToOne(mappedBy = "echange") */
	  private Transaction transaction;
	  
	  
	  @JsonIgnore
	  @OneToMany(mappedBy="echange", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	  private List<Blocage> blocages;
	  
	  @JsonIgnore
		@OneToMany(mappedBy="echange", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
		private List<Evaluation> evaluations;


	  
	public Echange() {
		super();
		
	}


	public Echange(Long id, LocalDate dateEnregistrement, Long emetteurId, Long recepteurId, String emetteurUsername,
			String recepteurUsername, LocalDate dateEcheance, String titre, String emetteurMail, String recepteurMail,
			EnumStatutEchange statutEchange, LocalDate dateConfirmation, LocalDate dateAnnulation,
			EnumEchangeAvis avisEmetteur, EnumEchangeAvis avisRecepteur, LocalDate dateFin, String commentaireEmetteur,
			String commentaireRecepteur, EnumEchangeAvis noteEmetteur, EnumEchangeAvis noteRecepteur,
			Transaction transaction) {
		super();
		this.id = id;
		this.dateEnregistrement = dateEnregistrement;
		this.emetteurId = emetteurId;
		this.recepteurId = recepteurId;
		this.emetteurUsername = emetteurUsername;
		this.recepteurUsername = recepteurUsername;
		this.dateEcheance = dateEcheance;
		this.titre = titre;
		this.emetteurMail = emetteurMail;
		this.recepteurMail = recepteurMail;
		this.statutEchange = statutEchange;
		this.dateConfirmation = dateConfirmation;
		this.dateAnnulation = dateAnnulation;
		this.avisEmetteur = avisEmetteur;
		this.avisRecepteur = avisRecepteur;
		this.dateFin = dateFin;
		this.commentaireEmetteur = commentaireEmetteur;
		this.commentaireRecepteur = commentaireRecepteur;
		this.noteEmetteur = noteEmetteur;
		this.noteRecepteur = noteRecepteur;
		this.transaction = transaction;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(LocalDate dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public EnumStatutEchange getStatutEchange() {
		return statutEchange;
	}

	public void setStatutEchange(EnumStatutEchange statutEchange) {
		this.statutEchange = statutEchange;
	}

	public LocalDate getDateConfirmation() {
		return dateConfirmation;
	}

	public void setDateConfirmation(LocalDate dateConfirmation) {
		this.dateConfirmation = dateConfirmation;
	}

	public LocalDate getDateAnnulation() {
		return dateAnnulation;
	}

	public void setDateAnnulation(LocalDate dateAnnulation) {
		this.dateAnnulation = dateAnnulation;
	}

	public EnumEchangeAvis getAvisEmetteur() {
		return avisEmetteur;
	}

	public void setAvisEmetteur(EnumEchangeAvis avisEmetteur) {
		this.avisEmetteur = avisEmetteur;
	}

	public EnumEchangeAvis getAvisRecepteur() {
		return avisRecepteur;
	}

	public void setAvisRecepteur(EnumEchangeAvis avisRecepteur) {
		this.avisRecepteur = avisRecepteur;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public String getCommentaireEmetteur() {
		return commentaireEmetteur;
	}

	public void setCommentaireEmetteur(String commentaireEmetteur) {
		this.commentaireEmetteur = commentaireEmetteur;
	}

	public String getCommentaireRecepteur() {
		return commentaireRecepteur;
	}

	public void setCommentaireRecepteur(String commentaireRecepteur) {
		this.commentaireRecepteur = commentaireRecepteur;
	}

	public EnumEchangeAvis getNoteEmetteur() {
		return noteEmetteur;
	}

	public void setNoteEmetteur(EnumEchangeAvis noteEmetteur) {
		this.noteEmetteur = noteEmetteur;
	}

	public EnumEchangeAvis getNoteRecepteur() {
		return noteRecepteur;
	}

	public void setNoteRecepteur(EnumEchangeAvis noteRecepteur) {
		this.noteRecepteur = noteRecepteur;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


	public Long getEmetteurId() {
		return emetteurId;
	}


	public void setEmetteurId(Long emetteurId) {
		this.emetteurId = emetteurId;
	}


	public Long getRecepteurId() {
		return recepteurId;
	}


	public void setRecepteurId(Long recepteurId) {
		this.recepteurId = recepteurId;
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

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getEmetteurMail() {
		return emetteurMail;
	}

	public void setEmetteurMail(String emetteurMail) {
		this.emetteurMail = emetteurMail;
	}

	public String getRecepteurMail() {
		return recepteurMail;
	}

	public void setRecepteurMail(String recepteurMail) {
		this.recepteurMail = recepteurMail;
	}


	public List<Blocage> getBlocages() {
		return blocages;
	}


	public void setBlocages(List<Blocage> blocages) {
		this.blocages = blocages;
	}


	public List<Evaluation> getEvaluations() {
		return evaluations;
	}


	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	
	
	 
}
