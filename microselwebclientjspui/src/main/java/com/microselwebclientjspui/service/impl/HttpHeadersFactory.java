package com.microselwebclientjspui.service.impl;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HttpHeadersFactory {
	
	
	
	/*
	 * public HttpHeaders createHeaders(String username, String password){ return
	 * new HttpHeaders() {{ String auth = username + ":" + password; byte[]
	 * encodedAuth = Base64.encodeBase64( auth.getBytes(Charset.forName("US-ASCII"))
	 * ); String authHeader = "Basic " + new String( encodedAuth ); set(
	 * "Authorization", authHeader ); }};
	 * 
	 * }
	 */
	 
	
	public HttpHeaders createHeaders(HttpServletRequest request) {
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal(); 
		KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
		KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer "+session.getTokenString());
		
		System.out.println("headerFactoryToken =" + session.getTokenString());
		System.out.println("headerFactory =" + httpHeaders.toString());
		
		return httpHeaders;
		
		
	}

}
