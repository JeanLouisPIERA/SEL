package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.EvaluationCriteria;
import com.microselbourse.entities.Evaluation;



public class EvaluationSpecification implements Specification<Evaluation> {

	private EvaluationCriteria evaluationCriteria;

	public EvaluationSpecification(EvaluationCriteria evaluationCriteria) {
		this.evaluationCriteria = evaluationCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Evaluation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();
		
		if (evaluationCriteria.getId() != null) {
			predicates.getExpressions()
					.add(builder.equal(root.get("id"), evaluationCriteria.getId()));
		}

		if (evaluationCriteria.getAdherentUsername() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("adherentUsername"), "%" + evaluationCriteria.getAdherentUsername() + "%"));
		}

		

		return builder.and(predicates);

	}

}
