package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;

public class PropositionSpecification implements Specification<Proposition> {

	private PropositionCriteria propositionCriteria;

	public PropositionSpecification(PropositionCriteria propositionCriteria) {
		this.propositionCriteria = propositionCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Proposition> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();
		
		if (propositionCriteria.getEmetteurId() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("emetteurId"), "%" + propositionCriteria.getEmetteurId()+ "%"));
		}

		if (propositionCriteria.getEmetteurUsername() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("emetteurUsername"), "%" + propositionCriteria.getEmetteurUsername() + "%"));
		}

		if (propositionCriteria.getCodeEnumTradeType() != null && !propositionCriteria.getCodeEnumTradeType().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("enumTradeType"),
					EnumTradeType.fromValueCode(propositionCriteria.getCodeEnumTradeType())));
		}

		if (propositionCriteria.getStatut() != null && !propositionCriteria.getStatut().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("statut"),
					EnumStatutProposition.fromValueCode(propositionCriteria.getStatut())));
		}

		if (propositionCriteria.getCodePostal() != null) {
			predicates.getExpressions().add(builder.equal(root.get("codePostal"), propositionCriteria.getCodePostal()));
		}

		if (propositionCriteria.getNomCategorie() != null && !propositionCriteria.getNomCategorie().isEmpty()) {
			predicates.getExpressions().add(builder.equal(root.get("categorie").get("name"),
					EnumCategorie.fromValueCode(propositionCriteria.getNomCategorie())));
		}

		if (propositionCriteria.getTitre() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("titre"), "%" + propositionCriteria.getTitre() + "%"));
		}

		if (propositionCriteria.getVille() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("ville"), "%" + propositionCriteria.getVille() + "%"));

		}

		return builder.and(predicates);

	}

}
