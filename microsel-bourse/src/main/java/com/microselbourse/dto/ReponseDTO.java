package com.microselbourse.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.microselbourse.entities.EnumTradeType;

import io.swagger.annotations.ApiModelProperty;

public class ReponseDTO {
	
	
	@ApiModelProperty(notes= "indique l'identifiant de l'adhérent récepteur - saisie obligatoire")
	@NotNull(message="Merci d'indiquer l'identifiant de l'adhérent qui répond à l'OFFRE ou à la DEMANDE d'origine")
	private Long recepteurId; 

	private String titre; 
	private String description; 
	private String image; 
	private String ville;  
	private Integer codePostal; 
	private Integer valeur; 
	private LocalDate dateEcheance; 

	
	public ReponseDTO() {
		super();
		
	}

	public Long getRecepteurId() {
		return recepteurId;
	}

	public void setRecepteurId(Long recepteurId) {
		this.recepteurId = recepteurId;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public Integer getValeur() {
		return valeur;
	}

	public void setValeur(Integer valeur) {
		this.valeur = valeur;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	
	
	

}
