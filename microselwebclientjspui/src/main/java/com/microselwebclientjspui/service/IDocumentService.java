package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.DocumentCriteria;
import com.microselwebclientjspui.dto.DocumentDTO;
import com.microselwebclientjspui.objets.Document;

public interface IDocumentService {

	Object createDocument(DocumentDTO documentDTO);

	Page<Document> searchByCriteria(DocumentCriteria documentCriteria, Pageable pageable);

	Document searchById(Long id);

}
