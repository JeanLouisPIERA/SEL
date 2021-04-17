package com.microselwebclientjspui.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.microselwebclientjspui.objets.User;

public interface IUserService {
	
	String identifyPrincipalId();
	
	String identifyPrincipalUsername();
	
	User consulterCompteAdherent(String id);
	
	Page<User> consulterListeDesAdherents(Pageable pageable);

}
