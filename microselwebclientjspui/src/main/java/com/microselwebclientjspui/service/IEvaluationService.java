package com.microselwebclientjspui.service;

import java.util.List;

import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.objets.Evaluation;

public interface IEvaluationService {

	Evaluation createEvaluation(Long echangeId, EvaluationDTO evaluationDTO);

	List<Evaluation> findAllByEchangeId(Long id);

}
