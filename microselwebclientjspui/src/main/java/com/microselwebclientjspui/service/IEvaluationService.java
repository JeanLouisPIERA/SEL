package com.microselwebclientjspui.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.EvaluationCriteria;
import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.objets.Article;
import com.microselwebclientjspui.objets.Evaluation;

public interface IEvaluationService {

	Evaluation createEvaluation(Long echangeId, EvaluationDTO evaluationDTO);

	List<Evaluation> findAllByEchangeId(Long id);

	Evaluation modererById(Long id);

	Page<Evaluation> searchByCriteria(EvaluationCriteria evaluationCriteria, Pageable pageable);

	
	

}
