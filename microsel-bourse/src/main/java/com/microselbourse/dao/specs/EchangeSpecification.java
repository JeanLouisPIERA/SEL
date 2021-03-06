package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutEchange;


/**
 * * Classe qui implémentate la JPA Spécification pour le requêtage de la classe Echange
 * @author jeanl
 *
 */
public class EchangeSpecification implements Specification<Echange> {

	private EchangeCriteria echangeCriteria;

	public EchangeSpecification(EchangeCriteria echangeCriteria) {
		this.echangeCriteria = echangeCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Echange> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();

		if (echangeCriteria.getId() != null) {
			predicates.getExpressions().add(builder.equal(root.get("id"), echangeCriteria.getId()));
		}

		if (echangeCriteria.getEmetteurId() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("emetteurId"), "%" + echangeCriteria.getEmetteurId() + "%"));
		}

		if (echangeCriteria.getRecepteurId() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("recepteurId"), "%" + echangeCriteria.getRecepteurId() + "%"));
		}

		if (echangeCriteria.getStatutEchange() != null && !echangeCriteria.getStatutEchange().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("statutEchange"),
					EnumStatutEchange.fromValueCode(echangeCriteria.getStatutEchange())));
		}

		if (echangeCriteria.getTitre() != null) {
			predicates.getExpressions().add(builder.like(root.get("titre"), "%" + echangeCriteria.getTitre() + "%"));
		}

		if (echangeCriteria.getEmetteurUsername() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("emetteurUsername"), "%" + echangeCriteria.getEmetteurUsername() + "%"));

		}

		if (echangeCriteria.getRecepteurUsername() != null) {
			predicates.getExpressions().add(
					builder.like(root.get("recepteurUsername"), "%" + echangeCriteria.getRecepteurUsername() + "%"));

		}

		return builder.and(predicates);
	}
}
