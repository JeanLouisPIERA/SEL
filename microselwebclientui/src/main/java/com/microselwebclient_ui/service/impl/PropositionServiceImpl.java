package com.microselwebclient_ui.service.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.microselwebclient_ui.criteria.PropositionCriteria;
import com.microselwebclient_ui.dto.PropositionDTO;
import com.microselwebclient_ui.objets.Proposition;
import com.microselwebclient_ui.service.IPropositionService;




@Service
public class PropositionServiceImpl implements IPropositionService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @Value("${application.uRLProposition}") private String uRL;
	 */

	@Override
	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable) {
		
		
		propositionCriteria.setCodePostal(75000);
		
		
		
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://microselbourse/sel/bourse/propositions")
    	        .queryParam("codeEnumTradeType", propositionCriteria.getCodeEnumTradeType())
    	        .queryParam("titre", propositionCriteria.getTitre())
    	        .queryParam("ville", propositionCriteria.getVille())
    	        .queryParam("codePostal", propositionCriteria.getCodePostal())
    	        .queryParam("nomCategorie", propositionCriteria.getNomCategorie())
    	        .queryParam("statut", propositionCriteria.getStatut())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
		System.out.println("metierBuilder" + builder.toUriString().toString());
		
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	System.out.println("metierEntity" + entity.toString());
    	
    	ResponseEntity<RestResponsePage<Proposition>> propositions = restTemplate.exchange
    			(builder.build().toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Proposition>>(){});
        Page<Proposition> pageProposition = propositions.getBody();
        
        System.out.println("metier1" + propositions.getStatusCode());
        System.out.println("metier2" + propositions.getBody().getNumberOfElements());
        System.out.println("metier2" + propositions.getBody().getTotalPages());
        propositions.getBody().forEach(e -> System.out.println("forEach"+ e));
        
        return pageProposition;
	}

	@Override
	public Object createProposition(PropositionDTO propositionDTO) {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public Proposition searchById(long id) {
		// FIXME Auto-generated method stub
		return null;
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
