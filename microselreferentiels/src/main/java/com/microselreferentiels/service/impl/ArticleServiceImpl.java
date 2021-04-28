package com.microselreferentiels.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselreferentiels.beans.UserBean;
import com.microselreferentiels.criteria.ArticleCriteria;
import com.microselreferentiels.dao.IArticleRepository;
import com.microselreferentiels.dao.ITypeArticleRepository;
import com.microselreferentiels.dao.specs.ArticleSpecification;
import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.exceptions.DeniedAccessException;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.mapper.IArticleMapper;
import com.microselreferentiels.mapper.IDocumentMapper;
import com.microselreferentiels.proxies.IMicroselAdherentsProxy;
import com.microselreferentiels.service.IArticleService;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	IArticleRepository articleRepository;

	@Autowired
	ITypeArticleRepository typeArticleRepository;

	@Autowired
	IDocumentMapper documentMapper;

	@Autowired
	IMicroselAdherentsProxy adherentsProxy;

	@Autowired
	IArticleMapper articleMapper;

	@Override
	public Article createArticle(@Valid ArticleDTO articleDTO)
			throws EntityAlreadyExistsException, EntityNotFoundException {

		Optional<TypeArticle> typeArticleFound = typeArticleRepository.findByTypeName(articleDTO.getTypeArticle());
		if (!typeArticleFound.isPresent())
			throw new EntityNotFoundException("Le type d'article que vous souhaitez créer n'existe pas");

		Optional<Article> articleToCreate = articleRepository
				.findByTypeArticleAndDocumentStatutEncours(typeArticleFound.get(), EnumStatutDocument.ENCOURS);
		if (articleToCreate.isPresent())
			throw new EntityAlreadyExistsException(
					"Il ne peut y exister qu'un seul document de ce type avec le statut en cours");

		UserBean auteurArticle = adherentsProxy.consulterCompteAdherent(articleDTO.getAuteurId());
		if (!auteurArticle.getId().equals(articleDTO.getAuteurId()))
			throw new EntityNotFoundException("Vous n'êtes pas identifié comme adhérent de l'association");

		Article articleCreated = articleMapper.articleDTOToArticle(articleDTO);

		articleCreated.setAuteurUsername(auteurArticle.getUsername());
		articleCreated.setDateCreation(LocalDate.now());
		articleCreated.setStatutDocument(EnumStatutDocument.ENCOURS);
		articleCreated.setTypeArticle(typeArticleFound.get());

		return articleRepository.save(articleCreated);
	}

	@Override
	public Page<Article> searchAllArticlesByCriteria(ArticleCriteria articleCriteria, Pageable pageable) {
		Specification<Article> articleSpecification = new ArticleSpecification(articleCriteria);
		Page<Article> articles = articleRepository.findAll(articleSpecification, pageable);
		return articles;
	}

	@Override
	public Article readArticle(@Valid Long id) throws EntityNotFoundException {
		Optional<Article> articleToRead = articleRepository.findById(id);
		if (!articleToRead.isPresent())
			throw new EntityNotFoundException("Le document que vous voulez consulter n'existe pas");

		return articleToRead.get();
	}

	@Override
	public Article modererArticle(@Valid Long id) throws EntityNotFoundException, DeniedAccessException {

		Optional<Article> articleToModerate = articleRepository.findById(id);
		if (!articleToModerate.isPresent())
			throw new EntityNotFoundException("L'article que vous souhaitez modérer n'existe pas.");

		/*
		 * Optional<List<Article>> alreadyPublishedArticles =
		 * articleRepository.findByStatutDocumentAndTypeArticle(
		 * EnumStatutDocument.PUBLIE,
		 * articleToModerate.get().getTypeArticle().getTypeName()); if
		 * (alreadyPublishedArticles.isPresent()) throw new
		 * EntityNotFoundException("Il ne peut y avoir qu'un seul document de ce type en cours de publication. Merci d'archiver les autres documents."
		 * );
		 */

		if (!articleToModerate.get().getStatutDocument().equals(EnumStatutDocument.PUBLIE))
			throw new DeniedAccessException("Vous ne pouvez pas modérer un article qui est déjà modéré ou archivé");

		articleToModerate.get().setIsModerated(true);
		articleToModerate.get().setDateModeration(LocalDate.now());

		return articleRepository.save(articleToModerate.get());
	}

	@Override
	public Article archiverArticle(@Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		Optional<Article> articleToArchive = articleRepository.findById(id);
		if (!articleToArchive.isPresent())
			throw new EntityNotFoundException("L'article que vous souhaitez archiver n'existe pas.");

		if (!articleToArchive.get().getStatutDocument().equals(EnumStatutDocument.ARCHIVE))
			throw new DeniedAccessException("Vous ne pouvez pas archiver un document qui est déjà archivé");

		articleToArchive.get().setStatutDocument(EnumStatutDocument.ARCHIVE);
		articleToArchive.get().setDateArchivage(LocalDate.now());

		return articleRepository.save(articleToArchive.get());
	}

	@Override
	public Article readArticleByTypeArticle(@Valid Long typearticleId) throws EntityNotFoundException {
		Optional<Article> articleToRead = articleRepository.findByTypeArticleIdAndStatutDocument(typearticleId,
				EnumStatutDocument.PUBLIE);
		if (!articleToRead.isPresent())
			throw new EntityNotFoundException("L'article que vous voulez consulter n'existe pas");

		return articleToRead.get();
	}

}
