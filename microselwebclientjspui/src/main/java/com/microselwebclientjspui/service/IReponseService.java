package com.microselwebclientjspui.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.objets.Reponse;


public interface IReponseService {
	
	public Reponse createReponse(Long id, ReponseDTO reponseDTO);

	public Page<Reponse> findAllByPropositionId(Long id, Pageable pageable);

}
