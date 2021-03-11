package com.microselbourse.mapper.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.Proposition;
import com.microselbourse.mapper.IEvaluationMapper;

@Service
public class EvaluationMapperImpl implements IEvaluationMapper{

	@Override
	public EvaluationDTO evaluationToEvaluationDTO(Evaluation entity) {
		if ( entity == null ) {
            return null;
        }

        EvaluationDTO evaluationDTO = new EvaluationDTO();
     
        evaluationDTO.setAdherentId(entity.getAdherentId());
        evaluationDTO.setCommentaire(entity.getCommentaire());
        evaluationDTO.setEnumNoteEchange(entity.getEnumNoteEchange());
        
        return evaluationDTO;
	}

	@Override
	public Evaluation evaluationDTOToEvaluation(EvaluationDTO dto) {
		
		 if ( dto == null ) {
	            return null;
	        }

	        Evaluation evaluation = new Evaluation();
	       
	        evaluation.setAdherentId(dto.getAdherentId());
	        evaluation.setCommentaire(dto.getCommentaire());
	        evaluation.setEnumNoteEchange(dto.getEnumNoteEchange());
	        
	        return evaluation;
		
	}
	
	

}