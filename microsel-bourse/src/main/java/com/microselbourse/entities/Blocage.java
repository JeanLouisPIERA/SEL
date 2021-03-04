package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name= "blocages")
public class Blocage implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blocage_id", length=5)
	private Long id;
	
	@Column(name = "adherent_id", length = 5, nullable=false)
	private Long adherentId; 
	
	@Column(name="date_debut_blocage")
	private LocalDate dateDebutBlocage;
	
	@Column(name="date_fin_blocage")
	private LocalDate dateFinBlocage;
	
	@Column(name="statut_blocage")
	private EnumStatutBlocage statutBlocage;
	
	@ManyToOne 
	@JoinColumn(name="echange_id")
	private Echange echange;

	public Blocage() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdherentId() {
		return adherentId;
	}

	public void setAdherentId(Long adherentId) {
		this.adherentId = adherentId;
	}

	public LocalDate getDateDebutBlocage() {
		return dateDebutBlocage;
	}

	public void setDateDebutBlocage(LocalDate dateDebutBlocage) {
		this.dateDebutBlocage = dateDebutBlocage;
	}

	public LocalDate getDateFinBlocage() {
		return dateFinBlocage;
	}

	public void setDateFinBlocage(LocalDate dateFinBlocage) {
		this.dateFinBlocage = dateFinBlocage;
	}

	public EnumStatutBlocage getStatutBlocage() {
		return statutBlocage;
	}

	public void setStatutBlocage(EnumStatutBlocage statutBlocage) {
		this.statutBlocage = statutBlocage;
	}

	public Echange getEchange() {
		return echange;
	}

	public void setEchange(Echange echange) {
		this.echange = echange;
	} 
	
	
	

}
