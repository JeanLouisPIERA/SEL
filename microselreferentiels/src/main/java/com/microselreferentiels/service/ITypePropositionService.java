package com.microselreferentiels.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselreferentiels.dto.TypePropositionDTO;
import com.microselreferentiels.entities.TypeProposition;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;

public interface ITypePropositionService {

	Page<TypeProposition> getAllTypePropositionsPaginated(Pageable pageable);

	TypeProposition createTypeProposition(@Valid TypePropositionDTO typePropositionDTO) throws EntityAlreadyExistsException;

	List<TypeProposition> getAllTypePropositions();

	TypeProposition readTypeProposition(@Valid Long id) throws EntityNotFoundException;

	TypeProposition readTypePropositionByTypeName(@Valid String typeName) throws EntityNotFoundException;

}
