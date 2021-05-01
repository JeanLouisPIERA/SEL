package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;

/**
 * * Classe permettant d'implémenter l'interface JPA pour les relations ORM de la classe Proposition
 * @author jeanl
 *
 */
@Repository
public interface IPropositionRepository
		extends JpaRepository<Proposition, Long>, JpaSpecificationExecutor<Proposition> {

	@Query("select proposition from Proposition proposition where proposition.emetteurId = ?1 AND proposition.titre like %?2  AND proposition.enumTradeType like ?3 AND proposition.statut like ?4")
	Optional<Proposition> findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours(String emetteurId, String titre,
			EnumTradeType enumTradeType, EnumStatutProposition statutEnCours);

}
