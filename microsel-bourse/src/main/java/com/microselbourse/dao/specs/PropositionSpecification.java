package com.microselbourse.dao.specs;

import java.time.LocalDate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;

public class PropositionSpecification implements Specification<Proposition>{
	
	
private PropositionCriteria propositionCriteria;
	
	public PropositionSpecification (PropositionCriteria propositionCriteria) {
		this.propositionCriteria = propositionCriteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Proposition> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
        Predicate predicates = builder.conjunction();

	        if (propositionCriteria.getCodeEnumTradeType()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("EnumTradeType"), EnumTradeType.fromValueCode(propositionCriteria.getCodeEnumTradeType())));			
	        }
       
        	if (propositionCriteria.getCodePostal()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("codePostal"), propositionCriteria.getCodePostal()));		
            }
        	
        	//Search des propositions qui ont été publiées dans le mois précédent 
        	if (propositionCriteria.getDateDebut()!= null) {
            	predicates.getExpressions().add(builder.greaterThan(root.get("dateEcheance"), LocalDate.now().minusDays(30)));		
            }
        	
        	
        	//Search des propositions dont l'échéance est dans moins d'un mois 
        	if (propositionCriteria.getDateEcheance()!= null) {
            	predicates.getExpressions().add(builder.lessThan(root.get("dateEcheance"), LocalDate.now().plusDays(30)));		
            }
        	
        	//Search des propositions dont la fin de publication est dans moins de quinze jours
            if (propositionCriteria.getDateFin()!= null) {
            	predicates.getExpressions().add(builder.lessThan(root.get("dateFin"), LocalDate.now().plusDays(15)));	  	            
            }
            
            if (propositionCriteria.getNomCategorie()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("categorie").get("nomCategorie"), "%" + propositionCriteria.getNomCategorie() + "%"));	
            }
            
            if (propositionCriteria.getTitre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("titre"), "%" + propositionCriteria.getTitre()+ "%"));	
            }
            
            if (propositionCriteria.getVille() != null) {
            	predicates.getExpressions().add(builder.like(root.get("ville"), "%" + propositionCriteria.getVille()+ "%"));			
            	
            }
         
       
        return builder.and(predicates);
		
	}

	
	
	

}
