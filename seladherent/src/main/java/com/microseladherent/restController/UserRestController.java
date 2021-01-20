package com.microseladherent.restController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microseladherent.dao.IUserRepository;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.AdresseMailAlreadyExistsException;
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
	   * Permet d'obtenir la liste de tous les utilisateurs
	   *
	   * @return the list
	   */
	  @GetMapping("/users")
	  public ResponseEntity<List<User>> getAllUsers() {
	    return new ResponseEntity<List<User>>(userRepository.findAll(), HttpStatus.OK);
	  }
	  
	  /**
	   * Creation d'un utilisateur
	   *
	   * @param user the user
	   * @return the user
	 * @throws MissingRequiredInformationException 
	 * @throws UsernameNotAvailableException 
	 * @throws AdresseMailAlreadyExistsException 
	   */
	  @PostMapping("/users")
	  public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException {
	    return new ResponseEntity<User>(userService.enregistrerUser(userDTO), HttpStatus.OK);
	  }
	  
	  /**
	   * Permet d'authentifier un utilisateur à partir de son nom et de son mot de passe
	   * @param userDTO
	   * @return
	   * @throws EntityNotFoundException
	   */
	  @PostMapping(value = "/users/login")
		public ResponseEntity<User> Authentication(@RequestBody UserDTO userDTO) throws EntityNotFoundException {
			
		    User userAuthenticated = userService.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
		    return new ResponseEntity<User>(userAuthenticated, HttpStatus.OK);
		  
		}

}
