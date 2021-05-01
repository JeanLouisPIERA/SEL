package com.microselbourse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.EvaluationCriteria;
import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IEvaluationService {

	Evaluation createEvaluation(Long echangeId, @Valid EvaluationDTO evaluationDTO) throws EntityNotFoundException,
			EntityAlreadyExistsException, UnsupportedEncodingException, MessagingException;

	

	Evaluation modererEvaluation(@Valid Long id) throws EntityNotFoundException, DeniedAccessException;

	List<Evaluation> findAllByEchangeIdAndNotModerated(Long id);



	Page<Evaluation> searchAllEvaluationsByCriteria(EvaluationCriteria evaluationCriteria, Pageable pageable);

}
