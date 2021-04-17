package com.microselbourse.service;

import com.microselbourse.entities.Blocage;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IBlocageService {

	Blocage createBlocage(Long echangeId, String adherentId) throws EntityNotFoundException;

	Blocage annulerBlocage(String adherentId) throws EntityNotFoundException;

	Blocage readBlocage(String adherentId) throws EntityNotFoundException;

}
