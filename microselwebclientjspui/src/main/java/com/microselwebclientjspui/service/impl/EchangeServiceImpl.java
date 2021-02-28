package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

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

import com.microselwebclientjspui.criteria.EchangeCriteria;
import com.microselwebclientjspui.objets.Echange;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.service.IEchangeService;

@Service
public class EchangeServiceImpl implements IEchangeService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${application.uRLEchange}") private String uRLEchange;

	@Override
	public Page<Echange> searchByCriteria(EchangeCriteria echangeCriteria, Pageable pageable) {

		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
 
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLEchange)
    	        .queryParam("id", echangeCriteria.getId())
    	        .queryParam("titre", echangeCriteria.getTitre())
    	        .queryParam("statutEchange", echangeCriteria.getStatutEchange())
    	        .queryParam("emetteurUsername", echangeCriteria.getEmetteurUsername())
    	        .queryParam("recepteurUsername", echangeCriteria.getRecepteurUsername())
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
    	
		System.out.println("metierBuilder" + builder.toUriString().toString());
    	        
    	ResponseEntity<RestResponsePage<Echange>> echanges = restTemplate.exchange
    			(builder.build().toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Echange>>(){});
    	
        Page<Echange> pageEchange= echanges.getBody();
         
        return pageEchange;
	}

	@Override
	public Echange searchById(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		
		String url = uRLEchange+"/" + id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.GET, requestEntity, Echange.class);
		
		return response.getBody();
	}


	@Override
	public Echange confirmerEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/confirmer/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody(); 
	}

	
	@Override
	public Echange annulerEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/annuler/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody();
	}

	@Override
	public Echange emetteurValiderEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/emetteurValider/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody();
	}

	@Override
	public Echange recepteurRefuserEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/recepteurRefuser/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody();
	}

	@Override
	public Echange emetteurRefuserEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/emetteurRefuser/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody();
	}

	@Override
	public Echange recepteurValiderEchange(Long id) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = 
       	     new HttpEntity<>(headers);
		
		String url = uRLEchange+"/recepteurValider/"+ id;
    	
		ResponseEntity<Echange> response = restTemplate.exchange(url , HttpMethod.PUT, requestEntity, Echange.class);
		
		return response.getBody();
	}
}


