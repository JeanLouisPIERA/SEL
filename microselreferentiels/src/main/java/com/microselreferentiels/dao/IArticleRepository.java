package com.microselreferentiels.dao;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.entities.TypeArticle;

public interface IArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

	@Query("select article from Article article where article.typeArticle like ?1 AND article.statutDocument like ?2")
	Optional<Article> findByTypeArticleAndDocumentStatutEncours(TypeArticle typeArticle, EnumStatutDocument encours);

	@Query("select article from Article article where (article.statutDocument like ?1)"
			+ "AND (article.typeArticle.typeName like ?2)")
	Optional<List<Article>> findByStatutDocumentAndTypeArticle(EnumStatutDocument enumStatutDocument,
			String typeArticleTypeName);

	@Query("select article from Article article where article.typeArticle.id = ?1 AND article.statutDocument like ?2")
	Optional<Article> findByTypeArticleIdAndStatutDocument(@Valid Long typearticleId, EnumStatutDocument publie);

	

}
