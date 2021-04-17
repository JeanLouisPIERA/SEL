package com.microselwebclientjspui.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.dto.EvaluationDTO;
import com.microselwebclientjspui.objets.Evaluation;
import com.microselwebclientjspui.service.IEvaluationService;

@Service
public class EvaluationServiceImpl implements IEvaluationService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${application.uRLEvaluation}")
	private String uRLEvaluation;

	@Value("${application.uRLEvaluationUser}")
	private String uRLEvaluationUser;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Override
	public Evaluation createEvaluation(Long echangeId, EvaluationDTO evaluationDTO) {

		String url = uRLEvaluationUser + "/echange/" + echangeId;

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<EvaluationDTO> requestEntity = new HttpEntity<>(evaluationDTO, headers);
		
		ResponseEntity<Evaluation> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				Evaluation.class);

		return response.getBody();
	}

	@Override
	public List<Evaluation> findAllByEchangeId(Long id) {

		String url = uRLEvaluationUser + "/echange/" + id;
		
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<EvaluationDTO> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseEntity<List<Evaluation>> evaluations = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<Evaluation>>() {
				});
		List<Evaluation> evaluationsList = evaluations.getBody();

		return evaluationsList;

	}

}
