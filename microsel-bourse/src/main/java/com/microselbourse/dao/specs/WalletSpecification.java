package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;


import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Wallet;

public class WalletSpecification implements Specification<Wallet>{
	
private WalletCriteria walletCriteria;
	
	public WalletSpecification (WalletCriteria walletCriteria) {
		this.walletCriteria = walletCriteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Wallet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		
        Predicate predicates = builder.conjunction();

	        if (walletCriteria.getId()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("id"), walletCriteria.getId()));	
	        }
	        
	        if (walletCriteria.getTitulaireId()!= null) {
	        	predicates.getExpressions().add(builder.equal(root.get("titulaireId"), walletCriteria.getTitulaireId()));			
	        }
       
        	if (walletCriteria.getSoldeWallet()!= 0) {
            	predicates.getExpressions().add(builder.equal(root.get("soldeWallet"), walletCriteria.getSoldeWallet()));		
            }
        	
        return builder.and(predicates);
		
	}

}
