package com.microselwebclientjspui.controller;

import java.util.EnumSet;

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

import com.microselwebclientjspui.criteria.EvaluationCriteria;
import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.EnumNoteEchange;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.service.IEchangeService;
import com.microselwebclientjspui.service.IEvaluationService;

@Controller
public class EvaluationController {

	@Autowired
	private IEchangeService echangeService;

	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;

	@Autowired
	private IEvaluationService evaluationService;

	/**
	 * Permet d'afficher le formulaire de création d'une evaluation
	 */
	@GetMapping("/evaluations/echange/{echangeId}")
	public String newEvaluation(Model model, @PathVariable Long echangeId) {

		model.addAttribute("echangeId", echangeId);

		model.addAttribute("evaluationDTO", new EvaluationDTO());

		model.addAttribute("enumNoteEchange", EnumSet.allOf(EnumNoteEchange.class));

		return "evaluations/evaluationCreation";
	}

	/**
	 * Permet de valider l'enregistrement d'une nouvelle évaluation
	 */
	@PostMapping("/evaluations/echange/{echangeId}")
	public String createEvaluation(Model model, @PathVariable Long echangeId,
			@ModelAttribute("evaluationDTO") EvaluationDTO evaluationDTO, BindingResult result) {

		if (result.hasErrors()) {
			return "evaluations/evaluationCreation";
		}

		try {

			Evaluation evaluationToCreate = evaluationService.createEvaluation(echangeId, evaluationDTO);
			model.addAttribute("evaluation", evaluationToCreate);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "evaluations/evaluationConfirmation";
	}
	
	/**
	 * Permet de modérer la publication d'une évaluation
	 */
	@GetMapping("/evaluations/moderation/{id}")
	public String modererEvaluation(Model model, @PathVariable("id") Long id) {
		try {
			Evaluation moderatedEvaluation = evaluationService.modererById(id);
			model.addAttribute("evaluation", moderatedEvaluation);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "evaluations/evaluationModerationConfirmation";
	}
	
	/**
	 * Permet d'afficher une sélection des evaluations sous forme de page
	 * 
	 * @throws NotAuthorizedException
	 */
	@GetMapping(value = "/evaluations")
	public String searchByCriteria(Model model,
			@PathParam(value = "evaluationCriteria") EvaluationCriteria evaluationCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("evaluationCriteria", new EvaluationCriteria());

		Page<Evaluation> evaluations = evaluationService.searchByCriteria(evaluationCriteria,PageRequest.of(page, size));

		model.addAttribute("evaluations", evaluations.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", evaluations.getNumber());
		model.addAttribute("totalPages", evaluations.getTotalPages());
		model.addAttribute("totalElements", evaluations.getTotalElements());
		model.addAttribute("size", evaluations.getSize());

		return "evaluations/evaluationsPage";

	}

}
