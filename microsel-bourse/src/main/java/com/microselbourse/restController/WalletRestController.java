package com.microselbourse.restController;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IWalletService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class WalletRestController {

	@Autowired
	private IWalletService walletService;

	@ApiOperation(value = "Recherche multi-critères d'un ou plusieurs portefeuilles", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/bureau/wallets", produces = "application/json")
	public ResponseEntity<Page<Wallet>> searchAllWalletssByCriteria(
			@PathParam("walletCriteria") WalletCriteria walletCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "6") int size) {
		Page<Wallet> wallets = walletService.searchAllWalletsByCriteria(walletCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<Wallet>>(wallets, HttpStatus.OK);
	}

	@ApiOperation(value = "Consultation d'un portefeuille par un adhérent", response = Wallet.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le portefeuille recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Le portefeuille que vous voulez consulter n'existe pas"), })

	@GetMapping("/user/wallets/adherent")
	public ResponseEntity<Wallet> readWalletByUserId(@PathParam("userId") String userId) throws EntityNotFoundException {
		return new ResponseEntity<Wallet>(walletService.readByUserId(userId), HttpStatus.OK);
	}

	@ApiOperation(value = "Consultation d'un portefeuille par son N° identifiant", response = Wallet.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le portefeuille recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Le portefeuille que vous voulez consulter n'existe pas"), })

	@GetMapping("/user/wallets/{id}")
	public ResponseEntity<Wallet> readWalletById(@PathVariable @Valid Long id) throws EntityNotFoundException {
		return new ResponseEntity<Wallet>(walletService.readById(id), HttpStatus.OK);
	}
	
	

}
