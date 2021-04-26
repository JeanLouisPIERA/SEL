package com.microselwebclientjspui.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microselwebclientjspui.objets.Role;
import com.microselwebclientjspui.service.IRoleService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IUserService userService;

	@Value("${application.uRLRoleAdmin}")
	private String uRLRoleAdmin;

	@Value("${application.uRLRoleUser}")
	private String uRLRoleUser;

	@Override
	public List<Role> getRoles() {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<List<Role>> rolesReponse = restTemplate.exchange(uRLRoleAdmin, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Role>>() {
				});
		List<Role> roles = rolesReponse.getBody();

		return roles;
	}

	@Override
	public List<Role> getRolesByUserId() {

		String userId = userService.identifyPrincipalId();

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = uRLRoleUser + "/" + userId;

		ResponseEntity<List<Role>> rolesReponse = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Role>>() {
				});
		List<Role> roles = rolesReponse.getBody();

		return roles;

	}

	@Override
	public List<Role> getRolesByUserId(String id) {


		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = uRLRoleUser + "/" + id;

		ResponseEntity<List<Role>> rolesReponse = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<Role>>() {
				});
		List<Role> roles = rolesReponse.getBody();

		return roles;

	}

}
