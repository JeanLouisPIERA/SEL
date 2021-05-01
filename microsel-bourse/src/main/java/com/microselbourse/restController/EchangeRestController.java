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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEchangeService;
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

	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs echanges", response = Echange.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/all/echanges", produces = "application/json")
	public ResponseEntity<Page<Echange>> searchAllEchangesByCriteria(
			@PathParam("echangeCriteria") EchangeCriteria echangeCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		Page<Echange> echanges = echangeService.searchAllEchangesByCriteria(echangeCriteria,
				PageRequest.of(page, size));
		return new ResponseEntity<Page<Echange>>(echanges, HttpStatus.OK);
	}

	@ApiOperation(value = "Consultation d'un echange par un adhérent", response = Echange.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été trouvée"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échangeque vous voulez consulter n'existe pas"), })

	@GetMapping("/user/echanges/{id}")
	public ResponseEntity<Echange> readEchange(@PathVariable @Valid Long id) throws EntityNotFoundException {

		return new ResponseEntity<Echange>(echangeService.readEchange(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Consultation du détail d'un echange par un adhérent", response = Reponse.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été trouvée"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échangeque vous voulez consulter n'existe pas"), })

	@GetMapping("/user/echanges/details/{id}")
	public ResponseEntity<Reponse> readDetailsEchange(@PathVariable @Valid Long id) throws EntityNotFoundException {

		return new ResponseEntity<Reponse>(reponseService.readReponse(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Confirmation d'un echange par l'adhérent émetteur de la proposition initiale", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été confirmé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échange que vous voulez confirmer n'existe pas"), })
	@PostMapping("/user/echanges/confirmer/{id}")
	public ResponseEntity<Echange> confirmerEchange(@PathVariable @Valid Long id, @RequestBody String intervenantId)
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException {
		return new ResponseEntity<Echange>(echangeService.confirmerEchange(id, intervenantId), HttpStatus.OK);
	}

	@ApiOperation(value = "Annulation d'un echange par l'adhérent émetteur de la proposition initiale", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été annulé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échange que vous voulez annuler n'existe pas"), })
	@PutMapping("/user/echanges/annuler/{id}")
	public ResponseEntity<Echange> annulerEchange(@PathVariable @Valid Long id, @RequestBody String intervenantId)
			throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException {
		return new ResponseEntity<Echange>(echangeService.annulerEchange(id, intervenantId), HttpStatus.OK);
	}

	@ApiOperation(value = "Validation d'un echange par l'adhérent émetteur de la proposition initiale ou l'adhérent récepteur", response = Echange.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été validé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échange que vous voulez valider n'existe pas"), })
	@PutMapping("/user/echanges/valider/{id}")
	public ResponseEntity<Echange> validerEchange(@PathVariable @Valid Long id, @RequestBody String intervenantId)
			throws UnsupportedEncodingException, EntityNotFoundException, DeniedAccessException, MessagingException,
			EntityAlreadyExistsException {
		return new ResponseEntity<Echange>(echangeService.validerEchange(id, intervenantId), HttpStatus.OK);
	}

	@ApiOperation(value = "Refus de valider un echange par l'adhérent émetteur de la proposition initiale ou l'adherent récepteur", response = Echange.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Léchange recherché a été refusé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "L'échange que vous voulez refuser n'existe pas"), })
	@PutMapping("/user/echanges/refuser/{id}")
	public ResponseEntity<Echange> refuserEchange(@PathVariable @Valid Long id, @RequestBody String intervenantId)
			throws UnsupportedEncodingException, EntityNotFoundException, DeniedAccessException, MessagingException,
			EntityAlreadyExistsException {
		return new ResponseEntity<Echange>(echangeService.refuserEchange(id, intervenantId), HttpStatus.OK);
	}

}
