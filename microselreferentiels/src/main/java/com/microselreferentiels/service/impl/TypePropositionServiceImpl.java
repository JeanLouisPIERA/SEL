package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.microselreferentiels.dao.ITypePropositionRepository;
import com.microselreferentiels.dto.TypePropositionDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.entities.TypeProposition;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.service.ITypePropositionService;

@Service
public class TypePropositionServiceImpl implements ITypePropositionService{
	
	@Autowired
	private ITypePropositionRepository typePropositionRepository;

	@Override
	public Page<TypeProposition> getAllTypePropositionsPaginated(Pageable pageable) {
		Page<TypeProposition> typePropositionsPage = typePropositionRepository.findAll(pageable);
		
        return typePropositionsPage;
	}

	@Override
	public TypeProposition createTypeProposition(@Valid TypePropositionDTO typePropositionDTO) throws EntityAlreadyExistsException {
		Optional<TypeProposition> typePropositionToCreate = typePropositionRepository.findByTypeName(typePropositionDTO.getTypeName());
		if(typePropositionToCreate.isPresent())
			throw new EntityAlreadyExistsException("Ce type de proposition a déjà été enregistré avec le même nom");
		
		TypeProposition typePropositionCreated = new TypeProposition();
		
		typePropositionCreated.setTypeName(typePropositionDTO.getTypeName());
		typePropositionCreated.setDescription(typePropositionDTO.getDescription());
		typePropositionCreated.setDateCreation(LocalDate.now());
	
		return typePropositionRepository.save(typePropositionCreated);
	}

	@Override
	public List<TypeProposition> getAllTypePropositions() {
		
		List<TypeProposition> typePropositions = typePropositionRepository.findAll();
		return typePropositions;
		
	}
	
	
	@Override
	public TypeProposition readTypeProposition(@Valid Long id) throws EntityNotFoundException {
		Optional<TypeProposition> typePropositionToRead = typePropositionRepository.findById(id); 
		if(!typePropositionToRead.isPresent())
			throw new EntityNotFoundException("Le type de proposition que vous voulez consulter n'existe pas");
		
		return typePropositionToRead.get();
	}

	@Override
	public TypeProposition readTypePropositionByTypeName(@Valid String typeName) throws EntityNotFoundException {
		Optional<TypeProposition> typePropositionToRead = typePropositionRepository.findByTypeName(typeName); 
		if(!typePropositionToRead.isPresent())
			throw new EntityNotFoundException("Le type de proposition que vous voulez consulter n'existe pas");
		
		return typePropositionToRead.get();
	}

}
