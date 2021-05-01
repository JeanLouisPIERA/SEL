package com.microselbourse.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;

public interface IReponseService {

	Reponse createReponse(Long propositionId, ReponseDTO reponseDTO) throws EntityNotFoundException,
			DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException;

	Reponse readReponse(Long id) throws EntityNotFoundException;

	Page<Reponse> findAllByWalletId(Long id, Pageable pageable) throws EntityNotFoundException;

}
