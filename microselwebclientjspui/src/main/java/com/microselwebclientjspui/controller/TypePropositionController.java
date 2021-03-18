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

import com.microselwebclientjspui.dto.TypePropositionDTO;
import com.microselwebclientjspui.errors.TypePropositionExceptionMessage;
import com.microselwebclientjspui.objets.TypeProposition;
import com.microselwebclientjspui.service.ITypePropositionService;


@Controller
public class TypePropositionController {
	
	@Autowired
	private ITypePropositionService typePropositionService;
	
	@Autowired
	private TypePropositionExceptionMessage typePropositionExceptionMessage;
	
	/**
     * Permet d'afficher le formulaire de création d'un type de Proposition
     */
    @GetMapping("/referentiels/newTypeProposition")
    public String newTypeProposition(Model model){
        model.addAttribute("typePropositionDTO", new TypePropositionDTO());
        
        return "typepropositions/typePropositionCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'un nouveau type de Proposition
     */
    @PostMapping("/referentiels/newTypeProposition")
    public String createTypeProposition(Model model, @ModelAttribute("typePropositionDTO") TypePropositionDTO typePropositionDTO, BindingResult result) {
		
    	if(result.hasErrors()) {
			return "typepropositions/typePropositionCreation";
		}
    	
    	Object typePropositionToCreate = new Object();
    	
    	try {
			typePropositionToCreate = typePropositionService.createTypeDocument(typePropositionDTO);
			model.addAttribute((TypeProposition)typePropositionToCreate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = typePropositionExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
    	
		return "typedocuments/typeDocumentConfirmation";
    }
    
    /**
     * Permet d'afficher tous les types de propositions sous forme de page
     */
    @GetMapping(value="/referentiels/typepropositions")
    public String getAll(Model model, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="10") int size){
    	
    	   	
    	Page<TypeProposition> typePropositions = typePropositionService.getAll(PageRequest.of(page, size));
    	
    	model.addAttribute("typePropositions", typePropositions.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", typePropositions.getNumber());
        model.addAttribute("totalPages", typePropositions.getTotalPages());
        model.addAttribute("totalElements", typePropositions.getTotalElements());
        model.addAttribute("size", typePropositions.getSize());
        
        return "typepropositions/typePropositionsPage";
        
    }
    

}
