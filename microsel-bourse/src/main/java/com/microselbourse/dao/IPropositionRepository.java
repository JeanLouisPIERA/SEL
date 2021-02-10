package com.microselbourse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microselbourse.entities.Proposition;

public interface IPropositionRepository extends JpaRepository<Proposition, Long>{
	
	Optional<Proposition> findByIdAndTitre(Long id, String titre);

}
