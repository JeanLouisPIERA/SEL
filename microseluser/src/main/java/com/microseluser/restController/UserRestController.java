package com.microseluser.restController;

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

import com.microseluser.criteria.UserCriteria;
import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;
import com.microseluser.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

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
	@GetMapping("/admin/listaccounts")
	public ResponseEntity<List<User>> showAllUsers() {
		return new ResponseEntity<List<User>>(userService.showAllUsers(), HttpStatus.OK);
	}

	@ApiOperation(value = "Affichage de tous les comptes par un membre du bureau", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"), })
	@GetMapping("/admin/accounts")
	public ResponseEntity<Page<User>> searchAllUsersByCriteria(@PathParam("userCriteria") UserCriteria userCriteria,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		Page<User> users = userService.searchAllUsersByCriteria(userCriteria, PageRequest.of(page, size));
		return new ResponseEntity<Page<User>>(users, HttpStatus.OK);
	}

	/**
	 * Ce endpoint permet à un microservice d'accéder au compte d'un adhérent via
	 * feign peut consulter les données de son compte RGPD COMPLIANT
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@ApiIgnore
	@ApiOperation(value = "Affichage des données d'un compte d'adhérent pour un microservice)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Ce compte n'existe pas"), })
	@GetMapping("/accounts/{id}")
	public ResponseEntity<User> displayAccount(@PathVariable @Valid String id) throws EntityNotFoundException {
		User userFound = userService.readAccount(id);
		return new ResponseEntity<User>(userFound, HttpStatus.OK);

	}

	/**
	 * Ce endpoint permet à un adhérent d'accéder à son compte peut consulter les
	 * données de son compte RGPD COMPLIANT
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@ApiOperation(value = "Affichage des données de son compte pour un adhérent)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Ce compte n'existe pas"), })
	@GetMapping("/user/accounts/account")
	public ResponseEntity<User> securisedDisplayAccount(@RequestParam @Valid String adherentMyId)
			throws EntityNotFoundException {
		User userFound = userService.readAccount(adherentMyId);
		return new ResponseEntity<User>(userFound, HttpStatus.OK);

	}

	/**
	 * Ce endpoint permet à un admin d'accéder au détail d'un compte adherent
	 * 
	 * @param id
	 * @return
	 * @throws EntityNotFoundException
	 */
	@ApiOperation(value = "Affichage des données d'un compte adhérent par une Admin)", response = User.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
			@ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
			@ApiResponse(code = 413, message = "Ce compte n'existe pas"), })
	@GetMapping("/admin/accounts/{id}")
	public ResponseEntity<User> securisedDisplayAccountToAdmin(@PathVariable @Valid String id)
			throws EntityNotFoundException {
		User userFound = userService.readAccount(id);
		return new ResponseEntity<User>(userFound, HttpStatus.OK);

	}

}
