package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.entities.Reponse;

/**
 * * Classe qui implémentate la JPA Spécification pour le requêtage de la classe Réponse
 * @author jeanl
 *
 */
public class ReponseSpecification implements Specification<Reponse> {

	@Override
	public Predicate toPredicate(Root<Reponse> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		return null;
	}

}
