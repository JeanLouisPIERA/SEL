package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IEchangeService {
	

	Echange readEchange(Long id) throws EntityNotFoundException;

	Echange confirmerEchange(Long id, Long emetteurId, Boolean decision) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException;

	Echange validerEchange(@Valid Long id, Long validateurId, Boolean decision) throws UnsupportedEncodingException, MessagingException, EntityNotFoundException, DeniedAccessException;

	Echange solderEchange(@Valid Long id);
}
