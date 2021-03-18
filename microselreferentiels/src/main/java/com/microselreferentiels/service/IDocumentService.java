package com.microselreferentiels.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;

public interface IDocumentService {

	Document createStaticDocument(@Valid DocumentDTO documentDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

	Page<Document> searchAllDocumentsByCriteria(DocumentCriteria documentCriteria, Pageable pageable);

	Document readDocument(@Valid Long id) throws EntityNotFoundException;

}
