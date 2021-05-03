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

import com.microselreferentiels.dao.ITypeDocumentRepository;
import com.microselreferentiels.entities.TypeDocument;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TypeDocumentServiceImplTEst {
	
	@Mock
	private ITypeDocumentRepository typeDocumentRepository;
	
	@InjectMocks
	private TypeDocumentServiceImpl typeDocumentService;

	private TypeDocument typeDocument;
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		typeDocument = new TypeDocument();
		
		when(typeDocumentRepository.findById((long) 1)).thenReturn(Optional.of(typeDocument));
		when(typeDocumentRepository.findById((long) 0)).thenReturn(Optional.empty());
		
	
	}	

	
	@Test
	public void testMethod() {
		
	}

}
