package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.dto.PropositionUpdateDTO;
import com.microselwebclientjspui.objets.Proposition;

public interface IPropositionService {

	public Proposition createProposition(PropositionDTO propositionDTO);

	public Proposition searchById(long id);

	public Proposition updateProposition(Long propositionId, PropositionUpdateDTO propositionUpdateDTO);

	public Proposition closeProposition(Long id);

	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable);

	public Page<Proposition> searchByCriteriaByAdherent(PropositionCriteria propositionCriteria, Pageable pageable);

	public Page<Proposition> searchByCriteriaByAdherentId(String adherentId, PropositionCriteria propositionCriteria,
			Pageable pageable);

}
