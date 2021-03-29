package com.microselwebclientjspui.controller;


import java.util.EnumSet;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.errors.PropositionExceptionMessage;

import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumStatutProposition;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IPropositionService;
import com.microselwebclientjspui.service.IReponseService;


@Controller
public class PropositionController {
	
	@Autowired
    private IPropositionService propositionService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PropositionExceptionMessage propositionExceptionMessage;
    @Autowired
    private IReponseService reponseService;
    

    //CREATE PROPOSITION *****************************************************************************************************
    
    /**
     * Permet d'afficher le formulaire de création d'une proposition
     */
    @GetMapping("/propositions/newProposition")
    public String newProposition(Model model){
        model.addAttribute("propositionDTO", new PropositionDTO());
        model.addAttribute("enumTradeType", EnumSet.allOf(EnumTradeType.class) );
        model.addAttribute("enumCategorie", EnumSet.allOf(EnumCategorie.class) );
        
        
        return "propositions/propositionCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'une nouvelle proposition
     */
    @PostMapping("/propositions/newProposition")
    public String createProposition(Model model, @ModelAttribute("propositionDTO") PropositionDTO propositionDTO, BindingResult result) {
		
    	if(result.hasErrors()) {
			return "propositions/propositionCreation";
		}
  
    	//Object propositionToCreate = new Object();
    	
		
		/*
		 * try { Proposition propositionToCreate =
		 * propositionService.createProposition(propositionDTO);
		 * model.addAttribute((Proposition)propositionToCreate); } catch
		 * (HttpClientErrorException e) { String errorMessage =
		 * propositionExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage
		 * (e); model.addAttribute("error", errorMessage); return"/error"; }
		 */
		 
		  Proposition propositionToCreate = propositionService.createProposition(propositionDTO); 
		  model.addAttribute((Proposition)propositionToCreate); 
    	
    	
		return "propositions/propositionConfirmation";
    }
    
   
    //READ AND SEARCH PROPOSITION(S)************************************************************************************************
     
    
	/**
     * Permet d'afficher une sélection de propositions sous forme de page
     */
    @GetMapping(value="/propositions")
    public String searchByCriteria(Model model, HttpServletRequest request, @PathParam(value = "propositionCriteria") PropositionCriteria propositionCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size){
    	
    	model.addAttribute("propositionCriteria", new PropositionCriteria());
    	model.addAttribute("enumTradeTypeList", EnumTradeType.getListEnumTradeType());
    	model.addAttribute("enumStatutPropositionList", EnumStatutProposition.getListEnumStatutProposition());
    	model.addAttribute("enumCategorieList", EnumCategorie.getListEnumCategorie());
    	
    	Page<Proposition> propositions = propositionService.searchByCriteria(propositionCriteria, PageRequest.of(page, size));
    	
    	model.addAttribute("propositions", propositions.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", propositions.getNumber());
        model.addAttribute("totalPages", propositions.getTotalPages());
        model.addAttribute("totalElements", propositions.getTotalElements());
        model.addAttribute("size", propositions.getSize());
        
        return "propositions/propositionsPage";
        
    }
    
    /**
     * Permet de lire la fiche d'une propositionn
     */
    @GetMapping("/propositions/reponses/{id}")
    public String readProposition(Model model, @PathVariable("id") Long id, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size) {
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
			String errorMessage = propositionExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
			return"/error";
		}
    	return "propositions/propositionView";
    }
    
    
    
    //UPDATE PROPOSITION *************************************************************************************************

    /**
     * Permet d'afficher le formulaire de modification d'une proposition
     */
    @GetMapping(value="/propositions/update/{id}")
    public String ShowPropositionPageToUpdate(Model model, @PathVariable long id, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="1") int size) {
    
    	model.addAttribute("propositionId", id);
    	
    	Proposition propositionToUpdate = propositionService.searchById(id);
    	
		model.addAttribute("propositionToUpdate", propositionToUpdate);
		
    	return "propositions/propositionEdition";
    	
    }
    
	/**
	 * Permet de mettre à jour une proposition
	 */
    @PutMapping(value = "/propositions/update/{id}")
    
    public String addPropositionToUpdate(Model model, Proposition proposition){
        
        try {
			Proposition propositionToUpdate = propositionService.updateProposition(proposition); 
			model.addAttribute((Proposition)propositionToUpdate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = propositionExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
      
        return "propositions/propositionModification";
    }
    

    //CLOSE PROPOSITION ********************************************************************************************************
    
    /**
     * permet de clôturer une proposition avant sa date de fin de publication
     */
        
    @PutMapping(value="/propositions/close/{id}")
    public String delete(Model model, @PathVariable("id") Long id) {
        try {
			propositionService.closeProposition(id);
		} catch (HttpClientErrorException e) {
			 String errorMessage = propositionExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
        return "/propositions/propositionClose";
    }

}
