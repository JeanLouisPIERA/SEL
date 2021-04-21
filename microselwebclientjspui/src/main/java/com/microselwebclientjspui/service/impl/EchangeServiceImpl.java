package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.criteria.EchangeCriteria;
import com.microselwebclientjspui.objets.Echange;
import com.microselwebclientjspui.service.IEchangeService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class EchangeServiceImpl implements IEchangeService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IUserService userService;

	@Value("${application.uRLEchange}")
	private String uRLEchange;

	@Value("${application.uRLEchangeUser}")
	private String uRLEchangeUser;

	@Value("${application.uRLEchangeBureau}")
	private String uRLEchangeBureau;

	@Value("${application.uRLEchangeAll}")
	private String uRLEchangeAll;

	
	@Override
	public Page<Echange> searchByCriteria(EchangeCriteria echangeCriteria, Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLEchangeAll)
				.queryParam("id", echangeCriteria.getId()).queryParam("titre", echangeCriteria.getTitre())
				.queryParam("statutEchange", echangeCriteria.getStatutEchange())
				.queryParam("emetteurUsername", echangeCriteria.getEmetteurUsername())
				.queryParam("recepteurUsername", echangeCriteria.getRecepteurUsername())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Echange>> echanges = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Echange>>() {
				});

		Page<Echange> pageEchange = echanges.getBody();

		return pageEchange;
	}
	
	@Override
	public Page<Echange> searchByCriteriaByAdherentRecepteur(EchangeCriteria echangeCriteria, Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String userId = userService.identifyPrincipalId();
		echangeCriteria.setRecepteurId(userId);
		
		String userUsername = userService.identifyPrincipalUsername();
		echangeCriteria.setRecepteurUsername(userUsername);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLEchangeAll)
				.queryParam("id", echangeCriteria.getId())
				.queryParam("emetteurId", echangeCriteria.getEmetteurId())
				.queryParam("recepteurId", echangeCriteria.getRecepteurId())
				.queryParam("titre", echangeCriteria.getTitre())
				.queryParam("statutEchange", echangeCriteria.getStatutEchange())
				.queryParam("emetteurUsername", echangeCriteria.getEmetteurUsername())
				.queryParam("recepteurUsername", echangeCriteria.getRecepteurUsername())
				.queryParam("page", pageable.getPageNumber())
				.queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Echange>> echanges = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Echange>>() {
				});

		Page<Echange> pageEchange = echanges.getBody();

		return pageEchange;
	}
	
	@Override
	public Page<Echange> searchByCriteriaByAdherentEmetteur(EchangeCriteria echangeCriteria, Pageable pageable) {
		
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<?> entity = new HttpEntity<>(headers);
		
		String userId = userService.identifyPrincipalId();
		echangeCriteria.setEmetteurId(userId);
		
		String userUsername = userService.identifyPrincipalUsername();
		echangeCriteria.setEmetteurUsername(userUsername);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLEchangeAll)
				.queryParam("id", echangeCriteria.getId())
				.queryParam("emetteurId", echangeCriteria.getEmetteurId())
				.queryParam("recepteurId", echangeCriteria.getRecepteurId())
				.queryParam("titre", echangeCriteria.getTitre())
				.queryParam("statutEchange", echangeCriteria.getStatutEchange())
				.queryParam("emetteurUsername", echangeCriteria.getEmetteurUsername())
				.queryParam("recepteurUsername", echangeCriteria.getRecepteurUsername())
				.queryParam("page", pageable.getPageNumber())
				.queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Echange>> echanges = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Echange>>() {
				});

		Page<Echange> pageEchange = echanges.getBody();

		return pageEchange;
	}

	@Override
	public Echange searchById(Long id) {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String url = uRLEchangeUser + "/" + id;

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.GET, entity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange confirmerEchange(Long id) {
		String intervenantId = userService.identifyPrincipalId();

		String url = uRLEchangeUser + "/confirmer/" + id;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<String> requestEntity = new HttpEntity<>(intervenantId, headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange annulerEchange(Long id) {
		
		String intervenantId = userService.identifyPrincipalId();

		String url = uRLEchangeUser + "/annuler/" + id;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(intervenantId, headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange emetteurValiderEchange(Long id) {
		
		String url = uRLEchangeUser + "/emetteurValider/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange recepteurRefuserEchange(Long id) {
		
		String url = uRLEchangeUser + "/recepteurRefuser/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange emetteurRefuserEchange(Long id) {
		
		String url = uRLEchangeUser + "/emetteurRefuser/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange recepteurValiderEchange(Long id) {
		
		String url = uRLEchangeUser + "/recepteurValider/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
	}

	@Override
	public Echange validerEchange(Long id) {
		String intervenantId = userService.identifyPrincipalId();
		
		String url = uRLEchangeUser + "/valider/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(intervenantId, headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
		
	}

	@Override
	public Echange refuserEchange(Long id) {
		String intervenantId = userService.identifyPrincipalId();
		
		String url = uRLEchangeUser + "/refuser/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(intervenantId, headers);

		ResponseEntity<Echange> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Echange.class);

		return response.getBody();
		
	}

	

	
}
