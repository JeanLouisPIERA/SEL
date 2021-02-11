package com.microselbourse.entities;

import java.time.LocalDate;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="reponse")
public class Reponse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reponse_id", length=5)
	private Long id;
	
	/*
	 * Le système renseigne l'id du récepteur et du SEL dont il est membre au moment de la création de sa réponse à une proposition
	 * L'Id du SEL où est publiée la réponse à une proposition n'est pas un attribut car on considère que par défaut c'est toujours 
	 * celui du SEL de l'émetteur de la réponse
	 * Cet id est vérifié avec le UserBean en consommant le microservice adhérent*****************************************************
	 */
	
	@Column(name = "recepteurId", length = 10, nullable=false)
	private Long recepteurId; 
	

	/*
	 * Le système génère l'enumTradeType contrepartie de celui de la proposition à laquelle répond le récepteur
	 * L'enumTradeType gère 2 positions pour la proposition : OFFRE ou DEMANDE *******************************************************
	 */
	
	@Column(name="type")
	private EnumTradeType enumTradeType; 
	
	/*
	 * Le récepteur a la possibilité de valider en l'état la proposition de l'émetteur ou de modifier certains de ses attributs
	 * Il peut donc modifier le titre, la description écrite ou par image, la ville et son code postal s'il s'agit d'une contreproposition.
	 * Idem pour sa valeur
	 * Le système renseigne la monnaie d'expression de la réponse dans la monnaie du SEL de l'émetteur (cas interSEL).
	 * L'émmeteur renseigne la date de fin de validité de sa proposition et la date d'échéance à laquelle l'échange doit être réalisé au plus tard
	 * Il indique aussi à quelle catégorie d'échange appartient sa proposition (@ManyToOne)*******************************************
	 * Mais le récepteur ne peut modifier aucune des dates enregistrées dans la proposition
	 */
	
	@Column(name = "titre", length = 100, nullable=false, unique=true)
	private String titre; 
	
	@Column(name = "description", length = 255, nullable=false)
	private String description; 
	
	@Column(name = "image", length = 30, nullable=true)
	private String image; 
	
	@Column(name = "ville", length = 50, nullable=false)
	private String ville; 
	
	@Column(name = "codePostal", length = 6, nullable=false)
	private Integer codePostal;
	
	@Column(name = "valeur", length = 10, nullable=false)
	private Integer valeur;
	
	/*
	 * @Column(name = "monnaie", length = 10, nullable=false) private MonnaieBean
	 * monnaie;
	 */
	
	/*
	 * Le système renseigne la date de publication de la réponse ********************************************************************
	 */
	
	@Column(name = "date_reponse", length = 25, nullable=false)
	private LocalDate dateReponse; 
	
	/*
	 * Chaque proposition (OFFRE ou DEMANDE) d'un émetteur peut être mise en relation avec une ou plusieurs réponses 
	 * d'un ou plusieurs récepteurs @ManyToOne
	 * Ce n'est pas la publication d'une proposition qui détermine l'échange car il faut qu'il y ait au moins une réponse pour 
	 * créer un échange
	 * L'échange est créé dès qu'il y a publication d'une réponse à une proposition @OneToOne
	 * ****************************************************************************************************************************
	 */
	
	@ManyToOne 
	@JoinColumn(name="proposition_id")
	private Proposition proposition;
	
	@OneToOne(mappedBy = "reponse")
    private Echange echange;
	

}
