package com.microselreferentiels.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;

public class DocumentSpecification implements Specification<Document> {

	private DocumentCriteria documentCriteria;

	public DocumentSpecification(DocumentCriteria documentCriteria) {
		this.documentCriteria = documentCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();

		if (documentCriteria.getStatutDocument() != null && !documentCriteria.getStatutDocument().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("statutDocument"),
					EnumStatutDocument.fromValueCode(documentCriteria.getStatutDocument())));

		}

		if (documentCriteria.getTypeDocument() != null && !documentCriteria.getTypeDocument().isEmpty()) {
			predicates.getExpressions().add(builder.like(root.get("typeDocument").get("typeName"),
					"%" + documentCriteria.getTypeDocument() + "%"));

		}

		return builder.and(predicates);

	}

}
