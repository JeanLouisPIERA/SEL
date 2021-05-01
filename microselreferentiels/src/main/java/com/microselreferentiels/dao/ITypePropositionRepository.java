package com.microselreferentiels.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microselreferentiels.entities.TypeProposition;


@Repository
public interface ITypePropositionRepository extends JpaRepository<TypeProposition, Long>{
	
	Optional<TypeProposition> findByTypeName(String typeName);


}
