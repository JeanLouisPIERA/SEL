package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.criteria.UserCriteria;
import com.microselwebclientjspui.objets.User;

public interface IUserService {
	
	String identifyPrincipalId();
	
	String identifyPrincipalUsername();
	
	User consulterCompteAdherent();

	Page<User> searchByCriteria(UserCriteria userCriteria, Pageable pageable);

	User searchById(String id);

	
}
