package com.microselreferentiels.mapper.impl;


import org.springframework.stereotype.Service;

import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.mapper.IDocumentMapper;

@Service
public class DocumentMapperImpl implements IDocumentMapper {

	@Override
	public DocumentDTO documentTodocumentDTO(Document entity) {
		if ( entity == null ) {
            return null;
        }

        DocumentDTO documentDTO = new DocumentDTO();
        
        documentDTO.setAuteurId(entity.getAuteurId());
        documentDTO.setContenu(entity.getContenu());
        documentDTO.setImage(entity.getImage());
        documentDTO.setTitre(entity.getTitre());
       
        
		return documentDTO;
        
	}

	@Override
	public Document documentDTOToDocument(DocumentDTO dto) {
		
		 if ( dto == null ) {
	            return null;
	        }
 
	        Document document = new Document();
	        
	        document .setAuteurId(dto.getAuteurId());
	        document.setContenu(dto.getContenu());
	        document.setImage(dto.getImage());
	        document.setTitre(dto.getTitre());
	        
	        
			return document;
		
	}

}
