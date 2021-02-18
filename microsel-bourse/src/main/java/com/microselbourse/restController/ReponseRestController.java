package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.criteria.ReponseCriteria;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IReponseService;


@RestController
@RequestMapping("/sel/bourse")
@Validated
public class ReponseRestController {
	
	@Autowired
	IReponseService reponseService;
	
	
	@PostMapping("/reponses/{propositionId}")
	  public ResponseEntity<Reponse> createReponse(
			  @PathVariable @Valid Long propositionId, @Valid @RequestBody ReponseDTO reponseDTO) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException  {
	    return new ResponseEntity<Reponse>(reponseService.createReponse(propositionId, reponseDTO), HttpStatus.OK);
	  }
	
	
	 @GetMapping(value="/reponses", produces="application/json") 
	  public ResponseEntity<Page<Reponse>> searchAllPropositionsByCriteria(
			  @PathParam("reponseCriteria") ReponseCriteria reponseCriteria, @RequestParam Pageable pageable) { 
		  Page<Reponse> reponses = reponseService.searchAllReponsesByCriteria(reponseCriteria, PageRequest.of(0, 6)); 
		  	return new ResponseEntity<Page<Reponse>>(reponses, HttpStatus.OK); }  

}
