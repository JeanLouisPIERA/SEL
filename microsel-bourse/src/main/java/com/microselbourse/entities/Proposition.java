package com.microselbourse.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Proposition.class)
@Entity
@Table(name = "propositions")
public class Proposition implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "proposition_id", length = 5)
	private Long Id;

	/*
	 * Le système renseigne l'id de l'émetteur au moment de la création de la
	 * proposition L'Id du SEL où est publiée la proposition n'est pas un attribut
	 * car on considère que par défaut c'est toujours celui du SEL de l'émetteur Cet
	 * id est vérifié avec le UserBean en consommant le microservice
	 * adhérent****************************************************
	 */

	private String emetteurId;

	@Column(name = "emetteur_username", length = 255, nullable=false)
	private String emetteurUsername;

	/*
	 * L'émetteur renseigne l'enumTradeType au moment de la publication de son offre
	 * L'enumTradeType gère 2 positions pour la proposition : OFFRE ou DEMANDE
	 * ******************************************************
	 */

	@Column(name = "trade_type")
	private EnumTradeType enumTradeType;

	/*
	 * L'émetteur renseigne aussi le titre, la description écrite ou par image, la
	 * ville et son code postal de sa proposition. Il renseigne sa valeur Le système
	 * renseigne la monnaie d'expression dans la monnaie du SEL de l'émetteur (cas
	 * interSEL) L'émetteur renseigne la date de fin de validité de sa proposition
	 * et la date d'échéance à laquelle l'échange doit être réalisé au plus tard
	 * L'émetteur indique aussi à quelle catégorie d'échange appartient sa
	 * proposition (@ManyToOne)**********************************
	 */

	@Column(name = "titre", length = 100, nullable = false)
	private String titre;

	@Column(name = "description", length = 255, nullable = false)
	private String description;

	@Column(name = "image", length = 30, nullable = true)
	private String image;

	@Column(name = "ville", length = 50, nullable = false)
	private String ville;

	@Column(name = "code_postal", length = 6, nullable = false)
	private Integer codePostal;

	@Column(name = "valeur", length = 3, nullable = false)
	private Integer valeur;

	/*
	 * @Column(name = "monnaie", length = 10, nullable=false) private MonnaieBean
	 * monnaie;
	 */
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_fin", length = 25, nullable = false)
	private LocalDate dateFin;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_echeance", length = 25, nullable = false)
	private LocalDate dateEcheance;

	@Column(name = "statut", length = 25, nullable = false)
	private EnumStatutProposition statut;;

	@ManyToOne
	@JoinColumn(name = "categorie_id")
	private Categorie categorie;

	/*
	 * Le système renseigne la date de publication de la proposition
	 * ***************************************************************
	 */

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "date_debut", length = 25, nullable = false)
	private LocalDate dateDebut;

	/*
	 * Chaque proposition (OFFRE ou DEMANDE) d'un émetteur peut être mise en
	 * relation avec une ou plusieurs réponses d'un ou plusieurs
	 * récepteurs @OneToMany Ce n'est pas la publication d'une proposition qui
	 * détermine l'échange L'échange est créé dès qu'il y a publication d'une
	 * réponse à une proposition ************************************************
	 */

	@JsonIgnore
	@OneToMany(mappedBy = "proposition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Reponse> reponses;

	public Proposition() {
		super();

	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public EnumStatutProposition getStatut() {
		return statut;
	}

	public void setStatut(EnumStatutProposition statut) {
		this.statut = statut;
	}

	public List<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(List<Reponse> reponses) {
		this.reponses = reponses;
	}

	/*
	 * @Override public String toString() { return "Proposition [id=" + id +
	 * ", emetteurId=" + emetteurId + ", enumTradeType=" + enumTradeType +
	 * ", titre=" + titre + ", description=" + description + ", image=" + image +
	 * ", ville=" + ville + ", codePostal=" + codePostal + ", valeur=" + valeur +
	 * ", dateFin=" + dateFin + ", dateEcheance=" + dateEcheance + ", statut=" +
	 * statut + ", categorie=" + categorie + ", dateDebut=" + dateDebut +
	 * ", reponses=" + reponses + "]"; }
	 */

}
