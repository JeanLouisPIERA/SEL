package com.microselbourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;


@Repository
public interface IEvaluationRepository extends JpaRepository<Evaluation, Long>{

	@Query("select evaluation from Evaluation evaluation where (evaluation.echange = ?1)"+ "AND (evaluation.adherentId = ?2)")
	Optional<Evaluation> findByEchangeAndAdherentId(Echange echange, Long adherentId);

	@Query("select evaluation from Evaluation evaluation where (evaluation.echange.id= ?1)")
	List<Evaluation> findAllByEchangeId(Long id);

}
