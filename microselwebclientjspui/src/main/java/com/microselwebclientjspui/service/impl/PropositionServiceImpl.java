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

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.service.IPropositionService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class PropositionServiceImpl implements IPropositionService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private IUserService userService;

	@Value("${application.uRLProposition}")
	private String uRLProposition;

	@Value("${application.uRLPropositionUser}")
	private String uRLPropositionUser;

	@Value("${application.uRLPropositionBureau}")
	private String uRLPropositionBureau;

	@Value("${application.uRLPropositionAll}")
	private String uRLPropositionAll;

	@Override
	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLPropositionAll)
				.queryParam("codeEnumTradeType", propositionCriteria.getEnumTradeType())
				.queryParam("titre", propositionCriteria.getTitre()).queryParam("ville", propositionCriteria.getVille())
				.queryParam("codePostal", propositionCriteria.getCodePostal())
				.queryParam("nomCategorie", propositionCriteria.getEnumCategorie())
				.queryParam("statut", propositionCriteria.getEnumStatutProposition())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize())
				.queryParam("emetteurUsername", propositionCriteria.getEmetteurUsername());

		ResponseEntity<RestResponsePage<Proposition>> propositions = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<Proposition>>() {
				});

		Page<Proposition> pageProposition = propositions.getBody();

		return pageProposition;
	}
	
	@Override
	public Page<Proposition> searchByCriteriaByAdherent(PropositionCriteria propositionCriteria, Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		String userId = userService.identifyPrincipalId();
			
		propositionCriteria.setEmetteurId(userId);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLPropositionAll)
				.queryParam("emetteurId", propositionCriteria.getEmetteurId())
				.queryParam("codeEnumTradeType", propositionCriteria.getEnumTradeType())
				.queryParam("titre", propositionCriteria.getTitre()).queryParam("ville", propositionCriteria.getVille())
				.queryParam("codePostal", propositionCriteria.getCodePostal())
				.queryParam("nomCategorie", propositionCriteria.getEnumCategorie())
				.queryParam("statut", propositionCriteria.getEnumStatutProposition())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize())
				.queryParam("emetteurUsername", propositionCriteria.getEmetteurUsername());

		ResponseEntity<RestResponsePage<Proposition>> propositions = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<Proposition>>() {
				});

		Page<Proposition> pageProposition = propositions.getBody();

		return pageProposition;
	}

	@Override
	public Proposition createProposition(PropositionDTO propositionDTO) {

		String emetteurId = userService.identifyPrincipalId();
		propositionDTO.setEmetteurId(emetteurId);

		String emetteurUsername = userService.identifyPrincipalUsername();
		propositionDTO.setEmetteurUsername(emetteurUsername);


		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<PropositionDTO> requestEntity = new HttpEntity<>(propositionDTO, headers);

		String url = uRLPropositionUser + "/create";

		ResponseEntity<Proposition> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				Proposition.class);

		return response.getBody();
	}

	@Override
	public Proposition searchById(long id) {
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<String> entity = new HttpEntity<>(headers);

		String url = uRLPropositionUser + "/" + id;

		ResponseEntity<Proposition> response = restTemplate.exchange(url, HttpMethod.GET, entity, Proposition.class);

		return response.getBody();
	}

	@Override
	public Proposition updateProposition(Proposition proposition) {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public void closeProposition(Long id) {
		// FIXME Auto-generated method stub

	}

	

}
