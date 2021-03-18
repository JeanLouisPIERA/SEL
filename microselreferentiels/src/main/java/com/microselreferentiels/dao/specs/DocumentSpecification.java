package com.microselreferentiels.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;



public class DocumentSpecification implements Specification<Document>{
	
	private DocumentCriteria documentCriteria;
	
	public DocumentSpecification (DocumentCriteria documentCriteria) {
		this.documentCriteria = documentCriteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
        Predicate predicates = builder.conjunction();

	        if (documentCriteria.getEnumStatutDocument()!= null && !documentCriteria.getEnumStatutDocument().isEmpty()) {
	        	predicates.getExpressions().add(builder.equal(root.get("statutDocument"), EnumStatutDocument.fromValueCode(documentCriteria.getEnumStatutDocument())));	
	        }
	        
        	if (documentCriteria.getAuteurId()!= null) {
            	predicates.getExpressions().add(builder.equal(root.get("auteurId"), documentCriteria.getAuteurId()));		
            }
        	
        	 if (documentCriteria.getAuteurUsername()!= null) {
             	predicates.getExpressions().add(builder.like(root.get("auteurUsername"), "%" + documentCriteria.getAuteurUsername()+ "%"));	
             }
        	
            if (documentCriteria.getNomTypeDocument()!= null && !documentCriteria.getNomTypeDocument().isEmpty()) {
            	predicates.getExpressions().add(builder.like(root.get("typeDocument").get("typeName"), documentCriteria.getNomTypeDocument()));	
            }
            
           
         
       
        return builder.and(predicates);
		
	}

	

}
