package com.microselwebclientjspui.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.dto.TypeArticleDTO;
import com.microselwebclientjspui.objets.TypeArticle;

public interface ITypeArticleService {

	List<TypeArticle> getAll();
	
	Object createTypeArticle (TypeArticleDTO typeArticleDTO);

	Page<TypeArticle> getAll(Pageable pageable);
	
	

	

}
