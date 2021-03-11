package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

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

import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.service.IReponseService;



@Service
public class ReponseServiceImpl implements IReponseService {
	
	
	  @Autowired private RestTemplate restTemplate;
	  
	  @Autowired
		private HttpHeadersFactory httpHeadersFactory;
	
	@Value("${application.uRLReponse}") private String uRLReponse;

	@Override
	public Reponse createReponse(Long id, HttpServletRequest request, ReponseDTO reponseDTO) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		 * headers.setContentType(MediaType.APPLICATION_JSON);
		 */
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);
    	
    	String url = uRLReponse + "/" + id;

    	HttpEntity<ReponseDTO> requestEntity = new HttpEntity<>(reponseDTO, headers);
    	ResponseEntity<Reponse> response =restTemplate.exchange(url, HttpMethod.POST, requestEntity, 
			              Reponse.class);
			
		  return response.getBody();
	}

	@Override
	public Page<Reponse> findAllByPropositionId(Long id, Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
		String url = uRLReponse+"/proposition/" + id;
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
		
    	ResponseEntity<RestResponsePage<Reponse>> reponses = restTemplate.exchange
    			(builder.build().toUriString(),  
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Reponse>>(){});
        Page<Reponse> pageReponse = reponses.getBody();
 
        return pageReponse;
		
	}

	

}
