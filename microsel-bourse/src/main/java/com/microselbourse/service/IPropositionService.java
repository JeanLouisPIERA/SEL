package com.microselbourse.service;


import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IPropositionService {
	
	Proposition createProposition(PropositionDTO propositionDTO) throws EntityAlreadyExistsException, EntityNotFoundException;
	
	Proposition readProposition(Long id);
	
	Proposition updateProposition(Long id, PropositionDTO propositionDTO);
	
	

}
