package com.microselbourse.restController;

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

import com.microselbourse.criteria.WalletCriteria;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.ITransactionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/bourse")
@Validated
public class TransactionRestController {

	@Autowired
	ITransactionService transactionService;

	@ApiOperation(value = "Recherche de toutes les transactions d'un portefeuille", response = Proposition.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "La recherche a été réalisée avec succés"), })

	@GetMapping(value = "/bureau/transactions/wallet/{id}", produces = "application/json")
	public ResponseEntity<Page<Transaction>> searchAllByWalletId(@PathVariable Long id,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "6") int size) throws EntityNotFoundException {
		Page<Transaction> transactions = transactionService.findAllByWalletId(id, PageRequest.of(page, size));
		return new ResponseEntity<Page<Transaction>>(transactions, HttpStatus.OK);
	}

}
