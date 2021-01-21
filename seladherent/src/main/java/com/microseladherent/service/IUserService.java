package com.microseladherent.service;

import java.util.Optional;

import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.AdresseMailAlreadyExistsException;
import com.microseladherent.exceptions.DeniedAccessException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.exceptions.MissingRequiredInformationException;
import com.microseladherent.exceptions.UsernameNotAvailableException;

public interface IUserService {

	public User enregistrerAdherent(UserDTO userDTO) 
			throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException;
	
	public User enregistrerBureau(String username, String password, UserDTO userDTO) 
			throws AdresseMailAlreadyExistsException, 
			UsernameNotAvailableException, 
			MissingRequiredInformationException, 
			EntityNotFoundException, 
			DeniedAccessException;
	
	public User findByUsernameAndPassword(String username, String password) 
			throws EntityNotFoundException;
	
	public User consulterCompteAdherent(Long id, UserDTO userDTO) 
			throws EntityNotFoundException, DeniedAccessException; 
	
}
