package com.microselwebclientjspui.service.impl;

import java.util.Arrays;

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

@Service
public class DocumentServiceImpl implements IDocumentService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLDocument}")
	private String uRLDocument;

	@Override
	public Object createDocument(DocumentDTO documentDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<DocumentDTO> requestEntity = new HttpEntity<>(documentDTO, headers);
		ResponseEntity<Document> response = restTemplate.exchange(uRLDocument, HttpMethod.POST, requestEntity,
				Document.class);

		return response.getBody();
	}

	@Override
	public Page<Document> searchByCriteria(DocumentCriteria documentCriteria, Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLDocument)
				.queryParam("auteurId", documentCriteria.getAuteurId())
				.queryParam("auteurUsername", documentCriteria.getAuteurUsername())
				.queryParam("dateCreation", documentCriteria.getDateCreation())
				.queryParam("dateLastUpdate", documentCriteria.getDateLastUpdate())
				.queryParam("enumStatutProposition", documentCriteria.getEnumStatutDocument())
				.queryParam("nomTypeDocument", documentCriteria.getNomTypeDocument())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Document>> documents = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Document>>() {
				});
		Page<Document> pageDocument = documents.getBody();

		return pageDocument;
	}

	@Override
	public Document searchById(Long id) {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLDocument + "/" + id;

		ResponseEntity<Document> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Document.class);

		return response.getBody();
	}

}
