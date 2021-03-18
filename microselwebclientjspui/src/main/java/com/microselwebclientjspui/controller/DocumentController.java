package com.microselwebclientjspui.controller;

import java.util.EnumSet;
import java.util.List;

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

import com.microselwebclientjspui.criteria.DocumentCriteria;

import com.microselwebclientjspui.dto.DocumentDTO;
import com.microselwebclientjspui.errors.DocumentExceptionMessage;
import com.microselwebclientjspui.objets.Document;
import com.microselwebclientjspui.objets.EnumStatutDocument;
import com.microselwebclientjspui.objets.TypeDocument;
import com.microselwebclientjspui.service.IDocumentService;
import com.microselwebclientjspui.service.ITypeDocumentService;


@Controller
public class DocumentController {
	
	@Autowired
	private IDocumentService documentService;
	
	@Autowired
	private ITypeDocumentService typeDocumentService;
	
	@Autowired
	private DocumentExceptionMessage documentExceptionMessage;
	
//CREATE PROPOSITION *****************************************************************************************************
    
    /**
     * Permet d'afficher le formulaire de création d'un document
     */
    @GetMapping("/referentiels/newDocument")
    public String newDocument(Model model){
    	
    	List<TypeDocument> typeDocuments = typeDocumentService.getAll();
    	
        model.addAttribute("documentDTO", new DocumentDTO());
        model.addAttribute("enumStatutDocument", EnumSet.allOf(EnumStatutDocument.class) );
        model.addAttribute("typeDocumentsList", typeDocuments);
        
        return "documents/documentCreation";
    }
    
    /**
     * Permet de valider l'enregistrement d'un nouveau document
     */
    @PostMapping("/referentiels/newDocument")
    public String createDocument(Model model, @ModelAttribute("documentDTO") DocumentDTO documentDTO, BindingResult result) {
		
    	if(result.hasErrors()) {
			return "documents/documentCreation";
		}
    	
    	Object documentToCreate = new Object();
    	
    	try {
			documentToCreate = documentService.createDocument(documentDTO);
			model.addAttribute((Document)documentToCreate);
		} catch (HttpClientErrorException e) {
			 String errorMessage = documentExceptionMessage.convertHttpClientErrorExceptionToExceptionMessage(e);
			 model.addAttribute("error", errorMessage);
		     return"/error";
		}
    	
		return "documents/documentConfirmation";
    }
    
    /**
     * Permet d'afficher une sélection de documents sous forme de page
     */
    @GetMapping(value="/referentiels/documents")
    public String searchByCriteria(Model model, @PathParam(value = "documentCriteria") DocumentCriteria documentCriteria, @RequestParam(name="page", defaultValue="0") int page, 
			@RequestParam(name="size", defaultValue="6") int size){
    	
    	List<TypeDocument> typeDocuments = typeDocumentService.getAll();
    	
    	model.addAttribute("documentCriteria", new DocumentCriteria());
    	model.addAttribute("enumStatutDocument", EnumStatutDocument.getListEnumStatutDocument());
    	model.addAttribute("typeDocumentsList", typeDocuments);
    	
    	Page<Document> documents = documentService.searchByCriteria(documentCriteria, PageRequest.of(page, size));
    	
    	model.addAttribute("documents", documents.getContent());
    	model.addAttribute("page", Integer.valueOf(page));
		model.addAttribute("number", documents.getNumber());
        model.addAttribute("totalPages", documents.getTotalPages());
        model.addAttribute("totalElements", documents.getTotalElements());
        model.addAttribute("size", documents.getSize());
        
        return "documents/documentsPage";
        
    }
    
    /**
     * Permet de lire le contenu d'un document
     */
    @GetMapping("/documents/{id}")
    public String readDocument(Model model, @PathVariable("id") Long id) {
    	try {
			Document readDocument = documentService.searchById(id);
			model.addAttribute("document", readDocument);
		
		} catch (HttpClientErrorException e) {
			String errorMessage = documentExceptionMessage.convertCodeStatusToExceptionMessage(e.getRawStatusCode());
			model.addAttribute("error", errorMessage);
			return"/error";
		}
    	return "documents/documentView";
    }
    

}
