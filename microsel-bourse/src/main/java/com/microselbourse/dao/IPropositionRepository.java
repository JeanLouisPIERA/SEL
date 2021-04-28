package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;

@Repository
public interface IPropositionRepository
		extends JpaRepository<Proposition, Long>, JpaSpecificationExecutor<Proposition> {

	@Query("select proposition from Proposition proposition where proposition.emetteurId = ?1 AND proposition.titre like %?2  AND proposition.enumTradeType like ?3 AND proposition.statut like ?4")
	Optional<Proposition> findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours(String emetteurId, String titre,
			EnumTradeType enumTradeType, EnumStatutProposition statutEnCours);

	

}
