package com.microseluser.restController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;
import com.microseluser.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel/users")
@Validated
public class UserRestController {

	@Autowired
	private IUserService userService;

	/**
	 * Ce endpoint permet à un membre du bureau d'obtenir la liste de tous les
	 * utilisateurs Seul un membre du bureau peut consulter cette liste
	 * 
	 * @return
	 */
	@ApiOperation(value = "Affichage de tous les comptes par un membre du bureau", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"), })
	@GetMapping("/accounts")
	public ResponseEntity<List<User>> showAllUsers() {
		return new ResponseEntity<List<User>>(userService.showAllUsers(), HttpStatus.OK);
	}

	/**
	 * Ce endpoint permet à un adhérent de consulter son compte Seul un adhérent
	 * peut consulter les données de son compte RGPD COMPLIANT
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@ApiOperation(value = "Affichage des données de son compte par un adhérent)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Ce compte n'existe pas"), })
	@GetMapping("/accounts/{id}")
	public ResponseEntity<User> displayAccount(@PathVariable @Valid String id) throws EntityNotFoundException {
		User userFound = userService.readAccount(id);
		return new ResponseEntity<User>(userFound, HttpStatus.OK);

	}

}
