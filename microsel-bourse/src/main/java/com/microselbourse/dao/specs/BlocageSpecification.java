package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.BlocageCriteria;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.EnumStatutBlocage;

public class BlocageSpecification implements Specification<Blocage> {

	private BlocageCriteria blocageCriteria;

	public BlocageSpecification(BlocageCriteria blocageCriteria) {
		this.blocageCriteria = blocageCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Blocage> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();

		if (blocageCriteria.getId() != null) {
			predicates.getExpressions().add(builder.equal(root.get("id"), blocageCriteria.getId()));
		}

		if (blocageCriteria.getAdherentId() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("adherentId"), "%" + blocageCriteria.getAdherentId() + "%"));
		}

		if (blocageCriteria.getAdherentUsername() != null ) {
			predicates.getExpressions()
					.add(builder.like(root.get("adherentUsername"), "%" + blocageCriteria.getAdherentUsername() + "%"));
		}

		if (blocageCriteria.getStatutBlocage() != null && !blocageCriteria.getStatutBlocage().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("statutBlocage"),
					EnumStatutBlocage.fromValueCode(blocageCriteria.getStatutBlocage())));
		}

		return builder.and(predicates);

	}
}
