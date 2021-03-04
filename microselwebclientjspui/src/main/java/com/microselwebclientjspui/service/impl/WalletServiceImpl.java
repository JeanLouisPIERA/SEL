package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.criteria.WalletCriteria;
import com.microselwebclientjspui.objets.Proposition;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IWalletService;

@Service
public class WalletServiceImpl implements IWalletService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${application.uRLWallet}") private String uRLWallet;

	@Override
	public Wallet searchByAdherentId(Long adherentId) {
		HttpHeaders headers = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		
		String url = uRLWallet+"/" + adherentId;
    	
		ResponseEntity<Wallet> wallet = restTemplate.exchange(url , HttpMethod.GET, requestEntity, Wallet.class);
		
		return wallet.getBody(); 
	}

	@Override
	public Page<Wallet> searchByCriteria(WalletCriteria walletCriteria, PageRequest of) {
		
		HttpHeaders headers = new HttpHeaders();
    	headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    	
    	HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLWallet)
    	        .queryParam("id", walletCriteria.getId())
    	        .queryParam("titulaireId", walletCriteria.getTitulaireId())
    	        .queryParam("solde", walletCriteria.getSoldeWallet());
    
    	ResponseEntity<RestResponsePage<Wallet>> wallets = restTemplate.exchange
    			(builder.build().toUriString(), 
				HttpMethod.GET,
				entity,
    			new ParameterizedTypeReference<RestResponsePage<Wallet>>(){});
        Page<Wallet> pageWallet = wallets.getBody();
 
        return pageWallet;
	}
	
	

}
