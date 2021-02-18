package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.entities.Echange;


public class EchangeSpecification implements Specification<Echange>{

	@Override
	public Predicate toPredicate(Root<Echange> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		// FIXME Auto-generated method stub
		return null;
	}

}
