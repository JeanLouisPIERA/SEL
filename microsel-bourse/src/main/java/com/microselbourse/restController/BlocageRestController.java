package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.entities.Blocage;
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
	
	@ApiOperation(value = " Annulation d'un blocage d'adherent suite à un échange en anomalie", response = Blocage.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "Le blocage recherché a été annulé"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "Le blocage que vous voulez annuler n'existe pas"), })
	@PutMapping("blocages/annuler/{id}")
	public ResponseEntity<Blocage> annulerBlocage (
			@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Blocage> (blocageService.annulerBlocage(id), HttpStatus.OK);
	}

}
