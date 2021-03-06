package com.microselbourse.dao.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Wallet;

/**
 * * Classe qui implémentate la JPA Spécification pour le requêtage de la classe Wallet
 * @author jeanl
 *
 */
public class WalletSpecification implements Specification<Wallet> {

	private WalletCriteria walletCriteria;

	public WalletSpecification(WalletCriteria walletCriteria) {
		this.walletCriteria = walletCriteria;
	}

	@Override
	public Predicate toPredicate(Root<Wallet> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		Predicate predicates = builder.conjunction();

		if (walletCriteria.getId() != null) {
			predicates.getExpressions().add(builder.equal(root.get("id"), walletCriteria.getId()));
		}

		if (walletCriteria.getTitulaireId() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("titulaireId"), "%" + walletCriteria.getTitulaireId() + "%"));
		}
		
		if (walletCriteria.getTitulaireUsername() != null) {
			predicates.getExpressions()
					.add(builder.like(root.get("titulaireUsername"), "%" + walletCriteria.getTitulaireUsername() + "%"));
		}

		if (walletCriteria.getSoldeWallet() != null) {
			predicates.getExpressions()
					.add(builder.greaterThanOrEqualTo(root.get("soldeWallet"), walletCriteria.getSoldeWallet()));
		}

		return builder.and(predicates);

	}

}
