package com.microselbourse.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumTradeType;

import io.swagger.annotations.ApiModelProperty;

public class PropositionDTO {

	@ApiModelProperty(notes = "indique l'identifiant de l'adhérent émetteur - saisie obligatoire")
	@NotNull(message = "Merci d'indiquer l'identifiant de l'adhérent à l'origine de l'OFFRE ou de la DEMANDE")
	private String emetteurId;

	@ApiModelProperty(notes = "indique le username de l'adhérent émetteur - saisie obligatoire")
	@NotNull(message = "Merci d'indiquer le username de l'adhérent à l'origine de l'OFFRE ou de la DEMANDE")
	private String emetteurUsername;

	@ApiModelProperty(notes = "indique OFFRE ou DEMANDE - saisie obligatoire")
	@NotEmpty(message = "Merci d'indiquer si votre proposition est une OFFRE ou une DEMANDE")
	private String enumTradeTypeCode;

	@ApiModelProperty(notes = "indique la catégorie de la proposition(Bricolage, Jardinage, Brocante ...) - saisie obligatoire")
	@NotNull(message = "Merci d'indiquer dans quelle catégorie vous publiez votre proposition")
	private String categorieName;

	@ApiModelProperty(notes = "Titre de votre proposition")
	@NotEmpty(message = "Merci de saisir le titre de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères")
	private String titre;

	@ApiModelProperty(notes = "Description de votre proposition")
	@NotEmpty(message = "Merci de saisir la description  de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères")
	private String description;

	@ApiModelProperty(notes = "Image illustrant votre proposition")
	@Size(min = 5, max = 30, message = "Le nom de votre fichier image ne doit pas dépasser 30 caractères")
	private String image;

	@ApiModelProperty(notes = "Ville de réalisation de l'échange que vous proposez")
	@NotEmpty(message = "Merci de saisir la ville de réalisation de l'échange de votre proposition")
	@Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères")
	private String ville;

	@ApiModelProperty(notes = "Département de la ville")
	@NotNull(message = "Merci de saisir les références du département")
	@Range(min = 01000, max = 99999, message = "Votre code postal n'est pas correctement saisi")
	private Integer codePostal;

	@ApiModelProperty(notes = "Valeur dans la monnaie du SEL de l'échange que vous proposez")
	@NotNull(message = "Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez")
	@Range(min = 1, max = 999, message = "La valeur de votre proposition ne peut excéder 999 unités")
	private Integer valeur;

	@ApiModelProperty(notes = "Date de fin de la publication")
	//@Future (message = "Merci de saisir une date de fin de publication de votre proposition postérieure à la date du jour")
	@NotNull(message = "Merci de saisir la date de fin de la publication de votre proposition")
	private String dateFin;

	@ApiModelProperty(notes = "Date d'échéance pour la réalisation de l'échange que vous proposez")
	//@Future (message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez postérieure à la date du jour")
	@NotNull(message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez")
	private String dateEcheance;
	
	
	
	public PropositionDTO() {
		super();
	}

	public String getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(String emetteurId) {
		this.emetteurId = emetteurId;
	}

	public String getEmetteurUsername() {
		return emetteurUsername;
	}

	public void setEmetteurUsername(String emetteurUsername) {
		this.emetteurUsername = emetteurUsername;
	}

	public String getEnumTradeTypeCode() {
		return enumTradeTypeCode;
	}

	public void setEnumTradeTypeCode(String enumTradeTypeCode) {
		this.enumTradeTypeCode = enumTradeTypeCode;
	}

	public String getCategorieName() {
		return categorieName;
	}

	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
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

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(String dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

}
