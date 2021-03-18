package com.microselreferentiels.restController;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselreferentiels.criteria.DocumentCriteria;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.service.IDocumentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/referentiels")
@Validated
public class DocumentRestController {
	
	@Autowired
	private IDocumentService documentService;
	
	@ApiOperation(value = "Enregistrement d'un document statique par un admin",  response = Document.class)
	  @ApiResponses(value = {
	  @ApiResponse(code = 201, message = 
			  "Le document a été créée"),
	  @ApiResponse(code = 400, message = 
			  "Les informations fournies ne sont pas correctes"),
	  @ApiResponse(code = 409, message = 
			  "Un autre document existe déjà avec ces attributs"), })

	@PostMapping("/documents")
	public ResponseEntity<Document> createStaticDocument(@Valid @RequestBody DocumentDTO documentDTO) throws EntityAlreadyExistsException, EntityNotFoundException {
	  return new ResponseEntity<Document>(documentService.createStaticDocument(documentDTO), HttpStatus.OK);
	}
	
	 @ApiOperation(value = "Recherche multi-critères d'un ou plusieurs documents", response = Document.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/documents", produces="application/json") 
	  public ResponseEntity<Page<Document>> searchAllDocumentsByCriteria(
			  @PathParam("documentCriteria") DocumentCriteria documentCriteria,
			  @RequestParam(name = "page", defaultValue= "0") int page, @RequestParam(name="size", defaultValue= "10") int size) { 
	  	  
	  	  Page<Document> documents = documentService.searchAllDocumentsByCriteria(documentCriteria, PageRequest.of(page, size)); 	
		  return new ResponseEntity<Page<Document>>(documents, HttpStatus.OK); 
	  }
 
	  @ApiOperation(value = "Consultation du contenu d'un document", response = Document.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Le document recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "Le document que vous voulez consulter n'existe pas"), })
	 
	  @GetMapping("/documents/{id}")
	  public ResponseEntity<Document> readDocument(@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Document>(documentService.readDocument(id), HttpStatus.OK);  
	  }
 
	 
}
