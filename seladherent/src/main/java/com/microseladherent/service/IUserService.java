package com.microseladherent.service;

import com.microseladherent.dto.UserDTO;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.AdresseMailAlreadyExistsException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.exceptions.MissingRequiredInformationException;
import com.microseladherent.exceptions.UsernameNotAvailableException;

public interface IUserService {

	public User enregistrerUser(UserDTO userDTO) throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException;
	
	public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException;
	
}
