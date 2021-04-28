package com.microselwebclientjspui.controller;

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

import com.microselwebclientjspui.criteria.BlocageCriteria;
import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.errors.ConvertToExceptionMessage;
import com.microselwebclientjspui.objets.Blocage;
import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumStatutBlocage;
import com.microselwebclientjspui.objets.EnumStatutProposition;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.service.IBlocageService;

@Controller
public class BlocageController {
	
	@Autowired
	private IBlocageService blocageService;
	
	@Autowired
	private ConvertToExceptionMessage convertToExceptionMessage;
	
	
	/**
	 * Permet d'afficher une sélection de propositions sous forme de page
	 * 
	 * @throws NotAuthorizedException
	 */
	@GetMapping(value = "/blocages")
	public String searchByCriteria(Model model,
			@PathParam(value = "blocageCriteria") BlocageCriteria blocageCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		model.addAttribute("blocageCriteria", new BlocageCriteria());
		model.addAttribute("enumStatutBlocageList", EnumStatutBlocage.getListEnumStatutBlocage());
		
		Page<Blocage> blocages = blocageService.searchByCriteria(blocageCriteria,
				PageRequest.of(page, size));

		model.addAttribute("blocages", blocages.getContent());
		model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", blocages.getNumber());
		model.addAttribute("totalPages", blocages.getTotalPages());
		model.addAttribute("totalElements", blocages.getTotalElements());
		model.addAttribute("size", blocages.getSize());

		return "blocages/blocagesPage";

	}
	
	
	/**
	 * permet de clôturer une proposition avant sa date de fin de publication
	 */

	@GetMapping(value = "/blocages/close/{id}")
	public String delete(Model model, @PathVariable("id") Long id) {
		try {
			Blocage blocageClosed = blocageService.closeBlocage(id);
			model.addAttribute(blocageClosed);
		} catch (HttpClientErrorException e) {
			String errorMessage = convertToExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			model.addAttribute("error", errorMessage);
			return "/error";
		}
		return "/blocages/blocageConfirmation";
	}

}
