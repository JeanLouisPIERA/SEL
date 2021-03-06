package com.microselwebclientjspui.service.impl;

import java.util.Optional;

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

import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${application.uRLTransaction}") private String uRLTransaction;

	@Override
	public Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable) {
		
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);
    	
		String url = uRLTransaction+"/wallet/" + walletId;
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
    	        .queryParam("page", pageable.getPageNumber())
    	        .queryParam("size", pageable.getPageSize());
		
		System.out.println(builder.build().toUriString());
		
    	ResponseEntity<RestResponsePage<Transaction>> transactions = restTemplate.exchange
    			(builder.build().toUriString(),  
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Transaction>>(){});
        Page<Transaction> pageTransaction = transactions.getBody();
 
        return pageTransaction;
		
	}

}
