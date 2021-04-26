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
import com.microselwebclientjspui.objets.Proposition;
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
	
	@Value("${application.microselDescription}")
	private String microselDescription;
	
	@Value("${application.microselDefaultRole}")
	private String microselDefaultRole;

	private IDToken getIDToken() {

		KeycloakAuthenticationToken principal = (KeycloakAuthenticationToken) request.getUserPrincipal();
		IDToken iDToken = principal.getAccount().getKeycloakSecurityContext().getIdToken();
		return iDToken;

	}

	@Override
	public String identifyPrincipalId() {

		String userId = this.getIDToken().getSubject();
		return userId;

	}

	@Override
	public String identifyPrincipalUsername() {

		String userUsername = this.getIDToken().getPreferredUsername();
		return userUsername;

	}

	@Override
	public User consulterCompteAdherent() {

		String userId = this.getIDToken().getSubject();

		String url = uRLUserUser + "/account" ;
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("adherentMyId", userId);

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		//ResponseEntity<User> user = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);
		
		ResponseEntity<User> user = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<User>() {
				});

		return user.getBody();

	}

	@Override
	public Page<User> searchByCriteria(UserCriteria userCriteria, Pageable pageable) {
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		userCriteria.setDescription(microselDescription);
		
		userCriteria.setDefaultRole(microselDefaultRole);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLUserAdmin)
				.queryParam("email", userCriteria.getEmail())
				.queryParam("description", userCriteria.getDescription())
				.queryParam("defaultRole", userCriteria.getDefaultRole())
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

	@Override
	public User searchById(String id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String url = uRLUserAdmin + "/" + id;

		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, entity, User.class);

		return response.getBody();
	}

}
