package com.microselwebclientjspui.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.ArticleCriteria;
import com.microselwebclientjspui.dto.ArticleDTO;
import com.microselwebclientjspui.objets.Article;

public interface IArticleService {

	Object createArticle(ArticleDTO articleDTO);

	Page<Article> searchByCriteria(ArticleCriteria articleCriteria, Pageable pageable);

	Article searchById(Long id);

	Article modererById(Long id);

	Article archiverById(Long id);

	Article publierById(Long id);

	List<Article> select4ArticlesToBePublished();

	Article modifierArticleStandard(ArticleDTO articleDTO);

	

	


	

}
