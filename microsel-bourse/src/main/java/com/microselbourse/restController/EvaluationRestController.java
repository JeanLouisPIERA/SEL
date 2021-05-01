package com.microselbourse.restController;

import java.io.UnsupportedEncodingException;
import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.criteria.EvaluationCriteria;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEvaluationService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class EvaluationRestController {

	@Autowired
	private IEvaluationService evaluationService;

	@ApiOperation(value = "Enregistrement d'une évaluation par un adhérent", response = Evaluation.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "L'évaluation a été créée"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 409, message = "Une autre évaluation existe déjà avec ces attributs"), })

	@PostMapping("/user/evaluations/echange/{echangeId}")
	public ResponseEntity<Evaluation> createEvaluation(@PathVariable Long echangeId,
			@Valid @RequestBody EvaluationDTO evaluationDTO) throws EntityNotFoundException,
			EntityAlreadyExistsException, UnsupportedEncodingException, MessagingException {
		return new ResponseEntity<Evaluation>(evaluationService.createEvaluation(echangeId, evaluationDTO),
				HttpStatus.OK);
	}

	@ApiOperation(value = "Recherche des évaluations d'un échange", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/user/evaluations/echange/{id}", produces = "application/json")
	public ResponseEntity<List<Evaluation>> searchAllByEchangeId(@PathVariable Long id) {
		List<Evaluation> evaluations = evaluationService.findAllByEchangeIdAndNotModerated(id);
		return new ResponseEntity<List<Evaluation>>(evaluations, HttpStatus.OK);
	}
	
	
	 @ApiOperation(value = "Modération d'une évaluation", response = Evaluation.class)  
	  @ApiResponses(value = {
		  @ApiResponse(code = 201, message =
		  "L'évaluation recherchée a été trouvée"),
		  @ApiResponse(code = 400, message =
		  "Les informations fournies ne sont pas correctes"),
		  @ApiResponse(code = 413, message = 
		  "L'évaluatione que vous voulez consulter n'existe pas"), })
	 
	  @PutMapping("/admin/evaluations/moderation/{id}")
	  public ResponseEntity<Evaluation> modererEvaluation(@PathVariable("id") @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		return new ResponseEntity<Evaluation>(evaluationService.modererEvaluation(id), HttpStatus.OK);  
	  }
	 
	 @ApiOperation(value = "Recherche multi-critères d'une ou plusieurs evaluations", response = Proposition.class)
		@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

		@GetMapping(value = "/admin/evaluations", produces = "application/json")
		public ResponseEntity<Page<Evaluation>> searchAllEvaluationsByCriteria(
				@PathParam("evaluationCriteria") EvaluationCriteria evaluationCriteria,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "10") int size) {

			Page<Evaluation> evaluations = evaluationService.searchAllEvaluationsByCriteria(evaluationCriteria,
					PageRequest.of(page, size));
			return new ResponseEntity<Page<Evaluation>>(evaluations, HttpStatus.OK);
		}
		

}
