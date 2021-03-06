package com.microseluser.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microseluser.criteria.UserCriteria;
import com.microseluser.entities.User;

public class UserSpecification implements Specification<User> {

	private UserCriteria userCriteria;

	public UserSpecification(UserCriteria userCriteria) {
		this.userCriteria = userCriteria;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();
		query.groupBy(root.get("id"));

		if (userCriteria.getEmail() != null) {
			predicates.getExpressions().add(builder.like(root.get("email"), "%" + userCriteria.getEmail() + "%"));
		}

		if (userCriteria.getUsername() != null) {
			predicates.getExpressions().add(builder.like(root.get("username"), "%" + userCriteria.getUsername() + "%"));

		}

		if (userCriteria.getFirstName() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("firstName"), "%" + userCriteria.getFirstName() + "%"));

		}

		if (userCriteria.getLastName() != null) {
			predicates.getExpressions().add(builder.like(root.get("lastName"), "%" + userCriteria.getLastName() + "%"));

		}

		if (userCriteria.getDescription() != null) {
			Join join = root.join("roles");
			predicates.getExpressions()
					.add(builder.like(join.get("description"), "%" + userCriteria.getDescription() + "%"));

		}

		if (!userCriteria.getRole().isEmpty()) {
			Join join = root.join("roles");

			predicates.getExpressions().add(builder.like(join.get("name"), "%" + userCriteria.getRole() + "%"));

		}

		if (userCriteria.getRole().isEmpty()) {
			Join join = root.join("roles");

			predicates.getExpressions().add(builder.like(join.get("name"), "%" + userCriteria.getDefaultRole() + "%"));

		}

		return builder.and(predicates);
	}
}
