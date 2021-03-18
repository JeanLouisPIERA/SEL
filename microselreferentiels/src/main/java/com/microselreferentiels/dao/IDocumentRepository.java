package com.microselreferentiels.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.entities.TypeDocument;


public interface IDocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document>{
	
	
	@Query("select document from Document document where document.typeDocument like ?1 AND document.statutDocument like ?2")
	Optional<Document> findByTypeDocumentAndDocumentStatutEncours(TypeDocument typeDocument, EnumStatutDocument encours);

}
