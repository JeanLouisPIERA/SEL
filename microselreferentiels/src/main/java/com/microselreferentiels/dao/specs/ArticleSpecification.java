package com.microselreferentiels.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselreferentiels.criteria.ArticleCriteria;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.EnumStatutDocument;

public class ArticleSpecification implements Specification<Article> {

	private ArticleCriteria articleCriteria;

	public ArticleSpecification(ArticleCriteria articleCriteria) {
		this.articleCriteria = articleCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();

		predicates.getExpressions().add(builder.equal(root.get("isModerated"), Boolean.FALSE));

		predicates.getExpressions().add(builder.notEqual(root.get("typeArticle").get("id"), 9));

		if (articleCriteria.getStatutDocument() != null && !articleCriteria.getStatutDocument().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("statutDocument"),
					EnumStatutDocument.fromValueCode(articleCriteria.getStatutDocument())));

		}

		if (articleCriteria.getTypeArticle() != null && !articleCriteria.getTypeArticle().isEmpty()) {
			predicates.getExpressions().add(builder.like(root.get("typeArticle").get("typeName"),
					"%" + articleCriteria.getTypeArticle() + "%"));

		}

		return builder.and(predicates);

	}

}
