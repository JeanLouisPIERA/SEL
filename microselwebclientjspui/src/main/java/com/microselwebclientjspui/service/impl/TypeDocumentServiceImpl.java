package com.microselwebclientjspui.service.impl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.dto.TypeDocumentDTO;
import com.microselwebclientjspui.objets.TypeDocument;
import com.microselwebclientjspui.service.ITypeDocumentService;

@Service
public class TypeDocumentServiceImpl implements ITypeDocumentService {

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLTypeDocument}")
	private String uRLTypeDocument;

	@Value("${application.uRLTypeDocumentAdmin}")
	private String uRLTypeDocumentAdmin;

	@Override
	public Object createTypeDocument(TypeDocumentDTO typeDocumentDTO) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);
		HttpEntity<TypeDocumentDTO> requestEntity = new HttpEntity<>(typeDocumentDTO, headers);
		ResponseEntity<TypeDocument> response = restTemplate.exchange(uRLTypeDocumentAdmin, HttpMethod.POST,
				requestEntity, TypeDocument.class);

		return response.getBody();
	}

	@Override
	public Page<TypeDocument> getAll(Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = uRLTypeDocumentAdmin + "/Page";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<TypeDocument>> typeDocuments = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<TypeDocument>>() {
				});
		Page<TypeDocument> pageTypeDocument = typeDocuments.getBody();

		return pageTypeDocument;
	}

	@Override
	public List<TypeDocument> getAll() {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLTypeDocumentAdmin);

		ResponseEntity<List<TypeDocument>> typeDocuments = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<TypeDocument>>() {
				});
		List<TypeDocument> typeDocumentList = typeDocuments.getBody();

		return typeDocumentList;
	}

}
