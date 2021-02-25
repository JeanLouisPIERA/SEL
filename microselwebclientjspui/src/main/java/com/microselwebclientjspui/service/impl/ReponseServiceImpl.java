package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microselwebclientjspui.dto.PropositionDTO;
import com.microselwebclientjspui.dto.ReponseDTO;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Reponse;
import com.microselwebclientjspui.service.IReponseService;



@Service
public class ReponseServiceImpl implements IReponseService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLReponse}") private String uRLReponse;

	@Override
	public Reponse createReponse(Long id, ReponseDTO reponseDTO) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	String url = uRLReponse + "/" + id;

    	HttpEntity<ReponseDTO> requestEntity = new HttpEntity<>(reponseDTO, headers);
    	ResponseEntity<Reponse> response =restTemplate.exchange(url, HttpMethod.POST, requestEntity, 
			              Reponse.class);
			
		  return response.getBody();
	}

	

}
