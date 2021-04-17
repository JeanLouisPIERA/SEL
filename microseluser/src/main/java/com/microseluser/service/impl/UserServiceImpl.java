package com.microseluser.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microseluser.dao.IUserRepository;
import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;
import com.microseluser.service.IUserService;


@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * Cette méthode permet à un adhérent de consulter son compte
	 * Seul un adhérent peut consulter les données de son compte 
	 * RGPD COMPLIANT
	 * @throws EntityNotFoundException 
	 */
	@Override
	public User readAccount(String id) throws EntityNotFoundException {
		
		Optional<User> userFound = userRepository.findById(id);
		if(!userFound.isPresent())
			throw new EntityNotFoundException("Ce compte n'existe pas");
		
		return userFound.get();
		
	}
	
	/**
     * Cette méthode permet à un membre du bureau d'obtenir la liste de tous les utilisateurs
	  * Seul un membre du bureau peut consulter cette liste
	 */	
	  @Override public List<User> showAllUsers() {
	  
	  List<User> listUsers = userRepository.findAll();
	   
		/*
		 * for (User user : listUsers) { user.setPassword(null);
		 * user.setPasswordConfirm(null); }
		 */
	  
	  return listUsers; }

}
