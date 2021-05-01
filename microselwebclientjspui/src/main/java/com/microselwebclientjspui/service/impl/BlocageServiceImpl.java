package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

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

import com.microselwebclientjspui.criteria.BlocageCriteria;
import com.microselwebclientjspui.objets.Blocage;
import com.microselwebclientjspui.objets.EnumStatutBlocage;
import com.microselwebclientjspui.service.IBlocageService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class BlocageServiceImpl implements IBlocageService {

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IUserService userService;

	@Value("${application.uRLBlocageAdmin}")
	private String uRLBlocageAdmin;

	Long id;
	String adherentId;
	String adherentUsername;
	EnumStatutBlocage statutBlocage;

	@Override
	public Page<Blocage> searchByCriteria(BlocageCriteria blocageCriteria, Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLBlocageAdmin)
				.queryParam("id", blocageCriteria.getId()).queryParam("adherentId", blocageCriteria.getAdherentId())
				.queryParam("adherentUsername", blocageCriteria.getAdherentUsername())
				.queryParam("statutBlocage", blocageCriteria.getStatutBlocage())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Blocage>> blocages = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Blocage>>() {
				});

		Page<Blocage> pageBlocage = blocages.getBody();

		return pageBlocage;
	}

	@Override
	public Blocage closeBlocage(Long id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLBlocageAdmin + "/close/" + id;

		ResponseEntity<Blocage> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Blocage.class);

		return response.getBody();
	}

}
