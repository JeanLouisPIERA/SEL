package com.microseladherent.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.microseladherent.dao.IRoleRepository;
import com.microseladherent.dao.IUserRepository;
import com.microseladherent.dto.UserDTO;
import com.microseladherent.dto.UserMapperImpl;
import com.microseladherent.entities.Role;
import com.microseladherent.entities.RoleEnum;
import com.microseladherent.entities.User;
import com.microseladherent.exceptions.AdresseMailAlreadyExistsException;
import com.microseladherent.exceptions.EntityNotFoundException;
import com.microseladherent.exceptions.MissingRequiredInformationException;
import com.microseladherent.exceptions.UsernameNotAvailableException;
import com.microseladherent.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private SecurityServiceImpl securityService;
	@Autowired
	private UserMapperImpl userMapper;

	/**
	 * Cette méthode permet de persister un utilisateur en base de donnéees en respectant les contraintes ACID
	 * @throws AdresseMailAlreadyExistsException 
	 * @throws UsernameNotAvailableException 
	 * @throws MissingRequiredInformationException 
	 */
	@Override
	public User enregistrerUser(UserDTO userDTO) throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException {
		
		if(userDTO.getUsername().isEmpty()||userDTO.getPassword().isEmpty()||userDTO.getAdresseMail().isEmpty())
			throw new MissingRequiredInformationException("Tous les champs d'information doivent être remplis");
		
		Optional<User> usernameAlreadyExists = userRepository.findByUsername(userDTO.getUsername());
		if (usernameAlreadyExists.isPresent()) 
			throw new UsernameNotAvailableException("Ce nom d'adhérent est déjà utilisé"); 
		
		Optional<User> adresseMailAlreadyExists = userRepository.findByAdresseMail(userDTO.getAdresseMail());
		if(adresseMailAlreadyExists.isPresent())
			throw new AdresseMailAlreadyExistsException("Un compte d'adhérent existe déjà pour cette adresse mail");
		
		
		User userToCreate = userMapper.userDTOToUser(userDTO);
		
		if (!userDTO.getPasswordConfirm().equals(userDTO.getPassword()))
			throw new MissingRequiredInformationException("Le mot de passe n'a pas été correctement confirmé");
			
	    userToCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
	    userToCreate.setPasswordConfirm(bCryptPasswordEncoder.encode(userDTO.getPasswordConfirm()));
	    Role roleAdherent = roleRepository.findByName(RoleEnum.ADHERENT);
	    userToCreate.getRoles().add(roleAdherent);
	    return userRepository.save(userToCreate);
	}
	

	/**
	 * Cette méthode permet d'authentifier un utilisateur par son nom et son mot de passe
	 * @param username
	 * @param password
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException{
		    User userFound = securityService.autologin(username, password);
		    return userFound;
	}
	
	
	

	
}


