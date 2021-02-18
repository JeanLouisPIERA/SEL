package com.microselwebui.microselwebui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.microselwebui.microselwebui.criteria.PropositionCriteria;
import com.microselwebui.microselwebui.dto.PropositionDTO;
import com.microselwebui.microselwebui.objets.Proposition;

public interface IPropositionService {
	
	public Page<Proposition>searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable);

	public Object createProposition(PropositionDTO propositionDTO);

	public Proposition searchById(long id);

	public Proposition updateProposition(Proposition proposition);

	public void closeProposition(Long id);

}
