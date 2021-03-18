package com.microselwebclientjspui.objets;

import java.time.LocalDate;


public class TypeProposition {
	
	
	private Long id;
	
	
	private String typeName;
	
	
	private String description;
	
	
	private LocalDate dateCreation; 
	
	
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
