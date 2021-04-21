package com.microselbourse.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="categories")
public class Categorie implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "categorie_id", length=5)
	private Long id;

	@Column(name = "name", length = 25, nullable=false)
	private EnumCategorie name;
	
	/*
	 * Seule la proposition renseigne la catégorie. 
	 * La réponse et l'échange ne renseigne pas la catégorie
	 * La réponse fait référence à la proposition et l'échange fait référence à la réponse
	 */
	
	@JsonIgnore
	@OneToMany(mappedBy="categorie")
	private Collection<Proposition> propositions;

	public Categorie() {
		super();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EnumCategorie getName() {
		return name;
	}

	public void setName(EnumCategorie name) {
		this.name = name;
	}

	public Collection<Proposition> getPropositions() {
		return propositions;
	}

	public void setPropositions(Collection<Proposition> propositions) {
		this.propositions = propositions;
	}


	

	


}
