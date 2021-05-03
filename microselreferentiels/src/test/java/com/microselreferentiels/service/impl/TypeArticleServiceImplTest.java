package com.microselreferentiels.service.impl;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselreferentiels.dao.ITypeArticleRepository;
import com.microselreferentiels.entities.TypeArticle;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeArticleServiceImplTest {
	
	@Mock
	private ITypeArticleRepository typeArticleRepository;
	
	@InjectMocks
	private TypeArticleServiceImpl typeArticleService;

	private TypeArticle typeArticle;
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		typeArticle = new TypeArticle();
		
		when(typeArticleRepository.findById((long) 1)).thenReturn(Optional.of(typeArticle));
		when(typeArticleRepository.findById((long) 0)).thenReturn(Optional.empty());
		
	
	}	

	
	@Test
	public void testMethod() {
		
	}

}
