package com.microselbourse.service;

import com.microselbourse.entities.Blocage;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IBlocageService {
	
	Blocage createBlocage(Long echangeId, Long adherent) throws EntityNotFoundException;
	
	Blocage annulerBlocage(Long adherentId) throws EntityNotFoundException;
	
	Blocage readBlocage(Long adherentId) throws EntityNotFoundException;

}
