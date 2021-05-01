package com.microselreferentiels.mapper.impl;

import org.springframework.stereotype.Service;

import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.mapper.IArticleMapper;

@Service
public class ArticleMapperImpl implements IArticleMapper {

	@Override
	public ArticleDTO articleToArticleDTO(Article entity) {
		if (entity == null) {
			return null;
		}

		ArticleDTO articleDTO = new ArticleDTO();

		articleDTO.setAuteurId(entity.getAuteurId());
		articleDTO.setAuteurUsername(entity.getAuteurUsername());
		articleDTO.setContenu(entity.getContenu());
		articleDTO.setEntete(entity.getEntete());
		articleDTO.setImage(entity.getImage());
		articleDTO.setTitre(entity.getTitre());

		return articleDTO;

	}

	@Override
	public Article articleDTOToArticle(ArticleDTO dto) {

		if (dto == null) {
			return null;
		}

		Article article = new Article();

		article.setAuteurId(dto.getAuteurId());
		article.setAuteurUsername(dto.getAuteurUsername());
		article.setContenu(dto.getContenu());
		article.setEntete(dto.getEntete());
		article.setImage(dto.getImage());
		article.setTitre(dto.getTitre());

		return article;

	}

}
