package com.microselreferentiels.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselreferentiels.criteria.ArticleCriteria;
import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.exceptions.DeniedAccessException;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;

public interface IArticleService {
	
	Article createArticle(@Valid ArticleDTO articleDTO) throws EntityAlreadyExistsException, EntityNotFoundException;

	Article readArticle(@Valid Long id) throws EntityNotFoundException;

	Article modererArticle(@Valid Long id) throws EntityNotFoundException, DeniedAccessException;

	Article archiverArticle(@Valid Long id) throws EntityNotFoundException, DeniedAccessException;

	Article readArticleByTypeArticle(@Valid Long typearticleId) throws EntityNotFoundException;

	Page<Article> searchAllArticlesByCriteria(ArticleCriteria articleCriteria, Pageable pageable);

	Article publierArticle(@Valid Long id) throws EntityNotFoundException, DeniedAccessException;

	Article modifierArticleStandard(ArticleDTO articleDTO);

	

	

	


}
