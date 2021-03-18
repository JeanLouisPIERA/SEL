package com.microselreferentiels.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselreferentiels.dto.TypeDocumentDTO;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;

public interface ITypeDocumentService {

	TypeDocument createTypeDocument(@Valid TypeDocumentDTO typeDocumentDTO) throws EntityAlreadyExistsException;

	Page<TypeDocument> getAllTypeDocumentsPaginated(Pageable pageable);

	List<TypeDocument> getAllTypeDocuments();

}
