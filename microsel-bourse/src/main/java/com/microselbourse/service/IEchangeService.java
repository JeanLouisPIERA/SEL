package com.microselbourse.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IEchangeService {
	

	Echange createEchange(long id) throws EntityAlreadyExistsException, EntityNotFoundException;
	
	Echange readEchange(Long id) throws EntityNotFoundException;

	Page<Echange> searchAllEchangesByCriteria(EchangeCriteria echangeCriteria, Pageable pageable);

	Echange refuserEchangeEmetteur(@Valid Long id) 
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange validerEchangeEmetteur(@Valid Long id) 
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;
	
	Echange refuserEchangeRecepteur(@Valid Long id) 
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange validerEchangeRecepteur(@Valid Long id) 
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Echange annulerEchange(@Valid Long id) 
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException;

	Echange confirmerEchange(@Valid Long id) 
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException;

	List<Echange> searchAndUpdateEchangesASupprimer();

	List<Echange> searchAndUpdateEchangesAForceValider();

	List<Echange> searchAndUpdateEchangesAForceRefuser();
}
