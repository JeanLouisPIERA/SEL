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

import com.microselwebclientjspui.criteria.DocumentCriteria;
import com.microselwebclientjspui.dto.DocumentDTO;
import com.microselwebclientjspui.objets.Document;
import com.microselwebclientjspui.service.IDocumentService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private IUserService userService;


	@Value("${application.uRLDocument}")
	private String uRLDocument;
	
	@Value("${application.uRLDocumentAdmin}")
	private String uRLDocumentAdmin;

	@Override
	public Object createDocument(DocumentDTO documentDTO) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);
		
		String emetteurId = userService.identifyPrincipalId();
		documentDTO.setAuteurId(emetteurId);
		
		String emetteurUsername = userService.identifyPrincipalUsername();
		documentDTO.setAuteurUsername(emetteurUsername);
		
		HttpEntity<DocumentDTO> requestEntity = new HttpEntity<>(documentDTO, headers);
		ResponseEntity<Document> response = restTemplate.exchange(uRLDocumentAdmin, HttpMethod.POST, requestEntity,
				Document.class);

		return response.getBody();
	}

	@Override
	public Page<Document> searchByCriteria(DocumentCriteria documentCriteria, Pageable pageable) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLDocumentAdmin)
				.queryParam("statutDocument", documentCriteria.getStatutDocument())
				.queryParam("typeDocument", documentCriteria.getTypeDocument())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Document>> documents = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Document>>() {
				});
		Page<Document> pageDocument = documents.getBody();

		return pageDocument;
	}


	@Override
	public Document searchById(Long id) {
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLDocumentAdmin + "/" + id;

		ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Document.class);

		return response.getBody();
		
	}

	@Override
	public Document publierById(Long id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLDocumentAdmin + "/publication/" + id;

		ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Document.class);

		return response.getBody();
	}

	@Override
	public Document archiverById(Long id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLDocumentAdmin + "/archivage/" + id;

		ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Document.class);

		return response.getBody();
	}

	@Override
	public Document searchByTypeDocumentId(Long typedocumentId) {
		
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		
		String url = uRLDocument + "/" + typedocumentId;
		
		System.out.println("uri" + url);

		ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Document.class);

		return response.getBody();
	}

	

}
