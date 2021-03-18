package com.microselwebclientjspui.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.dto.TypeDocumentDTO;
import com.microselwebclientjspui.objets.TypeDocument;

public interface ITypeDocumentService {

	Object createTypeDocument(TypeDocumentDTO typeDocumentDTO);

	Page<TypeDocument> getAll(Pageable pageable);
	
	List<TypeDocument> getAll();

}
