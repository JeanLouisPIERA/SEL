package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="propositions")
public class Proposition implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proposition_id", length=5)
	private Long id;
	
	/*
	 * Le système renseigne l'id de l'émetteur au moment de la création de la proposition
	 * L'Id du SEL où est publiée la proposition n'est pas un attribut car on considère que par défaut c'est toujours 
	 * celui du SEL de l'émetteur 
	 * Cet id est vérifié avec le UserBean en consommant le microservice adhérent****************************************************
	 */
	
	@Column(name = "emetteurId", length = 10, nullable=false)
	private Long emetteurId; 
	
	/*
	 * L'émetteur renseigne l'enumTradeType au moment de la publication de son offre
	 * L'enumTradeType gère 2 positions pour la proposition : OFFRE ou DEMANDE ******************************************************
	 */
	
	@Column(name="type")
	private EnumTradeType enumTradeType; 
	
	/*
	 * L'émetteur renseigne aussi le titre, la description écrite ou par image, la ville et son code postal de sa proposition.
	 * Il renseigne sa valeur 
	 * Le système renseigne la monnaie d'expression dans la monnaie du SEL de l'émetteur (cas interSEL) 
	 * L'émetteur renseigne la date de fin de validité de sa proposition et la date d'échéance à laquelle l'échange doit être réalisé au plus tard
	 * L'émetteur indique aussi à quelle catégorie d'échange appartient sa proposition (@ManyToOne)**********************************
	 */
	
	@Column(name = "titre", length = 100, nullable=false)
	private String titre; 
	
	@Column(name = "description", length = 255, nullable=false)
	private String description; 
	
	@Column(name = "image", length = 30, nullable=true)
	private String image; 
	
	@Column(name = "ville", length = 50, nullable=false)
	private String ville; 
	
	@Column(name = "codePostal", length = 6, nullable=false)
	private Integer codePostal;
	
	@Column(name = "valeur", length = 3, nullable=false)
	private Integer valeur;
	
	/*
	 * @Column(name = "monnaie", length = 10, nullable=false) private MonnaieBean
	 * monnaie;
	 */
	
	@Column(name = "dateFin", length = 25, nullable=false)
	private LocalDate dateFin; 
	
	@Column(name = "dateEcheance", length = 25, nullable=false)
	private LocalDate dateEcheance; 
	
	@ManyToOne 
	@JoinColumn(name="categorie_id")
	private Categorie categorie;
	
	/*
	 * Le système renseigne la date de publication de la proposition ***************************************************************
	 */
	
	@Column(name = "dateDebut", length = 25, nullable=false)
	private LocalDate dateDebut; 
	
	
	/*
	 * Chaque proposition (OFFRE ou DEMANDE) d'un émetteur peut être mise en relation 
	 * avec une ou plusieurs réponses d'un ou plusieurs récepteurs @OneToMany
	 * Ce n'est pas la publication d'une proposition qui détermine l'échange 
	 * L'échange est créé dès qu'il y a publication d'une réponse à une proposition ************************************************
	 */
	
	@JsonIgnore
	@OneToMany(mappedBy="propositions", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Collection<Reponse> reponses;


	public Proposition() {
		super();
		
	}
	

	public Proposition(Long id, Long emetteurId, EnumTradeType enumTradeType, String titre, String description,
			String ville, Integer codePostal, Integer valeur, LocalDate dateFin, LocalDate dateEcheance,
			Categorie categorie, LocalDate dateDebut) {
		super();
		this.id = id;
		this.emetteurId = emetteurId;
		this.enumTradeType = enumTradeType;
		this.titre = titre;
		this.description = description;
		this.ville = ville;
		this.codePostal = codePostal;
		this.valeur = valeur;
		this.dateFin = dateFin;
		this.dateEcheance = dateEcheance;
		this.categorie = categorie;
		this.dateDebut = dateDebut;
	}







	public Proposition(Long id, Long emetteurId, EnumTradeType enumTradeType, String titre, String description,
			String image, String ville, Integer codePostal, Integer valeur, LocalDate dateFin, LocalDate dateEcheance,
			Categorie categorie, LocalDate dateDebut, Collection<Reponse> reponses) {
		super();
		this.id = id;
		this.emetteurId = emetteurId;
		this.enumTradeType = enumTradeType;
		this.titre = titre;
		this.description = description;
		this.image = image;
		this.ville = ville;
		this.codePostal = codePostal;
		this.valeur = valeur;
		this.dateFin = dateFin;
		this.dateEcheance = dateEcheance;
		this.categorie = categorie;
		this.dateDebut = dateDebut;
		this.reponses = reponses;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getEmetteurId() {
		return emetteurId;
	}


	public void setEmetteurId(Long emetteurId) {
		this.emetteurId = emetteurId;
	}

	public EnumTradeType getEnumTradeType() {
		return enumTradeType;
	}


	public void setEnumTradeType(EnumTradeType enumTradeType) {
		this.enumTradeType = enumTradeType;
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


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public LocalDate getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Collection<Reponse> getReponses() {
		return reponses;
	}


	public void setReponses(Collection<Reponse> reponses) {
		this.reponses = reponses;
	}

	@Override
	public String toString() {
		return "Proposition [id=" + id + ", emetteurId=" + emetteurId + ", enumTradeType=" + enumTradeType + ", titre="
				+ titre + ", description=" + description + ", image=" + image + ", ville=" + ville + ", codePostal="
				+ codePostal + ", valeur=" + valeur + ", dateFin=" + dateFin + ", dateEcheance=" + dateEcheance
				+ ", categorie=" + categorie + ", dateDebut=" + dateDebut + ", reponses=" + reponses + "]";
	}



}
