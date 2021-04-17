package com.microselwebclientjspui.controller;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.errors.EvaluationExceptionMessage;
import com.microselwebclientjspui.objets.EnumNoteEchange;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.service.IEchangeService;
import com.microselwebclientjspui.service.IEvaluationService;

@Controller
public class EvaluationController {

	@Autowired
	private IEchangeService echangeService;

	@Autowired
	private EvaluationExceptionMessage evaluationExceptionMessage;

	@Autowired
	private IEvaluationService evaluationService;

	/**
	 * Permet d'afficher le formulaire de création d'une evaluation
	 */
	@GetMapping("/evaluations/echange/{echangeId}")
	public String newEvaluation(Model model, @PathVariable Long echangeId) {

		model.addAttribute("echangeId", echangeId);

		// EvaluationDTO evaluationDTO = new EvaluationDTO();
		model.addAttribute("evaluationDTO", new EvaluationDTO());

		/*
		 * Echange echangeToEvaluate = echangeService.searchById(echangeId);
		 * List<Evaluation> evaluations = echangeToEvaluate.get
		 * model.addAttribute("echange", echangeToEvaluate);
		 */

		model.addAttribute("enumNoteEchange", EnumSet.allOf(EnumNoteEchange.class));

		return "evaluations/evaluationCreation";
	}

	/**
	 * Permet de valider l'enregistrement d'une nouvelle évaluation
	 */
	@PostMapping("/evaluations/echange/{echangeId}")
	public String createEvaluation(Model model, @PathVariable Long echangeId,
			@ModelAttribute("evaluationDTO") EvaluationDTO evaluationDTO, BindingResult result) {

		/*
		 * if(result.hasErrors()) { return "evaluations/evaluationCreation"; }
		 */

		/* Object evaluationToCreate = new Object(); */

		Evaluation evaluationToCreate = evaluationService.createEvaluation(echangeId, evaluationDTO);
		model.addAttribute("evaluation", evaluationToCreate);

		try {
			System.out.println("adherent_Id" + evaluationDTO.getEnumNoteEchange().toString());
			/*
			 * Evaluation evaluationToCreate = evaluationService.createEvaluation(echangeId,
			 * evaluationDTO); model.addAttribute("evaluation", evaluationToCreate);
			 */
		} catch (HttpClientErrorException e) {
			String errorMessage = evaluationExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "evaluations/evaluationConfirmation";
	}

}
