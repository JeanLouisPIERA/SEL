package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.criteria.UserCriteria;
import com.microselwebclientjspui.objets.User;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpHeadersFactory httpHeadersFactory;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${application.uRLUserUser}")
	private String uRLUserUser;
	
	@Value("${application.uRLUserBureau}")
	private String uRLUserBureau;
	
	@Value("${application.uRLUserAdmin}")
	private String uRLUserAdmin;
	
	@Value("${application.microselRealm}")
	private String microselRealm;

	private IDToken getIDToken() {

		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
		IDToken iDToken = principal.getAccount().getKeycloakSecurityContext().getIdToken();
		return iDToken;

	}

	@Override
	public String identifyPrincipalId() {

		/*
		 * KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken)
		 * request.getUserPrincipal(); String userId =
		 * principal.getAccount().getKeycloakSecurityContext().getIdToken().getSubject()
		 * ; return userId;
		 */

		String userId = this.getIDToken().getSubject();
		return userId;

	}

	@Override
	public String identifyPrincipalUsername() {

		/*
		 * KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken)
		 * request.getUserPrincipal(); String userUsername =
		 * principal.getAccount().getKeycloakSecurityContext().getToken().
		 * getPreferredUsername(); return userUsername;
		 */

		String userUsername = this.getIDToken().getPreferredUsername();
		return userUsername;

	}

	@Override
	public User consulterCompteAdherent() {

		String userId = this.getIDToken().getSubject();

		String url = uRLUserUser + "/" + userId;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<User> user = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

		return user.getBody();

	}

	@Override
	public Page<User> searchByCriteria(UserCriteria userCriteria, Pageable pageable) {
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		userCriteria.setRealm("microsel-realm");
		
		  
		 

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLUserAdmin)
				.queryParam("email", userCriteria.getEmail())
				.queryParam("realm", userCriteria.getRealm())
				.queryParam("firstName", userCriteria.getFirstName())
				.queryParam("lastName", userCriteria.getLastName())
				.queryParam("role", userCriteria.getRole())
				.queryParam("username", userCriteria.getUsername())
				.queryParam("page", pageable.getPageNumber())
				.queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<User>> users = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<User>>() {
				});
		Page<User> pageUser = users.getBody();

		return pageUser;
		
	}

}
