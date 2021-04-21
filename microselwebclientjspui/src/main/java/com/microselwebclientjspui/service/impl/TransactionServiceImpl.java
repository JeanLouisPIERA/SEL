package com.microselwebclientjspui.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.objets.Transaction;
import com.microselwebclientjspui.objets.Wallet;
import com.microselwebclientjspui.service.ITransactionService;
import com.microselwebclientjspui.service.IUserService;
import com.microselwebclientjspui.service.IWalletService;

@Service
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IWalletService walletService;

	@Value("${application.uRLTransaction}")
	private String uRLTransaction;

	@Value("${application.uRLTransactionBureau}")
	private String uRLTransactionBureau;
	
	@Value("${application.uRLTransactionUser}")
	private String uRLTransactionUser;

	@Override
	public Page<Transaction> findAllByWalletId(Long walletId, Pageable pageable) {

		String url = uRLTransactionBureau + "/wallet/" + walletId;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Transaction>> transactions = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<Transaction>>() {
				});
		Page<Transaction> pageTransaction = transactions.getBody();

		return pageTransaction;

	}
	
	@Override
	public Page<Transaction> findAllByWalletIdByAdherent(Long walletId, Pageable pageable)  {

		Wallet wallet = walletService.searchById(walletId);
		
		String userId = userService.identifyPrincipalId();
		
		String url = uRLTransactionUser + "/wallet/" + walletId;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Transaction>> transactions = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<Transaction>>() {
				});
		Page<Transaction> pageTransaction = transactions.getBody();

		return pageTransaction;

	}

}
