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

import com.microselwebclientjspui.dto.TypePropositionDTO;
import com.microselwebclientjspui.objets.TypeProposition;
import com.microselwebclientjspui.service.ITypePropositionService;

@Service
public class TypePropositionServiceImpl implements ITypePropositionService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLTypeProposition}")
	private String uRLTypeProposition;

	@Override
	public TypeProposition createTypeDocument(TypePropositionDTO typePropositionDTO) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<TypePropositionDTO> requestEntity = new HttpEntity<>(typePropositionDTO, headers);
		ResponseEntity<TypeProposition> response = restTemplate.exchange(uRLTypeProposition, HttpMethod.POST,
				requestEntity, TypeProposition.class);

		return response.getBody();
	}

	@Override
	public Page<TypeProposition> getAll(Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = uRLTypeProposition + "/Page";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<TypeProposition>> typePropositions = restTemplate.exchange(
				builder.build().toUriString(), HttpMethod.GET, entity,
				new ParameterizedTypeReference<RestResponsePage<TypeProposition>>() {
				});
		Page<TypeProposition> pageTypeProposition = typePropositions.getBody();

		return pageTypeProposition;
	}

}
