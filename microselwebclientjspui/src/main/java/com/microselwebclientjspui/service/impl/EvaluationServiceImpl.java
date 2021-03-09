package com.microselwebclientjspui.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.objets.EvaluationList;
import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.service.IEvaluationService;

@Service
public class EvaluationServiceImpl implements IEvaluationService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLEvaluation}") private String uRLEvaluation;


	@Override
	public Evaluation createEvaluation(Long echangeId, EvaluationDTO evaluationDTO) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	String url = uRLEvaluation+"/echange/" + echangeId;

    	System.out.println("urlEvaluation" + url);
    	HttpEntity<EvaluationDTO> requestEntity = new HttpEntity<>(evaluationDTO, headers);
    	ResponseEntity<Evaluation> response =restTemplate.exchange(url, HttpMethod.POST, requestEntity, 
			              Evaluation.class);
			
		  return response.getBody();
	}


	@Override
	public List<Evaluation> findAllByEchangeId(Long id) {
		
		/*
		 * String url = uRLEvaluation+"/echange/" + id;
		 * 
		 * System.out.println("URLEvaluation" + url);
		 * 
		 * EvaluationList response = restTemplate.getForObject(url ,
		 * EvaluationList.class);
		 * 
		 * System.out.println("response" + response);
		 * 
		 * List<Evaluation> evaluations = response.getEvaluations();
		 * 
		 * return evaluations;
		 */
		
		
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
    	String url = uRLEvaluation+"/echange/" + id;
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
    	ResponseEntity<List<Evaluation>> evaluations = restTemplate.exchange
    			(builder.build().toUriString(),  
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<List<Evaluation>>(){});
        List<Evaluation> evaluationsList = evaluations.getBody();
 
        return evaluationsList;

		
	}
	
	

}
