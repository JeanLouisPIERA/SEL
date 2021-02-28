package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;


public class EchangeSpecification implements Specification<Echange>{

private EchangeCriteria echangeCriteria;
	
	public EchangeSpecification (EchangeCriteria echangeCriteria) {
		this.echangeCriteria = echangeCriteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Echange> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
        Predicate predicates = builder.conjunction();

	        if (echangeCriteria.getId()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("id"), echangeCriteria.getId()));	
	        }
	        
	        if (echangeCriteria.getStatutEchange()!= null && !echangeCriteria.getStatutEchange().isEmpty()) {
	        	predicates.getExpressions().add(builder.equal(root.get("statutEchange"), EnumStatutEchange.fromValueCode(echangeCriteria.getStatutEchange())));			
	        }
       
            if (echangeCriteria.getTitre()!= null) {
            	predicates.getExpressions().add(builder.like(root.get("titre"), "%" + echangeCriteria.getTitre()+ "%"));	
            }
            
            if (echangeCriteria.getEmetteurUsername() != null) {
            	predicates.getExpressions().add(builder.like(root.get("emetteurUsername"), "%" + echangeCriteria.getEmetteurUsername()+ "%"));			
            	
            }
            
            if (echangeCriteria.getRecepteurUsername() != null) {
            	predicates.getExpressions().add(builder.like(root.get("recepteurUsername"), "%" + echangeCriteria.getRecepteurUsername()+ "%"));			
            	
            }
         
       
        return builder.and(predicates);
	}
}
