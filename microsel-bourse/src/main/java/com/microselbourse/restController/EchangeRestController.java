package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IPropositionService;
import com.microselbourse.service.IReponseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class EchangeRestController {
	
	@Autowired
	private IEchangeService echangeService;
	
	@Autowired
	private IReponseService reponseService;
	
	
	@ApiOperation(value = "Consultation d'un echange par un adhérent", response = Echange.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Léchange recherché a été trouvée"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'échangeque vous voulez consulter n'existe pas"), })
	 
	@GetMapping("/echanges/{id}")
	public ResponseEntity<Echange> readEchange(@PathVariable @Valid Long id) throws EntityNotFoundException {
	
		return new ResponseEntity<Echange>(echangeService.readEchange(id), HttpStatus.OK);  
	}
	
	@ApiOperation(value = "Consultation du détail d'un echange par un adhérent", response = Reponse.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Léchange recherché a été trouvée"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'échangeque vous voulez consulter n'existe pas"), })
	 
	@GetMapping("/echanges/details/{id}")
	public ResponseEntity<Reponse> readDetailsEchange(@PathVariable @Valid Long id) throws EntityNotFoundException {
	
		return new ResponseEntity<Reponse>(reponseService.readReponse(id), HttpStatus.OK);  
	}
	
	
	@ApiOperation(value = "Confirmation d'un echange par l'adhérent émetteur de la proposition initiale", response = Proposition.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Léchange recherché a été confirmé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'échange que vous voulez confirmer n'existe pas"), })
	@PutMapping("echanges/confirmer/{id}") 
	public ResponseEntity<Echange> confirmerEchange(
			@PathVariable @Valid Long id, 
			@RequestParam(name = "emetteurId") Long emetteurId, 
			@RequestParam(name= "decision") Boolean decision) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException{
		return new ResponseEntity<Echange> (echangeService.confirmerEchange(id, emetteurId, decision), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Validation d'un echange par l'adhérent émetteur de la proposition initiale et l'adhérent récepteur dont la réponse a été acceptée", response = Echange.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Léchange recherché a été validé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'échange que vous voulez valider n'existe pas"), })
	@PutMapping("echanges/valider/{id})")
	public ResponseEntity<Echange> validerEchange (
			@PathVariable @Valid Long id,
			@RequestParam(name="validateurId")Long validateurId,
			@RequestParam(name="decision") Boolean decision) throws UnsupportedEncodingException, EntityNotFoundException, DeniedAccessException, MessagingException {
		return new ResponseEntity<Echange> (echangeService.validerEchange(id, validateurId, decision), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Traitement d'un echange dont le statut est en anomalie à l'échéance soit différent de CLOTURE ou LITIGE ", response = Echange.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Léchange recherché a été soldé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'échange que vous voulez solder n'existe pas"), })
	@PutMapping("echanges/solder/{id}")
	public ResponseEntity<Echange> solderEchange(@PathVariable @Valid Long id) {
		return new ResponseEntity<Echange> (echangeService.solderEchange(id), HttpStatus.OK);
	}
	
	

}
