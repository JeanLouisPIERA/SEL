package com.microselwebclientjspui.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.microselwebclientjspui.criteria.ArticleCriteria;
import com.microselwebclientjspui.dto.ArticleDTO;
import com.microselwebclientjspui.objets.Article;
import com.microselwebclientjspui.objets.EnumStatutDocument;
import com.microselwebclientjspui.service.IArticleService;
import com.microselwebclientjspui.service.IUserService;

@Service
public class ArticleServiceImpl implements IArticleService {

	@Autowired
	private HttpHeadersFactory httpHeadersFactory;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private IUserService userService;

	@Value("${application.uRLArticle}")
	private String uRLArticle;

	@Value("${application.uRLArticleAdmin}")
	private String uRLArticleAdmin;

	@Override
	public Object createArticle(ArticleDTO articleDTO) {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		String emetteurId;
		try {
			emetteurId = userService.identifyPrincipalId();
		} catch (Exception e) {
			articleDTO.setAuteurUsername("visiteur");

			HttpEntity<ArticleDTO> requestEntity = new HttpEntity<>(articleDTO, headers);
			ResponseEntity<Article> response = restTemplate.exchange(uRLArticle, HttpMethod.POST, requestEntity,
					Article.class);

			return response.getBody();
		}

		String emetteurUsername = userService.identifyPrincipalUsername();

		articleDTO.setAuteurId(emetteurId);
		articleDTO.setAuteurUsername(emetteurUsername);

		HttpEntity<ArticleDTO> requestEntity = new HttpEntity<>(articleDTO, headers);
		ResponseEntity<Article> response = restTemplate.exchange(uRLArticle, HttpMethod.POST, requestEntity,
				Article.class);

		return response.getBody();

	}

	@Override
	public Page<Article> searchByCriteria(ArticleCriteria articleCriteria, Pageable pageable) {
		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<?> entity = new HttpEntity<>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uRLArticle)
				.queryParam("statutDocument", articleCriteria.getStatutDocument())
				.queryParam("typeArticle", articleCriteria.getTypeArticle())
				.queryParam("page", pageable.getPageNumber()).queryParam("size", pageable.getPageSize());

		ResponseEntity<RestResponsePage<Article>> articles = restTemplate.exchange(builder.build().toUriString(),
				HttpMethod.GET, entity, new ParameterizedTypeReference<RestResponsePage<Article>>() {
				});
		Page<Article> pageArticle = articles.getBody();

		return pageArticle;
	}

	@Override
	public Article searchById(Long id) {

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLArticle + "/" + id;

		ResponseEntity<Article> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Article.class);

		return response.getBody();

	}

	@Override
	public Article publierById(Long id) {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLArticleAdmin + "/publication/" + id;

		ResponseEntity<Article> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Article.class);

		return response.getBody();

	}

	@Override
	public Article modererById(Long id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLArticleAdmin + "/moderation/" + id;

		ResponseEntity<Article> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Article.class);

		return response.getBody();
	}

	@Override
	public Article archiverById(Long id) {
		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		HttpEntity<?> requestEntity = new HttpEntity<>(headers);

		String url = uRLArticleAdmin + "/archivage/" + id;

		ResponseEntity<Article> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Article.class);

		return response.getBody();
	}

	@Override
	public List<Article> select4ArticlesToBePublished() {

		List<Article> articleToBePublishedList = new ArrayList();

		ArticleCriteria articleCriteria = new ArticleCriteria();
		articleCriteria.setStatutDocument(EnumStatutDocument.PUBLIE.getCode());
		Page<Article> articlePublishablePage = this.searchByCriteria(articleCriteria, PageRequest.of(0, 10));

		Article articleByDefault = this.searchById((long) 1);

		Integer number = articlePublishablePage.getContent().size();

		if (number == 4) {

			for (int nombre = 1; nombre <= number; nombre++) {
				Article articleToBePublished = articlePublishablePage.getContent().get(number - nombre);
				articleToBePublishedList.add(articleToBePublished);
			}

		} else if (number == 3) {

			for (int nombre = 1; nombre <= number; nombre++) {
				Article articleToBePublished = articlePublishablePage.getContent().get(number - nombre);
				articleToBePublishedList.add(articleToBePublished);
			}
			articleToBePublishedList.add(articleByDefault);

		} else if (number == 2) {

			for (int nombre = 1; nombre <= number; nombre++) {
				Article articleToBePublished = articlePublishablePage.getContent().get(number - nombre);
				articleToBePublishedList.add(articleToBePublished);
			}
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);

		} else if (number == 1) {

			for (int nombre = 1; nombre <= number; nombre++) {
				Article articleToBePublished = articlePublishablePage.getContent().get(number - nombre);
				articleToBePublishedList.add(articleToBePublished);
			}
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);

		} else if (number == 0) {

			for (int nombre = 1; nombre <= number; nombre++) {
				Article articleToBePublished = articlePublishablePage.getContent().get(number - nombre);
				articleToBePublishedList.add(articleToBePublished);
			}
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);
			articleToBePublishedList.add(articleByDefault);

		}

		return articleToBePublishedList;
	}

	@Override
	public Article modifierArticleStandard(ArticleDTO articleDTO) {

		HttpHeaders headers = httpHeadersFactory.createHeaders(request);

		String emetteurId = userService.identifyPrincipalUsername();
		articleDTO.setAuteurId(emetteurId);

		String emetteurUsername = userService.identifyPrincipalUsername();
		articleDTO.setAuteurUsername(emetteurUsername);

		HttpEntity<ArticleDTO> requestEntity = new HttpEntity<>(articleDTO, headers);

		String url = uRLArticleAdmin + "/standard";

		ResponseEntity<Article> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Article.class);

		return response.getBody();

	}

}
