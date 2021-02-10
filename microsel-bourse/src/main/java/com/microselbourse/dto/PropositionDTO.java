package com.microselbourse.dto;



import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumTradeType;

import io.swagger.annotations.ApiModelProperty;

public class PropositionDTO {
	
	
	@ApiModelProperty(notes= "indique OFFRE ou DEMANDE - saisie obligatoire")
	@NotEmpty(message="Merci d'indiquer si votre proposition est une OFFRE ou une DEMANDE")
	private String enumTradeTypeCode; 
	
	@ApiModelProperty(notes= "indique la catégorie de la proposition(Bricolage, Jardinage, Brocante ...) - saisie obligatoire")
	@NotEmpty(message="Merci d'indiquer dans quelle catégorie vous publiez votre proposition")
	private Long idCategorie; 
	
	@ApiModelProperty(notes= "Titre de votre proposition")
	@NotEmpty(message="Merci de saisir le titre de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères")
	private String titre; 
	
	@ApiModelProperty(notes= "Description de votre proposition")
	@NotEmpty(message="Merci de saisir la description  de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères")
	private String description; 
	
	@ApiModelProperty(notes= "Image illustrant votre proposition")
	@Size(min = 5, max = 30, message = "Le nom de votre fichier image ne doit pas dépasser 30 caractères")
	private String image; 
	
	@ApiModelProperty(notes= "Ville de réalisation de l'échange que vous proposez")
	@NotEmpty(message="Merci de saisir la ville de réalisation de l'échange de votre proposition")
	@Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères")
	private String ville; 
	
	@ApiModelProperty(notes= "Département de la ville")
	@NotEmpty(message="Merci de saisir les références du département")
	@Size(min = 5, max = 6, message = "La longueur ne peut excéder 5 caractères")
	private Integer codePostal;
	
	@ApiModelProperty(notes= "Valeur dans la monnaie du SEL de l'échange que vous proposez")
	@NotEmpty(message="Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez")
	@Size(min = 1, max = 3, message = "La taille de votre valeur ne peut excéder 999")
	private Integer valeur;
	
	@ApiModelProperty(notes= "Date de fin de la publication")
	@NotEmpty(message="Merci de saisir la date de fin de la publication de votre proposition")
	private LocalDate dateFin;
	
	@ApiModelProperty(notes= "Date d'échéance pour la réalisation de l'échange que vous proposez")
	@NotEmpty(message="Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez")
	private LocalDate dateEcheance;

	public PropositionDTO() {
		super();
	}
	
	public PropositionDTO(
			@NotEmpty(message = "Merci d'indiquer si votre proposition est une OFFRE ou une DEMANDE") String enumTradeTypeCode,
			@NotEmpty(message = "Merci d'indiquer dans quelle catégorie vous publiez votre proposition") Long idCategorie,
			@NotEmpty(message = "Merci de saisir le titre de votre proposition") @Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères") String titre,
			@NotEmpty(message = "Merci de saisir la description  de votre proposition") @Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères") String description,
			@NotEmpty(message = "Merci de saisir la ville de réalisation de l'échange de votre proposition") @Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères") String ville,
			@NotEmpty(message = "Merci de saisir les références du département") @Size(min = 5, max = 6, message = "La longueur ne peut excéder 5 caractères") Integer codePostal,
			@NotEmpty(message = "Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez") @Size(min = 1, max = 3, message = "La taille de votre valeur ne peut excéder 999") Integer valeur,
			@NotEmpty(message = "Merci de saisir la date de fin de la publication de votre proposition") LocalDate dateFin,
			@NotEmpty(message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez") LocalDate dateEcheance) {
		super();
		this.enumTradeTypeCode = enumTradeTypeCode;
		this.idCategorie = idCategorie;
		this.titre = titre;
		this.description = description;
		this.ville = ville;
		this.codePostal = codePostal;
		this.valeur = valeur;
		this.dateFin = dateFin;
		this.dateEcheance = dateEcheance;
	}


	public String getEnumTradeTypeCode() {
		return enumTradeTypeCode;
	}

	public void setEnumTradeTypeCode(String enumTradeTypeCode) {
		this.enumTradeTypeCode = enumTradeTypeCode;
	}

	public Long getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(Long idCategorie) {
		this.idCategorie = idCategorie;
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
	
	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public LocalDate getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(LocalDate dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	@Override
	public String toString() {
		return "PropositionDTO [enumTradeTypeCode=" + enumTradeTypeCode + ", idCategorie=" + idCategorie + ", titre="
				+ titre + ", description=" + description + ", image=" + image + ", ville=" + ville + ", codePostal="
				+ codePostal + ", valeur=" + valeur + ", dateFin=" + dateFin + ", dateEcheance=" + dateEcheance + "]";
	}


}
