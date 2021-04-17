package com.microselwebclientjspui.objets;

import java.time.LocalDate;

public class Echange {

	private Long id;
	private LocalDate dateEnregistrement;
	private String emetteurId;
	private String recepteurId;
	private String emetteurUsername;
	private String recepteurUsername;
	private LocalDate dateEcheance;
	private String titre;
	private String emetteurMail;
	private String recepteurMail;
	private EnumStatutEchange statutEchange;
	private LocalDate dateConfirmation;
	private LocalDate dateAnnulation;
	private EnumEchangeAvis avisEmetteur;
	private EnumEchangeAvis avisRecepteur;
	private LocalDate dateFin;
	private String commentaireEmetteur;
	private String commentaireRecepteur;
	private EnumEchangeAvis noteEmetteur;
	private EnumEchangeAvis noteRecepteur;
	private Transaction transaction;

	public Echange() {
		super();
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

	public String getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(String emetteurId) {
		this.emetteurId = emetteurId;
	}

	public String getRecepteurId() {
		return recepteurId;
	}

	public void setRecepteurId(String recepteurId) {
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

}
