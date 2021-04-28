package com.microselreferentiels.entities;

import java.time.LocalDate;
import java.util.List;

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
@Table(name="typearticles")
public class TypeArticle {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typearticle_id", length=5)
	private Long id;
	
	@Column(name = "titre", length = 25, nullable=false)
	private String typeName;
	
	@Column(name = "description", length = 150)
	private String description;
	
	@Column(name = "date_creation")
	private LocalDate dateCreation; 
	
	@Column(name = "date_archivage")
	private LocalDate dateArchivage;
	
	@JsonIgnore
	@OneToMany(mappedBy="typeArticle", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Article> articles;

	public TypeArticle() {
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

	

	public LocalDate getDateArchivage() {
		return dateArchivage;
	}

	public void setDateArchivage(LocalDate dateArchivage) {
		this.dateArchivage = dateArchivage;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	
	
	

}
