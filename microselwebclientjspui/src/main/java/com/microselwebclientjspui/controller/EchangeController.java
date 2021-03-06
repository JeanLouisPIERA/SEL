package com.microselwebclientjspui.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microselwebclientjspui.criteria.EchangeCriteria;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.Echange;
import com.microselwebclientjspui.objets.EnumStatutEchange;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.service.IEchangeService;
import com.microselwebclientjspui.service.IEvaluationService;
import com.microselwebclientjspui.service.IUserService;

@Controller
public class EchangeController {

	@Autowired
	private IEchangeService echangeService;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;
	@Autowired
	private IEvaluationService evaluationService;
	@Autowired
	private IUserService userService;

	// READ AND SEARCH
	// ECHANGE(S)************************************************************************************************

	/**
	 * Permet d'afficher une sélection d'échanges sous forme de page
	 */
	@GetMapping(value = "/echanges")
	public String searchByCriteria(Model model, @PathParam(value = "echangeCriteria") EchangeCriteria echangeCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("echangeCriteria", new EchangeCriteria());
		model.addAttribute("enumStatutEchangeList", EnumStatutEchange.getListEnumStatutEchange());

		Page<Echange> echanges = echangeService.searchByCriteria(echangeCriteria, PageRequest.of(page, size));

		model.addAttribute("echanges", echanges.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", echanges.getNumber());
		model.addAttribute("totalPages", echanges.getTotalPages());
		model.addAttribute("totalElements", echanges.getTotalElements());
		model.addAttribute("size", echanges.getSize());

		return "echanges/echangesPage";

	}

	/**
	 * Permet d'afficher à un adhérent d'afficher une sélection de ses échanges où
	 * il est récepteur sous forme de page
	 */
	@GetMapping(value = "/echanges/recepteur")
	public String searchByCriteriaByAdherentRecepteur(Model model,
			@PathParam(value = "echangeCriteria") EchangeCriteria echangeCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("echangeCriteria", new EchangeCriteria());
		model.addAttribute("enumStatutEchangeList", EnumStatutEchange.getListEnumStatutEchange());

		Page<Echange> echanges = echangeService.searchByCriteriaByAdherentRecepteur(echangeCriteria,
				PageRequest.of(page, size));

		model.addAttribute("echanges", echanges.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", echanges.getNumber());
		model.addAttribute("totalPages", echanges.getTotalPages());
		model.addAttribute("totalElements", echanges.getTotalElements());
		model.addAttribute("size", echanges.getSize());

		return "echanges/echangesPageAdherentRecepteur";

	}

	/**
	 * Permet d'afficher à un adhérent d'afficher une sélection de ses échanges où
	 * il est émetteur sous forme de page
	 */
	@GetMapping(value = "/echanges/emetteur")
	public String searchByCriteriaByAdherentEmetteur(Model model,
			@PathParam(value = "echangeCriteria") EchangeCriteria echangeCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("echangeCriteria", new EchangeCriteria());
		model.addAttribute("enumStatutEchangeList", EnumStatutEchange.getListEnumStatutEchange());

		Page<Echange> echanges = echangeService.searchByCriteriaByAdherentEmetteur(echangeCriteria,
				PageRequest.of(page, size));

		model.addAttribute("echanges", echanges.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", echanges.getNumber());
		model.addAttribute("totalPages", echanges.getTotalPages());
		model.addAttribute("totalElements", echanges.getTotalElements());
		model.addAttribute("size", echanges.getSize());

		return "echanges/echangesPageAdherentEmetteur";

	}

	/**
	 * Permet de lire les évaluations d'un échange
	 */
	@GetMapping("/echanges/evaluations/{id}")
	public String readWallet(Model model, @PathVariable("id") Long id) {

		try {
			Echange readEchange = echangeService.searchById(id);
			model.addAttribute("readEchange", readEchange);

			List<Evaluation> evaluations = evaluationService.findAllByEchangeId(id);

			model.addAttribute("evaluations", evaluations);

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "echanges/echangeView";
	}

	/**
	 * Permet de confirmer un echange
	 */
	@GetMapping("/echanges/confirmation/{id}")
	public String confirmerEchange(Model model, @PathVariable("id") Long id) {

		try {
			Echange echangeAConfirmer = echangeService.confirmerEchange(id);
			model.addAttribute("echange", echangeAConfirmer);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";

		}

		return "echanges/echangeConfirmation";
	}

	/**
	 * Permet d'annuler un echange
	 */
	@GetMapping("/echanges/annulation/{id}")
	public String annulerEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeAAnnuler = echangeService.annulerEchange(id);
			model.addAttribute("echange", echangeAAnnuler);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeAnnulation";
	}

	/**
	 * Permet à l'emetteur de valider un echange
	 */
	@GetMapping("/echanges/emetteurValidation/{id}")
	public String emetteurValiderEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeAValider = echangeService.emetteurValiderEchange(id);
			model.addAttribute("echange", echangeAValider);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeValidation";
	}

	/**
	 * Permet au récepteur de valider un echange
	 */
	@GetMapping("/echanges/recepteurValidation/{id}")
	public String recepteurValiderEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeAValider = echangeService.recepteurValiderEchange(id);
			model.addAttribute("echange", echangeAValider);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeValidation";
	}

	/**
	 * Permet à l'emetteur de refuser un echange
	 */
	@GetMapping("/echanges/emetteurRefus/{id}")
	public String emetteurRefuserEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeARefuser = echangeService.emetteurRefuserEchange(id);
			model.addAttribute("echange", echangeARefuser);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeRefus";
	}

	/**
	 * Permet au récepteur de refuser un echange
	 */
	@GetMapping("/echanges/recepteurRefus/{id}")
	public String recepteurRefuserEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeARefuser = echangeService.recepteurRefuserEchange(id);
			model.addAttribute("echange", echangeARefuser);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeRefus";
	}

	/**
	 * Permet à l'emetteur ou au récepteur de valider un echange
	 */
	@GetMapping("/echanges/validation/{id}")
	public String validerEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeAValider = echangeService.validerEchange(id);
			model.addAttribute("echange", echangeAValider);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeValidation";
	}

	/**
	 * Permet à l'emetteur ou au récepteur de refuser un echange
	 */
	@GetMapping("/echanges/refus/{id}")
	public String refuserEchange(Model model, @PathVariable("id") Long id) {
		try {
			Echange echangeARefuser = echangeService.refuserEchange(id);
			model.addAttribute("echange", echangeARefuser);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "echanges/echangeRefus";
	}

}
