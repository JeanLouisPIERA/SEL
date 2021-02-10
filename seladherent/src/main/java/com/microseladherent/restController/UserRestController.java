package com.microseladherent.restController;

import java.util.List;


import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.microseladherent.dto.UpdateUserDTO;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.EntityAlreadyExistsException;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.service.IUserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/sel")
@Validated
public class UserRestController {
	
	@Autowired
	private IUserService userService;
		
	//ENDPOINTS ACCESSIBLES A TOUS LES ROLES *******************************************************************
	
	
	/**
	 * Ce endpoint permet à l'adhérent de créer son compte
	 * Il renseigne le UserDTO des informations du compte à créer
	 * La date de création du compte est enregistrée
	 * @param userDTO
	 * @return
	 * @throws EntityAlreadyExistsException
	 */
	  @ApiOperation(value = "Enregistrement de son compte par un adhérent)", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte a été créé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 409, message = "Un autre compte existe déjà avec ces attributs"),
		})
	  @PostMapping("/users/accounts")
	  public ResponseEntity<User> createAccount(@Valid @RequestBody UserDTO userDTO) throws EntityAlreadyExistsException{
	    return new ResponseEntity<User>(userService.createAccount(userDTO), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à un adhérent de consulter son compte
	   * Seul un adhérent peut consulter les données de son compte quel que soit son statut ACTIVE, LOCKED ou CLOSED
	   * RGPD COMPLIANT
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   */
	  @ApiOperation(value = "Affichage des données de son compte par un adhérent)", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		})
	  @GetMapping("/users/accounts/{id}")
	  public ResponseEntity<User> displayAccount(@PathVariable @Valid Long id) throws EntityNotFoundException {
		User userFound = userService.readAccount(id);
		//TODO : implémenter l'authorization - seul un adhérent peut consulter son compte*/
		return new ResponseEntity<User>(userFound, HttpStatus.OK);
		  
	  }
	  
	  
	  /**
	   * Ce endpoint permet à l'adhérent au statut ACTIVE de mettre à jour les infos de son compte (adresse mail, etc ...) 
	   * Seul un adhérent peut mettre à jour son compte mais seulement si son statut est ACTIVE 
	   * Il renseigne tous les éléments à modifier dans le UpdateUserDTO qui ont la validation constraint @NotEmpty
	   * RGPD COMPLIANT
	   * @param id
	   * @param updateUserDTO
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   * @throws EntityAlreadyExistsException
	   */
	  @ApiOperation(value = "Mise à jour des données de son compte par un adhérent non clôturé et non bloqué)", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 409, message = "Mise à jour impossible : un autre compte existe déjà avec ces attributs"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value = "/users/accounts/update/{id}")
	  	public ResponseEntity<User> updateAccount(
	  			@PathVariable @Valid Long id, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException  {
		  User userToUpdate = userService.updateAccount(id, updateUserDTO); 
		//TODO implémenter l'authorization - seul un adhérent peut mettre à jour son compte
		  return new ResponseEntity<User>(userToUpdate, HttpStatus.OK);
		  
	  }
	  
	  
	  /**
	   * Ce endpoint permet à l'adhérent dont le statut du compte est ACTIVE de cloturer son compte 
	   * Le statut du compte passe à EXADHERENT ce qui l'empêche d'accéder à d'autres API
	   * Seul l'adhérent peut clôturer son compte
	   * La date de clôture est enregistrée
	   * Le compte reste persisté en respectant la CONTRAINTE ACID
	   * RGPD COMPLIANT
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Clôture de son compte par un adhérent non clôturé et non bloqué)", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value="/users/accounts/close/{id}")
	  public ResponseEntity<User> closeAccount(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		  User userToClose = userService.closeAccount(id); 
		  //TODO implémenter l'authorization - seul un adhérent peut clôturer son compte
		  return new ResponseEntity<User>(userToClose, HttpStatus.OK);
		  
	  }
	  
	  /**
	   * Ce endpoint permet à l'adhérent dont le statut du compte est CLOSED de réactiver son compte 
	   * Le statut du compte repasse à ACTIVE et l'adhérent peut à nouveau accéder à d'autres API
	   * Seul l'adhérent peut réactiver son compte
	   * La date de fin de clôture est enregistrée
	   * Le compte reste persisté en respectant la CONTRAINTE ACID
	   * RGPD COMPLIANT
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Réactivation de son compte par un adhérent dont le compte est clôturé)", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value="/users/accounts/reactive/{id}")
	  public ResponseEntity<User> reactiveAccount(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		  User userToClose = userService.reactiveAccount(id); 
		//TODO : implémenter l'authorization - seul un adhérent peut réactiver son compte
		  return new ResponseEntity<User>(userToClose, HttpStatus.OK);
		  
	  }
	
	  
	  //ENDPOINTS ACCESSIBLES AU ROLE MEMBRE DU BUREAU ******************************************************************* 
	
	  /**
	   * Ce endpoint permet à un membre du bureau d'obtenir la liste de tous les utilisateurs
	   * Seul un membre du bureau peut consulter cette liste
	   * @return
	   */
	  @ApiOperation(value = "Affichage de tous les comptes par un membre du bureau", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		})
	  @GetMapping("/bureau/accounts")
	  public ResponseEntity<List<User>> showAllUsers() {
		//TODO : implémenter l'authorization - seul un membre du bureau peut obtenir la liste de tous les utilisateurs
	    return new ResponseEntity<List<User>>(userService.showAllUsers(), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à un membre du Bureau de bloquer le compte d'un adhérent 
	   * L'adhérent peut continuer à consulter son compte = RGPD Compliant
	   * Mais le statut du compte de l'adhérent passe à LOCKED ce qui l'empêche d'accéder à d'autres API 
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Blocage par un membre du bureau du compte d'un adhérent non clôturé et non bloqué", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value = "/bureau/accounts/lock/{id}")
	  	public ResponseEntity<User> lockAccount(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		  User userToLock = userService.lockAccount(id); 
		//TODO : implémenter l'authorization - seul un membre du bureau peut bloquer un compte d'adhérent
		  return new ResponseEntity<User>(userToLock, HttpStatus.OK);
		  
	  }
	  
	  /**
	   * Ce endpoint permet à un membre du Bureau de débloquer le compte d'un adhérent 
	   * L'adhérent peut consulter son compte = RGPD Compliant
	   * Comme le statut de son compte passe à LOCKED à ACTIVE il peut à nouveau accéder à d'autres API 
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Déblocage du compte d'un adhérent par un membre du Bureau", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value = "/bureau/accounts/unlock/{id}")
	  	public ResponseEntity<User> unlockAccount(
	  			@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException{ 
	  	User userToLock = userService.unlockAccount(id); 
		//TODO : implémenter l'authorization - seul un membre du bureau peut débloquer un compte d'adhérent
		  return new ResponseEntity<User>(userToLock, HttpStatus.OK);
		  
	  }
	  
	  
	  //ENDPOINTS ACCESSIBLES AU ROLE ADMINISTRATEUR***********************************************************************
	  	  
	  /**
	   * Ce endpoint permet à un administrateur de promouvoir un adhérent à un rôle de membre du Bureau
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Promotion par un administrateur de promouvoir un adhérent en tant que un membre du bureau", response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping("/admin/accounts/bureau/{id}")
	  public ResponseEntity<User> createBureau(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		//TODO : implémenter l'authorization - seul un admin peut promouvoir un membre du bureau
	    return new ResponseEntity<User>(userService.updateToBureau(id), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à un administrateur de rétrograder un membre du Bureau à un rôle d'Adhérent
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @ApiOperation(value = "Rétrogadation par un administrateur d'un membre du bureau en tant qu'adhérent",response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 400, message = "Les informations fournies ne sont pas correctes"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value = "/admin/accounts/bureau/close/{id}")
	  	public ResponseEntity<User> closeBureau(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException{
		//TODO : implémenter l'authorization - seul un admin peut rétrograder un membre du bureau
		  return new ResponseEntity<User>(userService.closeBureau(id), HttpStatus.OK);
		  
	  }
	  
	  /**
	   * Ce endpoint permet à un administrateur de promouvoir un membre du Bureau à un rôle d'Administrateur
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	    @ApiOperation(value = "Promotion par un administrateur d'un membre du bureau en tant qu'administrateur",response = User.class)
				@ApiResponses(value = {
				        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
				        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
				        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
				})
	  @PutMapping("/admin/accounts/admin/{id}")
	  public ResponseEntity<User> createAdmin(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		  	  //TODO : implémenter l'authorization - seul un admin peut promouvoir un admin
	    return new ResponseEntity<User>(userService.updateToAdmin(id), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à un administrateur de rétrograder un administrateur au rôle de membre du Bureau
	   * @param id
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	    @ApiOperation(value = "Rétrogadation par un administrateur d'un autre administrateur en tant que membre du bureau",response = User.class)
		@ApiResponses(value = {
		        @ApiResponse(code = 201, message = "Le compte recherché a été trouvé"),
		        @ApiResponse(code = 413, message = "Ce compte n'existe pas"),
		        @ApiResponse(code = 423, message = "Le statut de ce compte ne permet pas de réaliser cette opération"),
		})
	  @PutMapping(value = "/admin/accounts/admin/close/{id}")
	  	public ResponseEntity<User> closeAdmin(@PathVariable @Valid Long id) throws EntityNotFoundException, DeniedAccessException {
		  //TODO : implémenter l'authorization - seul un admin peut rétrograder un admin
		  return new ResponseEntity<User>(userService.closeAdmin(id), HttpStatus.OK);
		  
	  }
	  
	  

}
