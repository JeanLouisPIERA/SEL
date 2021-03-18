package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.dto.TypePropositionDTO;
import com.microselwebclientjspui.objets.TypeProposition;

public interface ITypePropositionService {

	TypeProposition createTypeDocument(TypePropositionDTO typePropositionDTO);

	Page<TypeProposition> getAll(Pageable pageable);

}
