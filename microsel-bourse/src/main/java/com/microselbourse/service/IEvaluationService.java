package com.microselbourse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IEvaluationService {

	Evaluation createEvaluation(Long echangeId, @Valid EvaluationDTO evaluationDTO) throws EntityNotFoundException,
			EntityAlreadyExistsException, UnsupportedEncodingException, MessagingException;

	List<Evaluation> findAllByEchangeId(Long id);

}
