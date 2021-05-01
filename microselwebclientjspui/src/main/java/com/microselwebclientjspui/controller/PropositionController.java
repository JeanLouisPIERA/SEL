package com.microselwebclientjspui.controller;

import java.util.EnumSet;

import javax.validation.Valid;
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

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.dto.PropositionUpdateDTO;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumStatutProposition;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.service.IPropositionService;
import com.microselwebclientjspui.service.IReponseService;

@Controller
public class PropositionController {

	@Autowired
	private IPropositionService propositionService;
	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;
	@Autowired
	private IReponseService reponseService;

	// CREATE PROPOSITION
	// *****************************************************************************************************

	/**
	 * Permet d'afficher le formulaire de création d'une proposition
	 */
	@GetMapping("/propositions/newProposition")
	public String newProposition(Model model) {
		model.addAttribute("propositionDTO", new PropositionDTO());
		model.addAttribute("enumTradeType", EnumSet.allOf(EnumTradeType.class));
		model.addAttribute("enumCategorie", EnumSet.allOf(EnumCategorie.class));

		return "propositions/propositionCreation";
	}

	/**
	 * Permet de valider l'enregistrement d'une nouvelle proposition
	 */
	@PostMapping("/propositions/newProposition")
	public String createProposition(Model model, @ModelAttribute("propositionDTO") PropositionDTO propositionDTO,
			BindingResult result) {

		if (result.hasErrors()) {
			return "propositions/propositionCreation";
		}

		try {
			Proposition propositionToCreate = propositionService.createProposition(propositionDTO);
			model.addAttribute("proposition", propositionToCreate);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "propositions/propositionConfirmation";
	}

	// READ AND SEARCH
	// PROPOSITION(S)************************************************************************************************

	/**
	 * Permet d'afficher une sélection de propositions sous forme de page
	 * 
	 * @throws NotAuthorizedException
	 */
	@GetMapping(value = "/propositions")
	public String searchByCriteria(Model model,
			@PathParam(value = "propositionCriteria") PropositionCriteria propositionCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("propositionCriteria", new PropositionCriteria());
		model.addAttribute("enumTradeTypeList", EnumTradeType.getListEnumTradeType());
		model.addAttribute("enumStatutPropositionList", EnumStatutProposition.getListEnumStatutProposition());
		model.addAttribute("enumCategorieList", EnumCategorie.getListEnumCategorie());

		Page<Proposition> propositions = propositionService.searchByCriteria(propositionCriteria,
				PageRequest.of(page, size));

		model.addAttribute("propositions", propositions.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", propositions.getNumber());
		model.addAttribute("totalPages", propositions.getTotalPages());
		model.addAttribute("totalElements", propositions.getTotalElements());
		model.addAttribute("size", propositions.getSize());

		return "propositions/propositionsPage";

	}

	/**
	 * Permet à un adhérent d'afficher une sélection de ses propositions sous forme
	 * de page
	 * 
	 * @throws NotAuthorizedException
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/propositions/adherent")
	public String searchByCriteriaByAdherent(Model model,
			@PathParam(value = "propositionCriteria") PropositionCriteria propositionCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("propositionCriteria", new PropositionCriteria());
		model.addAttribute("enumTradeTypeList", EnumTradeType.getListEnumTradeType());
		model.addAttribute("enumStatutPropositionList", EnumStatutProposition.getListEnumStatutProposition());
		model.addAttribute("enumCategorieList", EnumCategorie.getListEnumCategorie());

		Page<Proposition> propositions = propositionService.searchByCriteriaByAdherent(propositionCriteria,
				PageRequest.of(page, size));

		model.addAttribute("propositions", propositions.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", propositions.getNumber());
		model.addAttribute("totalPages", propositions.getTotalPages());
		model.addAttribute("totalElements", propositions.getTotalElements());
		model.addAttribute("size", propositions.getSize());

		return "propositions/propositionsPageAdherent";

	}
	
	/**
	 * Permet à un administrateur d'afficher la sélection des propositions d'un adhérent sous forme
	 * de page
	 * 
	 * @throws NotAuthorizedException
	 * @throws EntityNotFoundException
	 */
	@GetMapping(value = "/propositions/adherent/{adherentId}")
	public String searchByCriteriaByAdherentId(Model model,
			@PathVariable("adherentId") String adherentId,
			@PathParam(value = "propositionCriteria") PropositionCriteria propositionCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("propositionCriteria", new PropositionCriteria());
		model.addAttribute("enumTradeTypeList", EnumTradeType.getListEnumTradeType());
		model.addAttribute("enumStatutPropositionList", EnumStatutProposition.getListEnumStatutProposition());
		model.addAttribute("enumCategorieList", EnumCategorie.getListEnumCategorie());

		Page<Proposition> propositions = propositionService.searchByCriteriaByAdherentId(adherentId, propositionCriteria,
				PageRequest.of(page, size));

		model.addAttribute("propositions", propositions.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", propositions.getNumber());
		model.addAttribute("totalPages", propositions.getTotalPages());
		model.addAttribute("totalElements", propositions.getTotalElements());
		model.addAttribute("size", propositions.getSize());

		return "propositions/propositionsPageAdherent";

	}
	

	/**
	 * Permet de lire la fiche d'une proposition
	 */
	@GetMapping("/propositions/reponses/{id}")
	public String readProposition(Model model, @PathVariable("id") Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "6") int size) {
		try {
			Proposition readProposition = propositionService.searchById(id);
			model.addAttribute("proposition", readProposition);

			Page<Reponse> reponses = reponseService.findAllByPropositionId(id, PageRequest.of(page, size));

			model.addAttribute("reponses", reponses.getContent());
			model.addAttribute("page", Integer.valueOf(page));
			model.addAttribute("number", reponses.getNumber());
			model.addAttribute("totalPages", reponses.getTotalPages());
			model.addAttribute("totalElements", reponses.getTotalElements());
			model.addAttribute("size", reponses.getSize());

		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "propositions/propositionView";
	}

	// UPDATE PROPOSITION
	// *************************************************************************************************

	/**
	 * Permet d'afficher le formulaire de modification d'une proposition
	 */
	@GetMapping("/propositions/{propositionId}")
	public String ShowPropositionPageToUpdate(Model model, @PathVariable("propositionId") Long propositionId) {

		model.addAttribute("enumTradeType", EnumSet.allOf(EnumTradeType.class));
		model.addAttribute("enumCategorie", EnumSet.allOf(EnumCategorie.class));
		
		Proposition propositionToUpdate = propositionService.searchById(propositionId);
		model.addAttribute("proposition", propositionToUpdate);

		PropositionUpdateDTO propositionUpdateDTO = new PropositionUpdateDTO();
		model.addAttribute("propositionUpdateDTO", propositionUpdateDTO);

		return "propositions/propositionEdit";

	}

	/**
	 * Permet de mettre à jour une proposition
	 */
	@PostMapping("/propositions/{propositionId}")
	public String addPropositionToUpdate(Model model, @PathVariable Long propositionId, @Valid @ModelAttribute("propositionUpdateDTO") PropositionUpdateDTO propositionUpdateDTO  , BindingResult result  ) {
		
		if (result.hasErrors()) {
			return "propositions/propositionCreation";
		}
		
		try {
			Proposition propositionUpdated = propositionService.updateProposition(propositionId, propositionUpdateDTO);
			model.addAttribute((Proposition) propositionUpdated);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}

		return "propositions/propositionUpdateConfirmation";
	}

	// CLOSE PROPOSITION
	// ********************************************************************************************************

	/**
	 * permet de clôturer une proposition avant sa date de fin de publication
	 */

	@GetMapping(value = "/propositions/close/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {
		try {
			Proposition propositionClosed = propositionService.closeProposition(id);
			model.addAttribute(propositionClosed);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "/propositions/propositionConfirmation";
	}

}
