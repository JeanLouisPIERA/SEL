package com.microselwebclientjspui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.dto.TypeArticleDTO;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.TypeArticle;
import com.microselwebclientjspui.service.ITypeArticleService;

/*
 * Les types de documents à créer sont defSEL (définition SEL), defBOURSE (définition Bourse d'échanges), defRESEAU
 * (définition Réseau des SEL), CGU, FAQ, infosLegales etc ...
 */

@Controller
public class TypeArticleController {

	@Autowired
	private ITypeArticleService typeArticleService;

	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;

	/**
	 * Permet d'afficher le formulaire de création d'un type d'article
	 */
	@GetMapping("/typearticles/newTypeArticle")
	public String newTypeArticle(Model model) {
		model.addAttribute("typeArticleDTO", new TypeArticleDTO());

		return "typearticles/typeArticleCreation";
	}

	/**
	 * Permet de valider l'enregistrement d'un nouveau type d'article
	 */
	@PostMapping("/typearticles/newTypeArticle")
	public String createTypeArticle(Model model, @ModelAttribute("typeArticleDTO") TypeArticleDTO typeArticleDTO,
			BindingResult result) {

		if (result.hasErrors()) {
			return "typearticles/typeArticleCreation";
		}

		Object typeArticleToCreate = new Object();

		try {
			typeArticleToCreate = typeArticleService.createTypeArticle(typeArticleDTO);
			model.addAttribute((TypeArticle) typeArticleToCreate);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "typearticles/typeArticleConfirmation";
	}

	/**
	 * Permet d'afficher tous les types d'articles sous forme de page
	 */
	@GetMapping(value = "/typearticles")
	public String getAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		Page<TypeArticle> typeArticles = typeArticleService.getAll(PageRequest.of(page, size));

		model.addAttribute("typeArticles", typeArticles.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", typeArticles.getNumber());
		model.addAttribute("totalPages", typeArticles.getTotalPages());
		model.addAttribute("totalElements", typeArticles.getTotalElements());
		model.addAttribute("size", typeArticles.getSize());

		return "typearticles/typeArticlesPage";

	}

}
