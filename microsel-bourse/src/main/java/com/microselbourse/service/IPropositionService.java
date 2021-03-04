package com.microselbourse.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IPropositionService {
	
	Proposition createProposition(PropositionDTO propositionDTO) throws EntityAlreadyExistsException, EntityNotFoundException, DeniedAccessException;
	
	Page<Proposition> searchAllPropositionsByCriteria(PropositionCriteria propositionCriteria, Pageable pageable);
	
	Proposition readProposition(Long id) throws EntityNotFoundException;
	
	Proposition updateProposition(Long id, PropositionDTO propositionDTO) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException;
	
	Proposition closeProposition(Long id) throws EntityNotFoundException, DeniedAccessException;
	 
	

}
