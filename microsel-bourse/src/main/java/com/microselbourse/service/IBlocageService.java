package com.microselbourse.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.BlocageCriteria;
import com.microselbourse.entities.Blocage;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IBlocageService {

	
	

	Blocage readBlocage(String adherentId) throws EntityNotFoundException;

	Blocage createBlocageFromConflit(Long echangeId, String adherentId, String adherentUsername)
			throws EntityNotFoundException;

	Blocage createBlocage(Long echangeId, String adherentId, String adherentUsername) throws EntityNotFoundException;

	Page<Blocage> searchAllPropositionsByCriteria(BlocageCriteria blocageCriteria, Pageable pageable);

	Blocage annulerBlocage(@Valid Long id) throws EntityNotFoundException;

}
