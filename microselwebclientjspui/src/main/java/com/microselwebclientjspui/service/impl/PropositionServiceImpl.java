package com.microselwebclientjspui.service.impl;

import java.time.LocalDate;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.microselwebclientjspui.objets.KeycloakClientCredentialsRestTemplate;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.security.KeycloakClientCredentialsConfig;
import com.microselwebclientjspui.service.IPropositionService;




@Service
public class PropositionServiceImpl implements IPropositionService{
	
	
	 @Autowired private RestTemplate restTemplate; 
	 
	 //@Autowired private KeycloakRestTemplate keycloakRestTemplate; 
	 
	 @Autowired private KeycloakClientCredentialsConfig keycloakClientCredentialsConfig;
	 
	@Autowired
	private HttpHeadersFactory httpHeadersFactory;
	
	@Autowired
    private HttpServletRequest request;
	
	@Value("${application.uRLProposition}") private String uRLProposition;
	
	
	
	
	@Override
	public Page<Proposition> searchByCriteria(PropositionCriteria propositionCriteria, Pageable pageable) {
	
		
		 //HttpHeaders headers = httpHeadersFactory.createHeaders(zuulu, zuulp);
		//HttpHeaders headers = new HttpHeaders();
		//HttpHeaders headers = httpHeadersFactory.createHeaders(request);
		
		//System.out.println("Request" + request.getHeader(uRLProposition));
		  
		  //headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		  //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	//headers.setContentType(MediaType.APPLICATION_JSON);
		  //HttpEntity<?> entity = new HttpEntity<>(headers);
		
		KeycloakClientCredentialsRestTemplate keycloakRestTemplate = keycloakClientCredentialsConfig.createRestTemplate();
		 
		 
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLProposition)
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
				  keycloakRestTemplate.exchange(
						  builder.build().toUriString(), 
						  HttpMethod.GET, 
						  null,
						  new ParameterizedTypeReference<RestResponsePage<Proposition>>(){});
		 
    	    	
        Page<Proposition> pageProposition = propositions.getBody();
 
        return pageProposition;
	}
	

	@Override
	public Proposition createProposition(PropositionDTO propositionDTO) {
		
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 */
		
		/*
		 * HttpHeaders headers = httpHeadersFactory.createHeaders(zuulu, zuulp);
		 * headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		 */
		  
		  
		  //headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    	//headers.setContentType(MediaType.APPLICATION_JSON);
		
		 HttpHeaders headers = httpHeadersFactory.createHeaders(request);
		 
		 System.out.println("headers1 = " + headers.toString());
		
		  //HttpEntity<?> entity = new HttpEntity<>(headers);
		  
		  HttpEntity<PropositionDTO> requestEntity = new HttpEntity<>(propositionDTO,headers);
	
		  
		  String url = uRLProposition+"/create" ;
		
		      //String url = uRLProposition ;
			
			  //HttpEntity<PropositionDTO> requestEntity = new HttpEntity<>(propositionDTO);
			  
			
		  
				/*
				 * ResponseEntity<Proposition> response =keycloakRestTemplate.postForEntity(url,
				 * propositionDTO, Proposition.class);
				 */
			 
		
			
			  ResponseEntity<Proposition> response= restTemplate.exchange(url,
			  HttpMethod.POST, requestEntity, Proposition.class);
			 
			
		  return response.getBody();
	}


	@Override
	public Proposition searchById(long id) {
		HttpHeaders headers = new HttpHeaders();
		
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		
		String url = uRLProposition+"/" + id;
    	
		ResponseEntity<Proposition> response = restTemplate.exchange(url , HttpMethod.GET, requestEntity, Proposition.class);
		
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
