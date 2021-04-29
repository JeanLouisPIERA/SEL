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

import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.dto.TypeDocumentDTO;
import com.microselreferentiels.dto.TypePropositionDTO;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.entities.TypeProposition;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.service.ITypeDocumentService;
import com.microselreferentiels.service.ITypePropositionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/referentiels")
@Validated
public class TypePropositionRestController {
	
	@Autowired
	private ITypePropositionService typePropositionService;
	
	@ApiOperation(value = "Enregistrement d'un type de proposition par un admin",  response = TypeProposition.class)
	  @ApiResponses(value = {
	  @ApiResponse(code = 201, message = 
			  "Le type de proposition a été créé"),
	  @ApiResponse(code = 400, message = 
			  "Les informations fournies ne sont pas correctes"),
	  @ApiResponse(code = 409, message = 
			  "Un autre type de proposition existe déjà avec ces attributs"), })

	@PostMapping("/admin/typepropositions")
	public ResponseEntity<TypeProposition> createTypeProposition(@Valid @RequestBody TypePropositionDTO typePropositionDTO) throws EntityAlreadyExistsException {
	  return new ResponseEntity<TypeProposition>(typePropositionService.createTypeProposition(typePropositionDTO), HttpStatus.OK);
	}
	
	 @ApiOperation(value = "Recherche multi-critères d'un ou plusieurs types de proposition", response = TypeProposition.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/admin/typepropositions/Page", produces="application/json") 
	  public ResponseEntity<Page<TypeProposition>> getAllTypePropositionsByCriteria(
			  @RequestParam(name = "page", defaultValue= "0") int page, @RequestParam(name="size", defaultValue= "10") int size) { 
	  	  
	  	  Page<TypeProposition> typePropositionsPage = typePropositionService.getAllTypePropositionsPaginated(PageRequest.of(page, size)); 	
		  return new ResponseEntity<Page<TypeProposition>>(typePropositionsPage, HttpStatus.OK); 
	  }
	 
	 @ApiOperation(value = "Recherche de tous les types de propositions", response = TypeDocument.class)  
	  @ApiResponses(value = {
	  @ApiResponse(code = 200, message = 
			  "La recherche a été réalisée avec succés"), })
	  
	  @GetMapping(value="/typepropositions", produces="application/json") 
	  public ResponseEntity<List<TypeProposition>> getAllTypePropositions() { 
	  	  List<TypeProposition> typePropositions = typePropositionService.getAllTypePropositions(); 	
		  return new ResponseEntity<List<TypeProposition>>(typePropositions, HttpStatus.OK); 
	  }
 
	 @ApiOperation(value = "Consultation du contenu d'un type de proposition", response = TypeProposition.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Le type de proposition recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "Le type de proposition que vous voulez consulter n'existe pas"), })
	 
	  @GetMapping("/typepropositions/{id}")
	  public ResponseEntity<TypeProposition> readTypeProposition(@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<TypeProposition>(typePropositionService.readTypeProposition(id), HttpStatus.OK);  
	  }
	 
	 @ApiOperation(value = "Consultation du contenu d'un type de proposition à partir de son typeName", response = TypeProposition.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Le type de proposition recherché a été trouvé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "Le type de proposition que vous voulez consulter n'existe pas"), })
	 
	  @GetMapping("/typepropositions/{typeName}")
	  public ResponseEntity<TypeProposition> readTypePropositionByTypeName(@PathVariable @Valid String typeName) throws EntityNotFoundException {
		return new ResponseEntity<TypeProposition>(typePropositionService.readTypePropositionByTypeName(typeName), HttpStatus.OK);  
	  }

}
