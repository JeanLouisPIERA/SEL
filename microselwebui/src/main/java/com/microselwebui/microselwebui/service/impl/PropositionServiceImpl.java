package com.microselwebui.microselwebui.service.impl;

import java.time.LocalDate;

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

import com.microselwebui.microselwebui.beans.RestResponsePage;
import com.microselwebui.microselwebui.criteria.PropositionCriteria;
import com.microselwebui.microselwebui.dto.PropositionDTO;
import com.microselwebui.microselwebui.objets.Proposition;
import com.microselwebui.microselwebui.service.IPropositionService;


@Service
public class PropositionServiceImpl implements IPropositionService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	/*
	 * @Value("${application.uRLProposition}") private String uRL;
	 */

	@Override
	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable) {
		
		
		
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:9002/sel/bourse/propositions")
    	        .queryParam("codeEnumTradeType", propositionCriteria.getCodeEnumTradeType())
    	        .queryParam("titre", propositionCriteria.getTitre())
    	        .queryParam("ville", propositionCriteria.getVille())
    	        .queryParam("codePostal", propositionCriteria.getCodePostal())
				  .queryParam("dateDebut", propositionCriteria.getDateDebut())
				  .queryParam("dateFin", propositionCriteria.getDateFin())
				  .queryParam("dateEcheance", propositionCriteria.getDateEcheance())
    	        .queryParam("nomCategorie", propositionCriteria.getNomCategorie())
    	        .queryParam("statut", propositionCriteria.getStatut())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	ResponseEntity<RestResponsePage<Proposition>> propositions = restTemplate.exchange
    			(builder.toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Proposition>>(){});
        Page<Proposition> pageProposition = propositions.getBody();
        System.out.println("metier1" + propositions.getStatusCode());
        System.out.println("metier2" + propositions.getBody().getNumberOfElements());
        System.out.println("metier2" + propositions.getBody().getTotalPages());
        
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
