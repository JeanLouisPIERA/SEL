package com.microselwebui.microselwebui.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.microselwebui.microselwebui.criteria.PropositionCriteria;
import com.microselwebui.microselwebui.dto.PropositionDTO;
import com.microselwebui.microselwebui.errors.CustomErrorDecoder;
import com.microselwebui.microselwebui.errors.PropositionExceptionMessage;
import com.microselwebui.microselwebui.objets.Categorie;
import com.microselwebui.microselwebui.objets.Proposition;
import com.microselwebui.microselwebui.proxies.IMicroselBourseProxy;
import com.microselwebui.microselwebui.service.IPropositionService;

@Controller
public class PropositionController {
	
	@Autowired
    private IPropositionService propositionService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PropositionExceptionMessage propositionExceptionMessage;
    //CREATE PROPOSITION *****************************************************************************************************
    
    /**
     * Permet d'afficher le formulaire de création d'une proposition
     */
    @GetMapping("/propositions/newProposition")
    public String newProposition(Model model){
        model.addAttribute("propositionDTO", new PropositionDTO());
        return "propositions/propositionCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'une nouvelle proposition
     */
    @PostMapping("/propositions/newProposition")
    public String createLivre(Model model, @ModelAttribute("propositionDTO") PropositionDTO propositionDTO) {
		
    	Object propositionToCreate = new Object();
    	
    	try {
			propositionToCreate = propositionService.createProposition(propositionDTO);
			model.addAttribute((Proposition)propositionToCreate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = propositionExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
    	
		return "propositions/propositionConfirmation";
    	
    	
        
    }
    
   
    //READ AND SEARCH PROPOSITION(S)************************************************************************************************
     
    
	/**
     * Permet d'afficher une sélection de propositions sous forme de page
     */
    @GetMapping(value="/propositions")
    public String searchByCriteria(Model model, @PathParam(value = "propositionCriteria") PropositionCriteria propositionCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size){
    	PropositionCriteria propositionCriteriaTest = new PropositionCriteria();
    	propositionCriteriaTest.setVille("Paris");
    	model.addAttribute("propositionCriteria", propositionCriteriaTest);
    	
    	model.addAttribute("categorie", new Categorie());
    	model.addAttribute("proposition", new Proposition());
    	
    	
    	Page<Proposition> propositions = propositionService.searchByCriteria(propositionCriteria, PageRequest.of(page, size));
    	
    
    	
    	System.out.println(propositions.getSize());
    	System.out.println(propositions.getTotalElements());
    	
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
    @GetMapping("/propositions/{id}")
    public String readPret(Model model, @PathVariable("id") Long id) {
    	try {
			Proposition readProposition = propositionService.searchById(id);
			model.addAttribute("readProposition", readProposition);
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
