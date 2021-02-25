package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.objets.Proposition;




public interface IPropositionService {
	
	public Page<Proposition>searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable);

	public Proposition createProposition(PropositionDTO propositionDTO);

	public Proposition searchById(long id);

	public Proposition updateProposition(Proposition proposition);

	public void closeProposition(Long id);

}
