package com.microselbourse.mapper;

import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Evaluation;

public interface IEvaluationMapper {

	EvaluationDTO evaluationToEvaluationDTO(Evaluation entity);

	Evaluation evaluationDTOToEvaluation(EvaluationDTO dto);

}
