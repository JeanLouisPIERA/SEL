package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.entities.Proposition;

public interface IPropositionRepository extends JpaRepository<Proposition, Long>, JpaSpecificationExecutor<Proposition>{
	
	Optional<Proposition> findByIdAndTitre(Long id, String titre);
	
	/*
	 * Page<Proposition> findAll(PropositionSpecification propositionSpecification,
	 * Pageable pageable);
	 */
 
}
