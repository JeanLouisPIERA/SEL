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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import com.microselwebclientjspui.criteria.DocumentCriteria;
import com.microselwebclientjspui.dto.TypeDocumentDTO;
import com.microselwebclientjspui.errors.TypeDocumentExceptionMessage;
import com.microselwebclientjspui.objets.Document;
import com.microselwebclientjspui.objets.EnumStatutDocument;
import com.microselwebclientjspui.objets.TypeDocument;
import com.microselwebclientjspui.service.ITypeDocumentService;

/*
 * Les types de documents à créer sont defSEL (définition SEL), defBOURSE (définition Bourse d'échanges), defRESEAU
 * (définition Réseau des SEL), CGU, FAQ, infosLegales etc ...
 */

@Controller
public class TypeDocumentController {
	
	@Autowired
	private ITypeDocumentService typeDocumentService;
	
	@Autowired
	private TypeDocumentExceptionMessage typeDocumentExceptionMessage;
	

	
    /**
     * Permet d'afficher le formulaire de création d'un type de document
     */
    @GetMapping("/referentiels/newTypeDocument")
    public String newTypeDocument(Model model){
        model.addAttribute("typeDocumentDTO", new TypeDocumentDTO());
        
        return "typedocuments/typeDocumentCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'un nouveau type de document
     */
    @PostMapping("/referentiels/newTypeDocument")
    public String createTypeDocument(Model model, @ModelAttribute("typeDocumentDTO") TypeDocumentDTO typeDocumentDTO, BindingResult result) {
		
    	if(result.hasErrors()) {
			return "typedocuments/typeDocumentCreation";
		}
    	
    	Object typeDocumentToCreate = new Object();
    	
    	try {
			typeDocumentToCreate = typeDocumentService.createTypeDocument(typeDocumentDTO);
			model.addAttribute((TypeDocument)typeDocumentToCreate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = typeDocumentExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
    	
		return "typedocuments/typeDocumentConfirmation";
    }
    
    /**
     * Permet d'afficher tous les types de documents sous forme de page
     */
    @GetMapping(value="/referentiels/typedocuments")
    public String getAll(Model model, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="10") int size){
    	
    	   	
    	Page<TypeDocument> typeDocuments = typeDocumentService.getAll(PageRequest.of(page, size));
    	
    	model.addAttribute("typeDocuments", typeDocuments.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", typeDocuments.getNumber());
        model.addAttribute("totalPages", typeDocuments.getTotalPages());
        model.addAttribute("totalElements", typeDocuments.getTotalElements());
        model.addAttribute("size", typeDocuments.getSize());
        
        return "typedocuments/typeDocumentsPage";
        
    }
    

}
