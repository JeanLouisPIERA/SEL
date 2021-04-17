package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.microselwebclientjspui.objets.User;
import com.microselwebclientjspui.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private HttpServletRequest request;

	@Override
	public String identifyPrincipalId() {

		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
		String userId = principal.getAccount().getKeycloakSecurityContext().getIdToken().getSubject();
		return userId;

	}
	
	@Override
	public String identifyPrincipalUsername() {

		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
		String userUsername = principal.getAccount().getKeycloakSecurityContext().getToken().getPreferredUsername();
		System.out.println("usernamePrincipal = " + userUsername);
		return userUsername;

	}

	@Override
	public User consulterCompteAdherent(String id) {
		return null;
	}

	@Override
	public Page<User> consulterListeDesAdherents(Pageable pageable) {
		return null;
	}

}
