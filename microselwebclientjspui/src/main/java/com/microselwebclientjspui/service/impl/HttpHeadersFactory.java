package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.http.HttpHeaders;
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
			
		HttpHeaders headers = new HttpHeaders(); headers.set("Authorization", "Bearer " + session.getTokenString());

		return headers;
		
	}

}
