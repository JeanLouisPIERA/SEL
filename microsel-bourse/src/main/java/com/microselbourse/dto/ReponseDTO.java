package com.microselbourse.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.microselbourse.entities.EnumTradeType;

import io.swagger.annotations.ApiModelProperty;

public class ReponseDTO {

	@ApiModelProperty(notes = "indique l'identifiant de l'adhérent récepteur - saisie obligatoire")
	@NotNull(message = "Merci d'indiquer l'identifiant de l'adhérent qui répond à l'OFFRE ou à la DEMANDE d'origine")
	private String recepteurId;

	private String recepteurUsername;

	private String titre;
	private String description;
	private String image;
	private String ville;
	private Integer codePostal;
	private Integer valeur;
	private String dateEcheance;

	public ReponseDTO() {
		super();

	}

	public String getRecepteurId() {
		return recepteurId;
	}

	public void setRecepteurId(String recepteurId) {
		this.recepteurId = recepteurId;
	}

	public String getRecepteurUsername() {
		return recepteurUsername;
	}

	public void setRecepteurUsername(String recepteurUSername) {
		this.recepteurUsername = recepteurUSername;
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

	public String getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(String dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

}
