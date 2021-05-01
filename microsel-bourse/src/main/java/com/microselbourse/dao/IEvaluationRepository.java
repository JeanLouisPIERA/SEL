package com.microselbourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.Proposition;

@Repository
public interface IEvaluationRepository extends JpaRepository<Evaluation, Long>, JpaSpecificationExecutor<Evaluation> {

	@Query("select evaluation from Evaluation evaluation where (evaluation.echange = ?1)"
			+ "AND (evaluation.adherentId = ?2)")
	Optional<Evaluation> findByEchangeAndAdherentId(Echange echange, String adherentId);

	@Query("select evaluation from Evaluation evaluation where (evaluation.echange.id= ?1)")
	List<Evaluation> findAllByEchangeId(Long id);

	@Query("select evaluation from Evaluation evaluation where (evaluation.echange.id= ?1)"+ "AND (evaluation.isModerated= ?2)")
	List<Evaluation> findAllByEchangeIdAndNotModerated(Long id, Boolean false1);

}
