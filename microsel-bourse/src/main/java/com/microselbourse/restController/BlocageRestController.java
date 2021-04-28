package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.criteria.BlocageCriteria;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IBlocageService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class BlocageRestController {

	@Autowired
	private IBlocageService blocageService;
	
	
	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs blocages", response = Blocage.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/admin/blocages", produces = "application/json")
	public ResponseEntity<Page<Blocage>> searchAllBlocagesByCriteria(
			@PathParam("blocageCriteria") BlocageCriteria blocageCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		Page<Blocage> blocages = blocageService.searchAllPropositionsByCriteria(blocageCriteria,
				PageRequest.of(page, size));
		return new ResponseEntity<Page<Blocage>>(blocages, HttpStatus.OK);
	}
	
	

	@ApiOperation(value = " Annulation d'un blocage d'adherent suite à un échange en anomalie", response = Blocage.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le blocage recherché a été annulé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Le blocage que vous voulez annuler n'existe pas"), })
	@PutMapping("/admin/blocages/close/{id}")
	public ResponseEntity<Blocage> annulerBlocage(@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Blocage>(blocageService.annulerBlocage(id), HttpStatus.OK);
	}

}
