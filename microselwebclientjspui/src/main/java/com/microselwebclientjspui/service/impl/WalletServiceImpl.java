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

import com.microselwebclientjspui.criteria.WalletCriteria;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.IWalletService;

@Service
public class WalletServiceImpl implements IWalletService {
	
	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLWallet}")
	private String uRLWallet;
	
	@Value("${application.uRLWalletBureau}")
	private String uRLWalletBureau;

	@Override
	public Wallet searchByAdherentId(String adherentId) {
		
		String url = uRLWalletBureau + "/" + adherentId;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Wallet> wallet = restTemplate.exchange(url, HttpMethod.GET, entity, Wallet.class);

		return wallet.getBody();
	}

	@Override
	public Page<Wallet> searchByCriteria(WalletCriteria walletCriteria, Pageable pageable) {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLWalletBureau)
				.queryParam("id", walletCriteria.getId())
				.queryParam("titulaireId", walletCriteria.getTitulaireId())
				.queryParam("titulaireUsername", walletCriteria.getTitulaireUsername())
				.queryParam("soldeWallet", walletCriteria.getSoldeWallet()).queryParam("page", pageable.getPageNumber())
				.queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Wallet>> wallets = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Wallet>>() {
				});
		Page<Wallet> pageWallet = wallets.getBody();

		return pageWallet;
	}

	@Override
	public Wallet searchById(Long id) {
		
		String url = uRLWalletBureau + "/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<Wallet> wallet = restTemplate.exchange(url, HttpMethod.GET, entity, Wallet.class);

		return wallet.getBody();
	}

}
