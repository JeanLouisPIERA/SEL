package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IReponseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class ReponseRestController {

	@Autowired
	IReponseService reponseService;
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@PostMapping("/user/reponses/{id}")
	public ResponseEntity<Reponse> createReponse(@PathVariable @Valid Long id,
			@Valid @RequestBody ReponseDTO reponseDTO) throws EntityNotFoundException, DeniedAccessException,
			UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		log.info("Création d'une réponse");
		return new ResponseEntity<Reponse>(reponseService.createReponse(id, reponseDTO), HttpStatus.OK);
	}


	@ApiOperation(value = "Consultation d'une reponse par un adhérent", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "La reponse recherchée a été trouvée"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'offre ou la demande que vous voulez consulter n'existe pas"), })

	@GetMapping("/user/reponses/{id}")
	public ResponseEntity<Reponse> readReponse(@PathVariable @Valid Long id) throws EntityNotFoundException {
		log.info("Consultation d'une reponse par un adhérent");
		return new ResponseEntity<Reponse>(reponseService.readReponse(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Recherche de toutes les réponses à une proposition", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/user/reponses/proposition/{id}", produces = "application/json")
	public ResponseEntity<Page<Reponse>> searchAllByPropositionId(@PathVariable Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "6") int size) throws EntityNotFoundException {
		log.info("Recherche de toutes les réponses à une proposition");
		Page<Reponse> reponses = reponseService.findAllByWalletId(id, PageRequest.of(page, size));
		return new ResponseEntity<Page<Reponse>>(reponses, HttpStatus.OK);
	}

}
