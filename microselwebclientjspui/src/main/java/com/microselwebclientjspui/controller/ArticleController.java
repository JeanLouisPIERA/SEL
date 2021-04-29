package com.microselwebclientjspui.controller;

import java.util.EnumSet;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.criteria.ArticleCriteria;
import com.microselwebclientjspui.criteria.DocumentCriteria;
import com.microselwebclientjspui.dto.ArticleDTO;
import com.microselwebclientjspui.dto.DocumentDTO;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.Article;
import com.microselwebclientjspui.objets.Document;
import com.microselwebclientjspui.objets.EnumStatutDocument;
import com.microselwebclientjspui.objets.TypeArticle;
import com.microselwebclientjspui.objets.TypeDocument;
import com.microselwebclientjspui.service.IArticleService;
import com.microselwebclientjspui.service.ITypeArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ITypeArticleService typeArticleService;
	
	@Autowired
	private IArticleService articleService;
	
	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;
	
	/**
	 * Permet d'afficher le formulaire de création d'un article
	 */
	@GetMapping("/articles/newArticle")
	public String newArticle(Model model) {

		List<TypeArticle> typeArticles = typeArticleService.getAll();

		model.addAttribute("articleDTO", new ArticleDTO());
		model.addAttribute("enumStatutDocument", EnumSet.allOf(EnumStatutDocument.class));
		model.addAttribute("typeArticlesList", typeArticles);

		return "articles/articleCreation";
	}

	/**
	 * Permet de valider l'enregistrement d'un nouvel article
	 */
	@PostMapping("/articles/newArticle")
	public String createArticle(Model model, @ModelAttribute("articleDTO") ArticleDTO articleDTO,
			BindingResult result) {

		if (result.hasErrors()) {
			return "articles/articleCreation";
		}

		Object articleToCreate = new Object();

		try {
			articleToCreate = articleService.createArticle(articleDTO);
			model.addAttribute((Article) articleToCreate);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "articles/articleConfirmation";
	}

	/**
	 * Permet d'afficher une sélection d'articles sous forme de page
	 */
	@GetMapping(value = "/articles")
	public String searchByCriteria(Model model,
			@PathParam(value = "articleCriteria") ArticleCriteria articleCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		List<TypeArticle> typeArticles = typeArticleService.getAll();

		model.addAttribute("articleCriteria", new ArticleCriteria());
		model.addAttribute("enumStatutDocumentList", EnumStatutDocument.getListEnumStatutDocument());
		model.addAttribute("typeArticlesList", typeArticles);

		Page<Article> articles = articleService.searchByCriteria(articleCriteria, PageRequest.of(page, size));

		model.addAttribute("articles", articles.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", articles.getNumber());
		model.addAttribute("totalPages", articles.getTotalPages());
		model.addAttribute("totalElements", articles.getTotalElements());
		model.addAttribute("size", articles.getSize());

		return "articles/articlesPage";

	}

	/**
	 * Permet de lire le contenu d'un article
	 */
	@GetMapping("/articles/{id}")
	public String readArticle(Model model, @PathVariable("id") Long id) {
		try {
			Article readArticle = articleService.searchById(id);
			model.addAttribute("article", readArticle);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "articles/articleView";
	}
	
	/**
	 * Permet de publier un article. Le statut publié permet de sélectionner l'article pour l'afficher en page d'accueil
	 */
	@GetMapping("/articles/publication/{id}")
	public String publierArticle(Model model, @PathVariable("id") Long id) {
		try {
			Article publishedArticle = articleService.publierById(id);
			model.addAttribute("article", publishedArticle);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "articles/articlePublicationConfirmation";
	}
	
	/**
	 * Permet de modérer la publication d'un article
	 */
	@GetMapping("/articles/moderation/{id}")
	public String modererArticle(Model model, @PathVariable("id") Long id) {
		try {
			Article moderatedArticle = articleService.modererById(id);
			model.addAttribute("article", moderatedArticle);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "articles/articleModerationConfirmation";
	}
	
	/**
	 * Permet d'archiver un article. L'archivage d'un article empêche qu'il soit sélectionné pour être affiché en page d'accueil
	 */
	@GetMapping("/articles/archivage/{id}")
	public String archiverArticle(Model model, @PathVariable("id") Long id) {
		try {
			Article archivedArticle = articleService.archiverById(id);
			model.addAttribute("article", archivedArticle);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "articles/articleArchivageConfirmation";
	}


}
