package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.microselreferentiels.dao.ITypeDocumentRepository;
import com.microselreferentiels.dto.TypeDocumentDTO;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.service.ITypeDocumentService;

@Service
public class TypeDocumentServiceImpl implements ITypeDocumentService {

	@Autowired
	private ITypeDocumentRepository typeDocumentRepository;

	@Override
	public TypeDocument createTypeDocument(@Valid TypeDocumentDTO typeDocumentDTO) throws EntityAlreadyExistsException {

		Optional<TypeDocument> typeDocumentToCreate = typeDocumentRepository
				.findByTypeName(typeDocumentDTO.getTypeName());
		if (typeDocumentToCreate.isPresent())
			throw new EntityAlreadyExistsException("Ce type de document a déjà été enregistré avec le même nom");

		TypeDocument typeDocumentCreated = new TypeDocument();

		typeDocumentCreated.setTypeName(typeDocumentDTO.getTypeName());
		typeDocumentCreated.setDescription(typeDocumentDTO.getDescription());
		typeDocumentCreated.setDateCreation(LocalDate.now());

		return typeDocumentRepository.save(typeDocumentCreated);
	}

	@Override
	public Page<TypeDocument> getAllTypeDocumentsPaginated(Pageable pageable) {

		Page<TypeDocument> typeDocumentsPage = typeDocumentRepository.findAll(pageable);

		return typeDocumentsPage;
	}

	@Override
	public List<TypeDocument> getAllTypeDocuments() {
		List<TypeDocument> typeDocuments = typeDocumentRepository.findAll();
		return typeDocuments;
	}

}
