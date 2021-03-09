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
import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.errors.EchangeExceptionMessage;
import com.microselwebclientjspui.errors.PropositionExceptionMessage;
import com.microselwebclientjspui.objets.Echange;
import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumStatutEchange;
import com.microselwebclientjspui.objets.EnumStatutProposition;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IEchangeService;
import com.microselwebclientjspui.service.IEvaluationService;
import com.microselwebclientjspui.service.IPropositionService;

@Controller
public class EchangeController {
	
	@Autowired
    private IEchangeService echangeService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EchangeExceptionMessage echangeExceptionMessage;
    @Autowired
    private IEvaluationService evaluationService;
    
	
	//READ AND SEARCH ECHANGE(S)************************************************************************************************
    
    
		/**
	     * Permet d'afficher une sélection d'échanges sous forme de page
	     */
	    @GetMapping(value="/echanges")
	    public String searchByCriteria(Model model, @PathParam(value = "echangeCriteria") EchangeCriteria echangeCriteria, @RequestParam(name="page", defaultValue="0") int page, 
				@RequestParam(name="size", defaultValue="6") int size){
	    	
	    	model.addAttribute("echangeCriteria", new EchangeCriteria());
	    	model.addAttribute("enumStatutEchangeList", EnumStatutEchange.getListEnumStatutEchange());
	    	//model.addAttribute("enumStatutPropositionList", EnumStatutProposition.getListEnumStatutProposition());
	    	//model.addAttribute("enumCategorieList", EnumCategorie.getListEnumCategorie());
	    	
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
				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
				model.addAttribute("error", errorMessage);
				return"/error";
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
				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
				model.addAttribute("error", errorMessage);
				return"/error";
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
				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
				model.addAttribute("error", errorMessage);
				return"/error";
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
				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
				model.addAttribute("error", errorMessage);
				return"/error";
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
  				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
  				model.addAttribute("error", errorMessage);
  				return"/error";
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
				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
				model.addAttribute("error", errorMessage);
				return"/error";
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
  				String errorMessage = echangeExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
  				model.addAttribute("error", errorMessage);
  				return"/error";
  			}
  			return "echanges/echangeRefus";
  		}

}
