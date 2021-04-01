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

import com.microselwebclientjspui.criteria.PropositionCriteria;
import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.errors.NotAuthorizedException;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.service.IPropositionService;


@Service
public class PropositionServiceImpl implements IPropositionService{
	
	@Autowired private RestTemplate restTemplate; 
	 
	@Autowired
	private HttpHeadersFactory httpHeadersFactory;
	
	@Autowired
    private HttpServletRequest request;
	
	@Value("${application.uRLProposition}") private String uRLProposition;
	
	@Value("${application.uRLPropositionUser}") private String uRLPropositionUser;
	
	@Value("${application.uRLPropositionBureau}") private String uRLPropositionBureau;
	
	@Override
	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable) throws NotAuthorizedException {
	
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

        HttpEntity<String> entity = new HttpEntity<>(headers);
		 
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLPropositionUser)
    	        .queryParam("codeEnumTradeType", propositionCriteria.getEnumTradeType())
    	        .queryParam("titre", propositionCriteria.getTitre())
    	        .queryParam("ville", propositionCriteria.getVille())
    	        .queryParam("codePostal", propositionCriteria.getCodePostal())
    	        .queryParam("nomCategorie", propositionCriteria.getEnumCategorie())
    	        .queryParam("statut", propositionCriteria.getEnumStatutProposition())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
		 System.out.println("keycloak = " + restTemplate.getUriTemplateHandler().toString()); 
		
		  ResponseEntity<RestResponsePage<Proposition>> propositions = 
				  restTemplate.exchange(
						  builder.build().toUriString(), 
						  HttpMethod.GET, 
						  entity,
						  new ParameterizedTypeReference<RestResponsePage<Proposition>>(){});
		 
        Page<Proposition> pageProposition = propositions.getBody();
 
        return pageProposition;
	}
	

	@Override
	public Proposition createProposition(PropositionDTO propositionDTO) {
		
		 HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		 HttpEntity<PropositionDTO> requestEntity = new HttpEntity<>(propositionDTO,headers);
	  
		 String url = uRLPropositionUser + "/create" ;
			
		 ResponseEntity<Proposition> response= restTemplate.exchange(url, HttpMethod.POST, requestEntity, Proposition.class);
			
		 return response.getBody();
	}


	@Override
	public Proposition searchById(long id) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * 
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 * 
		 * HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		 */
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        
		String url = uRLProposition+"/" + id;
    	
		ResponseEntity<Proposition> response = restTemplate.exchange(url , HttpMethod.GET, entity, Proposition.class);
		
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
