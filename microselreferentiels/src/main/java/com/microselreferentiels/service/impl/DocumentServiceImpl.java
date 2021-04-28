package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.List;
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
import com.microselreferentiels.exceptions.DeniedAccessException;
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
		if(!auteurDocument.getId().equals(documentDTO.getAuteurId()))
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
			throw new EntityNotFoundException("Le document que vous voulez consulter n'existe pas");
		
		return documentToRead.get();
	}

	@Override
	public Document publierDocument(@Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		
		Optional<Document> documentToPublish = documentRepository.findById(id);
		if (!documentToPublish.isPresent())
			throw new EntityNotFoundException("Le document que vous souhaitez publier n'existe pas.");
		
		Optional<List<Document>> alreadyPublishedDocuments = documentRepository.findByStatutDocumentAndTypeDocument(
				EnumStatutDocument.PUBLIE, documentToPublish.get().getTypeDocument().getTypeName());
		if (alreadyPublishedDocuments.isPresent())
			throw new EntityNotFoundException("Il ne peut y avoir qu'un seul document de ce type en cours de publication. Merci d'archiver les autres documents.");
		
	
		if (!documentToPublish.get().getStatutDocument().equals(EnumStatutDocument.ENCOURS))
			throw new DeniedAccessException("Vous ne pouvez pas publier un document qui est déjà publié ou archivé");

		documentToPublish.get().setStatutDocument(EnumStatutDocument.PUBLIE);
		documentToPublish.get().setDatePublication(LocalDate.now());
		
		return documentRepository.save(documentToPublish.get());
	}

	@Override
	public Document archiverDocument(@Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		Optional<Document> documentToArchive = documentRepository.findById(id);
		if (!documentToArchive.isPresent())
			throw new EntityNotFoundException("Le document que vous souhaitez archiver n'existe pas.");
		
	
		if (!documentToArchive.get().getStatutDocument().equals(EnumStatutDocument.ARCHIVE))
			throw new DeniedAccessException("Vous ne pouvez pas archiver qu'un document qui est déjà archivé");

		documentToArchive.get().setStatutDocument(EnumStatutDocument.ARCHIVE);
		documentToArchive.get().setDateArchivage(LocalDate.now());
		
		return documentRepository.save(documentToArchive.get());
	}

	@Override
	public Document readDocumentByTypeDocument(@Valid Long typedocumentId) throws EntityNotFoundException {
		Optional<Document> documentToRead = documentRepository.findByTypeDocumentIdAndStatutDocument(typedocumentId, EnumStatutDocument.PUBLIE); 
		if(!documentToRead.isPresent())
			throw new EntityNotFoundException("Le document que vous voulez consulter n'existe pas");
		
		return documentToRead.get();
	}
	}


