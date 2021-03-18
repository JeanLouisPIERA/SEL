package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselreferentiels.beans.UserBean;
import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.dao.IDocumentRepository;
import com.microselreferentiels.dao.ITypeDocumentRepository;
import com.microselreferentiels.dao.specs.DocumentSpecification;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.mapper.IDocumentMapper;
import com.microselreferentiels.proxies.IMicroselAdherentsProxy;
import com.microselreferentiels.service.IDocumentService;

@Service
public class DocumentServiceImpl implements IDocumentService {
	
	@Autowired
	IDocumentRepository documentRepository;
	
	@Autowired
	ITypeDocumentRepository typeDocumentRepository;
	
	@Autowired
	IDocumentMapper documentMapper;
	
	@Autowired
	IMicroselAdherentsProxy adherentsProxy;

	@Override
	public Document createStaticDocument(@Valid DocumentDTO documentDTO) throws EntityAlreadyExistsException, EntityNotFoundException {
		
		 Optional<TypeDocument> typeDocumentFound = typeDocumentRepository.findByTypeName(documentDTO.getTypeDocument());
		  if(!typeDocumentFound.isPresent()) 
			  throw new EntityNotFoundException(
					  "Le type de document que vous souhaitez créer n'existe pas");
		  
		Optional<Document> documentToCreate = documentRepository.findByTypeDocumentAndDocumentStatutEncours(typeDocumentFound.get(), EnumStatutDocument.ENCOURS);
		if(documentToCreate.isPresent())
			throw new EntityAlreadyExistsException("Il ne peut y exister qu'un seul document de ce type avec le statut en cours");
		
		UserBean auteurDocument = adherentsProxy.consulterCompteAdherent(documentDTO.getAuteurId());
		if(auteurDocument.getId()!= documentDTO.getAuteurId())
			throw new EntityNotFoundException(
					"Vous n'êtes pas identifié comme adhérent de l'association");
		
		
		
		Document documentCreated = documentMapper.documentDTOToDocument(documentDTO);
		
		documentCreated.setAuteurUsername(auteurDocument.getUsername());
		documentCreated.setDateCreation(LocalDate.now());
		documentCreated.setStatutDocument(EnumStatutDocument.ENCOURS);
		documentCreated.setTypeDocument(typeDocumentFound.get());
	
		return documentRepository.save(documentCreated);
	}

	@Override
	public Page<Document> searchAllDocumentsByCriteria(DocumentCriteria documentCriteria, Pageable pageable) {
		Specification<Document> documentSpecification = new DocumentSpecification(documentCriteria); 
		  Page<Document> documents = documentRepository.findAll(documentSpecification, pageable); 
		  return documents; }

	@Override
	public Document readDocument(@Valid Long id) throws EntityNotFoundException {
		Optional<Document> documentToRead = documentRepository.findById(id); 
		if(!documentToRead.isPresent())
			throw new EntityNotFoundException("L'offre ou la demande que vous voulez consulter n'existe pas");
		
		return documentToRead.get();
	}
	}


