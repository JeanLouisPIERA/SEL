package com.microselreferentiels.mapper;

import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Document;

public interface IDocumentMapper {
	
	DocumentDTO documentTodocumentDTO(Document entity);
	
	Document documentDTOToDocument(DocumentDTO dto);

}
