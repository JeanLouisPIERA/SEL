package com.microseladherent.restController;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microseladherent.dao.IUserRepository;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.AdresseMailAlreadyExistsException;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.exceptions.MissingRequiredInformationException;
import com.microseladherent.exceptions.UsernameNotAvailableException;
import com.microseladherent.service.ISecurityService;
import com.microseladherent.service.IUserService;

@RestController
@RequestMapping("/sel")
public class UserRestController {
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IUserService userService;
	@Autowired
	ISecurityService securityService;

	
	  /**
	   * Ce endpoint permet à un membre du bureau d'obtenir la liste de tous les utilisateurs
	   *
	   * @return the list
	   */
	  @GetMapping("/bureau/accounts")
	  public ResponseEntity<List<User>> getAllUsers() {
	    return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à un adhérent de consulter son compte
	   * @param id
	   * @param userDTO
	   * @return
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @GetMapping("/users/accounts/{id}")
	  public ResponseEntity<User> consulterCompteAdhérent(@PathVariable Long id, @RequestBody UserDTO userDTO) throws EntityNotFoundException, DeniedAccessException {
		User userFound = userService.consulterCompteAdherent(id, userDTO);
		return new ResponseEntity<User>(userFound, HttpStatus.OK);
		  
	  }
	  
	  /**
	   * Ce endpoint permet à l'adhérent de créer son compte
	   *
	   * @param user the user
	   * @return the user
	 * @throws MissingRequiredInformationException 
	 * @throws UsernameNotAvailableException 
	 * @throws AdresseMailAlreadyExistsException 
	   */
	  @PostMapping("/users/accounts")
	  public ResponseEntity<User> createAdherent(@Valid @RequestBody UserDTO userDTO) throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException {
	    return new ResponseEntity<User>(userService.enregistrerAdherent(userDTO), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet à l'administrateur de créer le compte d'un membre du bureau
	   * @param userDTO
	   * @param username
	   * @param password
	   * @return
	   * @throws AdresseMailAlreadyExistsException
	   * @throws UsernameNotAvailableException
	   * @throws MissingRequiredInformationException
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	  @PostMapping("/admin/accounts")
	  public ResponseEntity<User> createBureau(@Valid @RequestBody UserDTO userDTO, @RequestParam String username, @RequestParam String password) 
			  throws AdresseMailAlreadyExistsException, 
			  UsernameNotAvailableException, 
			  MissingRequiredInformationException, 
			  EntityNotFoundException, 
			  DeniedAccessException {
	    return new ResponseEntity<User>(userService.enregistrerBureau(username, password, userDTO), HttpStatus.OK);
	  }
	  
	  /**
	   * Ce endpoint permet un utilisateur de s'authentifier avec son nom et son mot de passe
	   * @param userDTO
	   * @return
	   * @throws EntityNotFoundException
	   */
	  @PostMapping(value = "/users/login")
		public ResponseEntity<User> authentication(@RequestBody UserDTO userDTO) throws EntityNotFoundException {
			
		    User userAuthenticated = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		    return new ResponseEntity<User>(userAuthenticated, HttpStatus.OK);
		  
		}
	  
	  /**
	   * Ce endpoint permet à l'édhérent ou au membre du bureau de modifier son mot de passe en cas de perte par exemple  
	   * @param id
	   * @param password
	   * @param passwordConfirm
	   * @return
	   */
	  @PutMapping(value = "/users/accounts/updatePassword/{id}")
	  	public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestParam String password, @RequestParam String passwordConfirm) {
		  
			return null;
		  
	  }
	  
	  /**
	   * Ce endpoint permet à l'utilisateur de mettre à jour les autres infos de son compte (adresse mail, etc ...)  
	   * @param id
	   * @param userDTO
	   * @return
	   */
	  @PutMapping(value = "/users/accounts/updateInfos/{id}")
	  	public ResponseEntity<User> updateUserInfos(@PathVariable Long id, @RequestBody UserDTO userDTO) {
			
		  return null;
		  
	  }
	  
	  
	  /**
	   * Ce endpoint permet à un membre du Bureau de bloquer temporairement le compte d'un adhérent sans supprimer ses infos
	   * @param Id
	   * @param userDTO
	   * @return
	   */
	  @PutMapping(value = "/bureau/accounts/blocage/{id}")
	  	public ResponseEntity<User> updateStatutAdherent(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
			return null;
		  
	  }
	  
	  /**
	   * Ce endpoint permet à utilisateur de cloturer son compte définitivement en supprimant toutes les informations le concernant
	   * Le compte persiste mais les informations passent à NC 
	   * @param Id
	   * @param userDTO
	   * @return
	   */
	  @PutMapping(value="/users/accounts/cloture/{id}")
	  public ResponseEntity<User> deleteUserAccountByUser(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
			return null;
		  
	  }
	  
	  /**
	   * Ce endpoint permet à membre du bureau de cloturer définitivement le compte d'un adhérent en supprimant toutes les informations le concernant
	   * Le compte persiste mais les informations passent à NC 
	   * @param Id
	   * @param userDTO
	   * @return
	   */
	  @PutMapping(value="/bureau/accounts/cloture/{id}")
	  public ResponseEntity<User> deleteUserAccountByBureau(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
			return null;
		  
	  }
	  
	  /**
	   * Ce endpoint permet à un administrateur de clôturer le compte d'un membre du Bureau sans le supprimer 
	   * @param Id
	   * @param userDTO
	   * @return
	   */
	  @PutMapping(value = "/admin/accounts/cloture/{id}")
	  	public ResponseEntity<User> updateStatutBureau(@PathVariable Long Id, @RequestBody UserDTO userDTO) {
			return null;
		  
	  }
	  
	  
	  
	  

}
