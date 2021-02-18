package com.microselwebclient_ui.dto;



import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;


public class PropositionDTO {
	
	@NotNull(message="Merci d'indiquer l'identifiant de l'adhérent à l'origine de l'OFFRE ou de la DEMANDE")
	private Long emetteurId; 
	
	@NotEmpty(message="Merci d'indiquer si votre proposition est une OFFRE ou une DEMANDE")
	private String enumTradeTypeCode; 
	
	@NotNull(message="Merci d'indiquer dans quelle catégorie vous publiez votre proposition")
	private Long categorieId; 
	
	@NotEmpty(message="Merci de saisir le titre de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères")
	private String titre; 
	
	@NotEmpty(message="Merci de saisir la description  de votre proposition")
	@Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères")
	private String description; 
	
	@Size(min = 5, max = 30, message = "Le nom de votre fichier image ne doit pas dépasser 30 caractères")
	private String image; 
	
	@NotEmpty(message="Merci de saisir la ville de réalisation de l'échange de votre proposition")
	@Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères")
	private String ville; 
	
	@NotNull(message="Merci de saisir les références du département")
	@Range(min = 01000, max = 99999, message = "Votre code postal n'est pas correctement saisi")
	private Integer codePostal;
	
	@NotNull(message="Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez")
	@Range(min = 1, max = 999, message = "La valeur de votre proposition ne peut excéder 999 unités")
	private Integer valeur;
	
	@Future(message="Merci de saisir la date de fin de la publication de votre proposition")
	private LocalDate dateFin;
	
	@Future(message="Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez")
	private LocalDate dateEcheance;

	public PropositionDTO() {
		super();
	}
	
	public PropositionDTO(
			@NotEmpty(message = "Merci d'indiquer l'identifiant de l'adhérent à l'origine de l'OFFRE ou de la DEMANDE") Long emetteurId,
			@NotEmpty(message = "Merci d'indiquer si votre proposition est une OFFRE ou une DEMANDE") String enumTradeTypeCode,
			@NotEmpty(message = "Merci d'indiquer dans quelle catégorie vous publiez votre proposition") Long categorieId,
			@NotEmpty(message = "Merci de saisir le titre de votre proposition") @Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 100 caractères") String titre,
			@NotEmpty(message = "Merci de saisir la description  de votre proposition") @Size(min = 5, max = 100, message = "La taille de votre titre doit être comprise entre 5 et 255 caractères") String description,
			@Size(min = 5, max = 30, message = "Le nom de votre fichier image ne doit pas dépasser 30 caractères") String image,
			@NotEmpty(message = "Merci de saisir la ville de réalisation de l'échange de votre proposition") @Size(min = 5, max = 50, message = "La taille de nom de la ville ne doit pas dépasser 50 caractères") String ville,
			@NotEmpty(message = "Merci de saisir les références du département") @Size(min = 5, max = 6, message = "La longueur ne peut excéder 5 caractères") Integer codePostal,
			@NotEmpty(message = "Merci de saisir la valeur dans la monnaie du SEL de l'échange que vous proposez") @Size(min = 1, max = 3, message = "La taille de votre valeur ne peut excéder 999") Integer valeur,
			@NotEmpty(message = "Merci de saisir la date de fin de la publication de votre proposition") LocalDate dateFin,
			@NotEmpty(message = "Merci de saisir la date d'échance pour la réalisation de l'échange que vous proposez") LocalDate dateEcheance) {
		super();
		this.emetteurId = emetteurId;
		this.enumTradeTypeCode = enumTradeTypeCode;
		this.categorieId = categorieId;
		this.titre = titre;
		this.description = description;
		this.image = image;
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
	
	public Long getEmetteurId() {
		return emetteurId;
	}

	public void setEmetteurId(Long emetteurId) {
		this.emetteurId = emetteurId;
	}





	public Long getCategorieId() {
		return categorieId;
	}





	public void setCategorieId(Long categorieId) {
		this.categorieId = categorieId;
	}





	@Override
	public String toString() {
		return "PropositionDTO [emetteurId=" + emetteurId + ", enumTradeTypeCode=" + enumTradeTypeCode
				+ ", categorieId=" + categorieId + ", titre=" + titre + ", description=" + description + ", image="
				+ image + ", ville=" + ville + ", codePostal=" + codePostal + ", valeur=" + valeur + ", dateFin="
				+ dateFin + ", dateEcheance=" + dateEcheance + "]";
	}

	

}
