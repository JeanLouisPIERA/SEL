package com.microselreferentiels.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselreferentiels.beans.UserBean;
import com.microselreferentiels.dao.IArticleRepository;
import com.microselreferentiels.dao.ITypeArticleRepository;
import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.mapper.IArticleMapper;
import com.microselreferentiels.proxies.IMicroselAdherentsProxy;



@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceImplTest {
	
	@Mock
	private IArticleRepository articleRepository;
	
	@Mock
	private ITypeArticleRepository typeArticleRepository;
	
	@Mock
	private IMicroselAdherentsProxy adherentsProxy;
	
	@Mock
	private IArticleMapper articleMapper;
	
	@InjectMocks
	private ArticleServiceImpl articleService;

	private Article article;
	private ArticleDTO articleDTO;
	private TypeArticle typeArticle;
	private Article articleTest;
	private UserBean user;
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		article = new Article();
		articleDTO = new ArticleDTO();
		typeArticle = new TypeArticle();
		articleTest = new Article();
		user = new UserBean();
		
		when(articleRepository.findById((long) 1)).thenReturn(Optional.of(article));
		when(articleRepository.findById((long) 0)).thenReturn(Optional.empty());
		
		when(typeArticleRepository.findByTypeName("B")).thenReturn(Optional.of(typeArticle));
		when(typeArticleRepository.findByTypeName("A")).thenReturn(Optional.empty());
		
		when(adherentsProxy.consulterCompteAdherent("B")).thenReturn(user);
		
		when(articleMapper.articleDTOToArticle(articleDTO)).thenReturn(article);
		
		when(articleRepository.save(any(Article.class))).thenReturn(article);
		
	
	}	

	
	@Test
	public void testCreateArticle_whenEntityNotFoundException() {
		
		typeArticle.setTypeName("A");
		articleDTO.setTypeArticle(typeArticle.getTypeName());
		
		
		try {
			articleTest = articleService.createArticle(articleDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Le type d'article que vous souhaitez créer n'existe pas");
		}
		
		
		
	}
	
	@Test
	public void testCreateArticle_whenEntityNotFoundException_withAdherent() {
		
		typeArticle.setTypeName("B");
		articleDTO.setTypeArticle(typeArticle.getTypeName());
		articleDTO.setAuteurUsername("B");
		articleDTO.setAuteurId("B");
		user.setId("A");
		
		
		try {
			articleTest = articleService.createArticle(articleDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
		}
		
		
		
	}
	
	@Test
	public void testCreateArticle_withoutException_withVisiteur() throws EntityAlreadyExistsException, EntityNotFoundException {
		
		typeArticle.setTypeName("B");
		articleDTO.setTypeArticle(typeArticle.getTypeName());
		articleDTO.setAuteurUsername("visiteur");
		user.setId("B");
		articleDTO.setAuteurId("B");
		
		articleTest = articleService.createArticle(articleDTO);
		verify(articleRepository, times(1)).save(any(Article.class));
		Assert.assertTrue(articleTest.equals(article));
		
		
		
	}
	
	

}
