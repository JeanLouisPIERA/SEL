package com.microseluser.service;

import java.util.List;

import com.microseluser.entities.User;
import com.microseluser.exceptions.EntityNotFoundException;

public interface IUserService {

	/**
	 * Cette méthode permet à un adhérent de consulter son compte Seul un adhérent
	 * peut consulter les données de son compte RGPD COMPLIANT
	 * 
	 * @throws EntityNotFoundException
	 */
	User readAccount(String id) throws EntityNotFoundException;

	/**
	 * Cette méthode permet à un membre du bureau d'obtenir la liste de tous les
	 * utilisateurs Seul un membre du bureau peut consulter cette liste
	 */
	List<User> showAllUsers();

}
