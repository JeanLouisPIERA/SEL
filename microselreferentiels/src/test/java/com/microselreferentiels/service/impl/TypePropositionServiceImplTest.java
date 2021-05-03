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

import com.microselreferentiels.dao.IDocumentRepository;
import com.microselreferentiels.dao.ITypePropositionRepository;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.TypeProposition;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypePropositionServiceImplTest {
	
	@Mock
	private ITypePropositionRepository typePropositionRepository;
	
	@InjectMocks
	private TypePropositionServiceImpl typePropositionService;

	private TypeProposition typeProposition;
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		typeProposition = new TypeProposition();
		
		when(typePropositionRepository.findById((long) 1)).thenReturn(Optional.of(typeProposition));
		when(typePropositionRepository.findById((long) 0)).thenReturn(Optional.empty());
		
	
	}	

	
	@Test
	public void testMethod() {
		
	}

}
