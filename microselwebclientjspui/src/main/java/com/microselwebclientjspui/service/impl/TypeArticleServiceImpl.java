package com.microselwebclientjspui.service.impl;

import java.util.Arrays;
import java.util.List;

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

import com.microselwebclientjspui.dto.TypeArticleDTO;
import com.microselwebclientjspui.dto.TypeDocumentDTO;
import com.microselwebclientjspui.objets.TypeArticle;
import com.microselwebclientjspui.objets.TypeDocument;
import com.microselwebclientjspui.service.ITypeArticleService;
import com.microselwebclientjspui.service.ITypeDocumentService;

@Service
public class TypeArticleServiceImpl implements ITypeArticleService {

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;


	@Value("${application.uRLTypeArticle}")
	private String uRLTypeArticle;
	
	@Value("${application.uRLTypeArticleAdmin}")
	private String uRLTypeArticleAdmin;


	@Override
	public Object createTypeArticle(TypeArticleDTO typeArticleDTO) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);
		HttpEntity<TypeArticleDTO> requestEntity = new HttpEntity<>(typeArticleDTO, headers);
		ResponseEntity<TypeArticle> response = restTemplate.exchange(uRLTypeArticleAdmin, HttpMethod.POST, requestEntity,
				TypeArticle.class);

		return response.getBody();
	}

	@Override
	public Page<TypeArticle> getAll(Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = uRLTypeArticleAdmin + "/Page";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<TypeArticle>> typeArticles = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<TypeArticle>>() {
				});
		Page<TypeArticle> pageTypeArticle = typeArticles.getBody();

		return pageTypeArticle;
	}

	@Override
	public List<TypeArticle> getAll() {

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLTypeArticle);

		ResponseEntity<List<TypeArticle>> typeArticles = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<TypeArticle>>() {
				});
		List<TypeArticle> typeArticleList = typeArticles.getBody();

		return typeArticleList;
	}

}
