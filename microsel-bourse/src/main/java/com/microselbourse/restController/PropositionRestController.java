package com.microselbourse.restController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

/*import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IPropositionService;
import com.microselbourse.service.impl.RabbitMQSender;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class PropositionRestController {
	
	@Autowired
	private IPropositionService propositionService;
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	  @ApiOperation(value = "Enregistrement d'une proposition par un adhérent",  response = Proposition.class)
		  @ApiResponses(value = {
		  @ApiResponse(code = 201, message = 
				  "La proposition a été créée"),
		  @ApiResponse(code = 400, message = 
				  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 409, message = 
				  "Une autre proposition existe déjà avec ces attributs"), })
	 
	  @PostMapping("/user/propositions/create")
	  public ResponseEntity<Proposition> createProposition(@Valid @RequestBody PropositionDTO propositionDTO) throws EntityAlreadyExistsException, EntityNotFoundException, DeniedAccessException {
		  //rabbitMQSender.send(propositionDTO);
		  return new ResponseEntity<Proposition>(propositionService.createProposition(propositionDTO), HttpStatus.OK);
	  }
	  
	  
		
	  @ApiOperation(value = "Recherche multi-critères d'une ou plusieurs propositions", response = Proposition.class)  
		  @ApiResponses(value = {
		  @ApiResponse(code = 200, message = 
				  "La recherche a été réalisée avec succés"), })
		  
		  @GetMapping(value="/user/propositions", produces="application/json") 
		  public ResponseEntity<Page<Proposition>> searchAllPropositionsByCriteria(
				  @PathParam("propositionCriteria") PropositionCriteria propositionCriteria,
				  @RequestParam(name = "page", defaultValue= "0") int page, @RequestParam(name="size", defaultValue= "10") int size) { 
		  	  
		  	  Page<Proposition> propositions = propositionService.searchAllPropositionsByCriteria(propositionCriteria, PageRequest.of(page, size)); 	
		  	  List<Proposition> propositionsList = propositions.getContent();
		  	  for(Proposition proposition : propositionsList) {System.out.println("titre" + proposition.getTitre());}
			  return new ResponseEntity<Page<Proposition>>(propositions, HttpStatus.OK); 
		  }
	  

		  
	 
	  @ApiOperation(value = "Consultation d'une proposition par un adhérent", response = Proposition.class)  
		  @ApiResponses(value = {
			  @ApiResponse(code = 201, message =
			  "La proposition recherchée a été trouvée"),
			  @ApiResponse(code = 400, message =
			  "Les informations fournies ne sont pas correctes"),
			  @ApiResponse(code = 413, message = 
			  "L'offre ou la demande que vous voulez consulter n'existe pas"), })
		 
	  @GetMapping("/user/propositions/{id}")
	  public ResponseEntity<Proposition> readProposition(@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Proposition>(propositionService.readProposition(id), HttpStatus.OK);  
	  }
	 
	 
	  @ApiOperation(value = "Mise à jour d'une proposition par son émetteur)",response = Proposition.class)
		  @ApiResponses(value = {
			  @ApiResponse(code = 201, message =
			  "La proposition à modifier a été trouvée"),
			  @ApiResponse(code = 400, message =
			  "Les informations fournies ne sont pas correctes"),
			  @ApiResponse(code = 409, message =
			  "Mise à jour impossible : une autre proposition existe déjà avec ces attributs"
			  ),
			  @ApiResponse(code = 413, message = "Cette proposition n'existe pas"), })
		 
	  @PutMapping(value = "/user/propositions/update/{id}")
	  public ResponseEntity<Proposition> updateProposition(
  			@PathVariable @Valid Long id, @Valid @RequestBody PropositionDTO propositionDTO) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException {
		  return new ResponseEntity<Proposition>(propositionService.updateProposition(id, propositionDTO), HttpStatus.OK);
		  
	  }
	  
	  @ApiOperation(value = "Clôture d'une proposition par son émetteur avant la date de fin de publication)", response = Proposition.class)
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "La proposition à clôturer a été trouvée"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'offre ou la demande que vous voulez clôturer n'existe pas"), 
		  @ApiResponse(code = 423, message =
		  "Clôture impossible"
		  ),
		  @ApiResponse(code = 413, message = "Cette proposition n'existe pas"), })
		 
	  @PutMapping(value = "/user/propositions/close/{id}")
	  public ResponseEntity<Proposition> closeProposition(
  			@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException {
		 return new ResponseEntity<Proposition>(propositionService.closeProposition(id), HttpStatus.OK);
	  }  

}
