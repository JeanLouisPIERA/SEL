package com.microselbourse.dto;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class PropositionUpdateDTO {

	@NotEmpty(message = "Merci de saisir le titre de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères")
	private String titre;

	@NotEmpty(message = "Merci de saisir la description  de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères")
	private String description;

	@Size(min = 5, max = 30, message = "Le nom de votre fichier image ne doit pas dépasser 30 caractères")
	private String image;

	@NotEmpty(message = "Merci de saisir la ville de réalisation de l'échange de votre proposition")
	@Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères")
	private String ville;

	@NotNull(message = "Merci de saisir les références du département")
	@Range(min = 01000, max = 99999, message = "Votre code postal n'est pas correctement saisi")
	private Integer codePostal;

	@NotNull(message = "Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez")
	@Range(min = 1, max = 999, message = "La valeur de votre proposition ne peut excéder 999 unités")
	private Integer valeur;

	/* @DateTimeFormat(pattern = "yyyy-MM-dd") */
	//@Future(message = "Merci de saisir une date de fin de publication de votre proposition postérieure à la date du jour")
	@NotNull(message = "Merci de saisir la date de fin de la publication de votre proposition")
	private String dateFin;

	/* @DateTimeFormat(pattern = "yyyy-MM-dd") */
	//@Future(message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez postérieure à la date du jour")
	@NotNull(message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez")
	private String dateEcheance;

	public PropositionUpdateDTO() {
		super();
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



	@Override
	public String toString() {
		return "PropositionUpdateDTO [titre=" + titre + ", description=" + description + ", image=" + image + ", ville="
				+ ville + ", codePostal=" + codePostal + ", valeur=" + valeur + ", dateFin=" + dateFin
				+ ", dateEcheance=" + dateEcheance + "]";
	}

	

}
