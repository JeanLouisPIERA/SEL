package com.microselreferentiels.mapper;

import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.Document;

public interface IArticleMapper {
	
	ArticleDTO articleToArticleDTO(Article entity);
	
	Article articleDTOToArticle(ArticleDTO dto);

}
