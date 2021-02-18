package com.microselwebclient_ui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclient_ui.criteria.PropositionCriteria;
import com.microselwebclient_ui.dto.PropositionDTO;
import com.microselwebclient_ui.objets.Proposition;




public interface IPropositionService {
	
	public Page<Proposition>searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable);

	public Object createProposition(PropositionDTO propositionDTO);

	public Proposition searchById(long id);

	public Proposition updateProposition(Proposition proposition);

	public void closeProposition(Long id);

}
