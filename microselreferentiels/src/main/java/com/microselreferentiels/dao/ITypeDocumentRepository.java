package com.microselreferentiels.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microselreferentiels.entities.TypeDocument;



@Repository
public interface ITypeDocumentRepository extends JpaRepository<TypeDocument, Long>{

	Optional<TypeDocument> findByTypeName(String typeName);

}
