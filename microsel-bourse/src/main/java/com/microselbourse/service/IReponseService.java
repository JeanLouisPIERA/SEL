package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.ReponseCriteria;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IReponseService {
	
	Reponse createReponse(Long propositionId, ReponseDTO reponseDTO) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException;
	
	Page<Reponse> searchAllReponsesByCriteria(ReponseCriteria reponseCriteria, Pageable pageable);
	
	Reponse readReponse(Long id);

	

}