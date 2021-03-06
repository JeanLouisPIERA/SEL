package com.microselreferentiels.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microselreferentiels.entities.TypeArticle;



@Repository
public interface ITypeArticleRepository extends JpaRepository<TypeArticle, Long>{

	Optional<TypeArticle> findByTypeName(String typeName);

}
