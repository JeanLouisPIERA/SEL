package com.microselreferentiels.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselreferentiels.dto.TypeArticleDTO;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;

public interface ITypeArticleService {


	
	Page<TypeArticle> getAllTypeArticlesPaginated(Pageable pageable);

	List<TypeArticle> getAllTypeArticles();

	TypeArticle createTypeArticle(@Valid TypeArticleDTO typeArticleDTO) throws EntityAlreadyExistsException;
	
	

}
