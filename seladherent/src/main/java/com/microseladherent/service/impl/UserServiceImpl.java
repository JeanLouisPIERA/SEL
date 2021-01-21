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
import com.microseladherent.exceptions.DeniedAccessException;
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
	public User enregistrerAdherent(UserDTO userDTO) throws AdresseMailAlreadyExistsException, UsernameNotAvailableException, MissingRequiredInformationException {
		
		if(userDTO.getUsername().isEmpty()||userDTO.getPassword().isEmpty()||userDTO.getAdresseMail().isEmpty())
			throw new MissingRequiredInformationException("Tous les champs d'information doivent être remplis");
		
		Optional<User> usernameAlreadyExists = userRepository.findByUsername(userDTO.getUsername());
		if (usernameAlreadyExists.isPresent()) 
			throw new UsernameNotAvailableException("Ce nom d'adhérent est déjà utilisé"); 
		
		Optional<User> adresseMailAlreadyExists = userRepository.findByEmail(userDTO.getAdresseMail());
		if(adresseMailAlreadyExists.isPresent())
			throw new AdresseMailAlreadyExistsException("Un compte d'adhérent existe déjà pour cette adresse mail");
		
		
		User userToCreate = userMapper.userDTOToUser(userDTO);
		
		if (!userDTO.getPasswordConfirm().equals(userDTO.getPassword()))
			throw new MissingRequiredInformationException("Le mot de passe n'a pas été correctement confirmé");
			
	    userToCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
	    userToCreate.setPasswordConfirm(bCryptPasswordEncoder.encode(userDTO.getPasswordConfirm()));
	    userToCreate.setRole(roleRepository.findByName(RoleEnum.ADHERENT));
	    return userRepository.save(userToCreate);
	}
	
	/**
	   * Cette méthode permet de persister un membre du bureau en base de donnéees en respectant les contraintes ACID
	   * @throws AdresseMailAlreadyExistsException
	   * @throws UsernameNotAvailableException
	   * @throws MissingRequiredInformationException
	   * @throws EntityNotFoundException
	   * @throws DeniedAccessException
	   */
	@Override
	public User enregistrerBureau(String username, String password, UserDTO userDTO) 
			throws AdresseMailAlreadyExistsException, 
			UsernameNotAvailableException, 
			MissingRequiredInformationException, 
			EntityNotFoundException, 
			DeniedAccessException {
		
		if(userDTO.getUsername().isEmpty()||userDTO.getPassword().isEmpty()||userDTO.getAdresseMail().isEmpty())
			throw new MissingRequiredInformationException("Tous les champs d'information doivent être remplis");
		
		if(username.isEmpty()||password.isEmpty())
			throw new MissingRequiredInformationException("Votre identification n'est pas complète");
		
		Optional<User> userFound = securityService.autologin(username, password);
	    if(!userFound.isPresent()) {
	    	throw new EntityNotFoundException("Les informations communiquée ne permettent pas l'authentification");
	    }
	    else if(userFound.get().getRole().equals(RoleEnum.ADMIN)) {
	    	throw new DeniedAccessException("Vous n'avez pas le niveau d'autorisation requis");
	    }
	    
		Optional<User> usernameAlreadyExists = userRepository.findByUsername(userDTO.getUsername());
		if (usernameAlreadyExists.isPresent()) 
			throw new UsernameNotAvailableException("Ce nom d'adhérent est déjà utilisé"); 
		
		Optional<User> adresseMailAlreadyExists = userRepository.findByEmail(userDTO.getAdresseMail());
		if(adresseMailAlreadyExists.isPresent())
			throw new AdresseMailAlreadyExistsException("Un compte d'adhérent existe déjà pour cette adresse mail");
		
		User userToCreate = userMapper.userDTOToUser(userDTO);
		
		if (!userDTO.getPasswordConfirm().equals(userDTO.getPassword()))
			throw new MissingRequiredInformationException("Le mot de passe n'a pas été correctement confirmé");
			
	    userToCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
	    userToCreate.setPasswordConfirm(bCryptPasswordEncoder.encode(userDTO.getPasswordConfirm()));
	    userToCreate.setRole(roleRepository.findByName(RoleEnum.BUREAU));
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
		    Optional<User> userFound = securityService.autologin(username, password);
		    if(!userFound.isPresent())
		    	throw new EntityNotFoundException("Les informations communiquée ne permettent pas l'authentification");
		    return userFound.get();
	}


	@Override
	public User consulterCompteAdherent(Long id, UserDTO userDTO) throws EntityNotFoundException, DeniedAccessException {
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		Optional<User> userLogged = securityService.autologin(userDTO.getUsername(), userDTO.getPassword());
		if(!userLogged.isPresent())
			throw new EntityNotFoundException("Les informations communiquées ne sont pas correctes");
		
		if(!userFound.get().getUsername().equals(userLogged.get().getUsername()) || 
				userLogged.get().getPassword().equals(userLogged.get().getUsername()))
			throw new DeniedAccessException("Les informations communiquées ne permettent pas l'accés à la consultation de ce compte");
		
		return userFound.get();
		
	}
}
	
	

	



