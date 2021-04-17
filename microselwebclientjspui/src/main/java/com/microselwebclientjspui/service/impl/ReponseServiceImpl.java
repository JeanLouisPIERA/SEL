package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.service.IReponseService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class ReponseServiceImpl implements IReponseService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private IUserService userService;

	@Value("${application.uRLReponseBureau}")
	private String uRLReponseBureau;

	@Value("${application.uRLReponseUser}")
	private String uRLReponseUser;

	@Value("${application.uRLReponse}")
	private String uRLReponse;

	@Override
	public Reponse createReponse(Long id, ReponseDTO reponseDTO) {

		String recepteurId = userService.identifyPrincipalId();
		reponseDTO.setRecepteurId(recepteurId);

		String recepteurUsername = userService.identifyPrincipalUsername();
		reponseDTO.setRecepteurUsername(recepteurUsername);

		String url = uRLReponseUser + "/" + id;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<ReponseDTO> requestEntity = new HttpEntity<>(reponseDTO, headers);

		ResponseEntity<Reponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Reponse.class);

		return response.getBody();
	}

	@Override
	public Page<Reponse> findAllByPropositionId(Long id, Pageable pageable) {

		String url = uRLReponseUser + "/proposition/" + id;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Reponse>> reponses = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Reponse>>() {
				});
		Page<Reponse> pageReponse = reponses.getBody();

		return pageReponse;

	}

}
