package com.microselwebclientjspui.controller;

import java.util.EnumSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.errors.PropositionExceptionMessage;
import com.microselwebclientjspui.objets.EnumCategorie;
import com.microselwebclientjspui.objets.EnumTradeType;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.service.IPropositionService;
import com.microselwebclientjspui.service.IReponseService;

@Controller
public class ReponseController {
	
	@Autowired
    private IPropositionService propositionService;
	@Autowired
	private IReponseService reponseService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PropositionExceptionMessage propositionExceptionMessage;
    
//CREATE PROPOSITION *****************************************************************************************************
    
    /**
     * Permet d'afficher le formulaire de création d'une réponse en injectant les attributs de la proposition
     */
    @GetMapping("/reponses/newReponse/{id}")
    public String newReponse(Model model, @PathVariable long id){
        model.addAttribute("reponseDTO", new ReponseDTO());
        model.addAttribute("enumTradeType", EnumSet.allOf(EnumTradeType.class) );
        model.addAttribute("enumCategorie", EnumSet.allOf(EnumCategorie.class) );
        
        Proposition propositionToRespond = propositionService.searchById(id);
        model.addAttribute("proposition", propositionToRespond);
        
        return "reponses/reponseCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'une nouvelle reponse
     */
    @PostMapping("/reponses/newReponse/{id}")
    public String createReponse(Model model, @PathVariable Long id, @ModelAttribute("reponseDTO") ReponseDTO reponseDTO, BindingResult result) {
		
		/*
		 * Proposition propositionToRespond = propositionService.searchById(id);
		 * model.addAttribute("proposition", propositionToRespond);
		 */
    	
    	if(result.hasErrors()) {
			return "reponses/reponseConfirmation";
		}
    	
    	try {
    		System.out.println("reponseDTO recepteurId" + reponseDTO.getRecepteurId());
			Reponse reponseToCreate = reponseService.createReponse(id, reponseDTO);
			model.addAttribute("reponse", reponseToCreate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = propositionExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
    	
		return "reponses/reponseConfirmation";
    }

}
