package com.microselreferentiels.entities;

import java.time.LocalDate;



import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="typepropositions")
public class TypeProposition {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typeproposition_id", length=5)
	private Long id;
	
	@Column(name = "titre", length = 25, nullable=false)
	private String typeName;
	
	@Column(name = "description", length = 150)
	private String description;
	
	@Column(name = "date_creation")
	private LocalDate dateCreation; 
	
	@Column(name = "date_last_update")
	private LocalDate dateLastUpdate;
	
	
	public TypeProposition() {
		super();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDate dateCreation) {
		this.dateCreation = dateCreation;
	}

	public LocalDate getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateLastUpdate(LocalDate dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	

}
