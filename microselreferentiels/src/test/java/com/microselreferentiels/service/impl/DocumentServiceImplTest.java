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
import com.microselreferentiels.dao.IDocumentRepository;
import com.microselreferentiels.dao.ITypeArticleRepository;
import com.microselreferentiels.dao.ITypeDocumentRepository;
import com.microselreferentiels.dto.ArticleDTO;
import com.microselreferentiels.dto.DocumentDTO;
import com.microselreferentiels.entities.Article;
import com.microselreferentiels.entities.Document;
import com.microselreferentiels.entities.EnumStatutDocument;
import com.microselreferentiels.entities.TypeArticle;
import com.microselreferentiels.entities.TypeDocument;
import com.microselreferentiels.exceptions.EntityAlreadyExistsException;
import com.microselreferentiels.exceptions.EntityNotFoundException;
import com.microselreferentiels.mapper.IArticleMapper;
import com.microselreferentiels.mapper.IDocumentMapper;
import com.microselreferentiels.proxies.IMicroselAdherentsProxy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DocumentServiceImplTest {
	
	@Mock
	private IDocumentRepository documentRepository;
	
	@Mock
	private ITypeDocumentRepository typeDocumentRepository;
	
	@Mock
	private IMicroselAdherentsProxy adherentsProxy;
	
	@Mock
	private IDocumentMapper documentMapper;
	
	
	@InjectMocks
	private DocumentServiceImpl documentService;

	private Document document;
	private DocumentDTO documentDTO;
	private TypeDocument typeDocument;
	private TypeDocument typeDocumentC;
	private Document documentTest;
	private UserBean user;
	
	
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		
		document = new Document();
		documentDTO = new DocumentDTO();
		typeDocument = new TypeDocument();
		documentTest = new Document();
		user = new UserBean();
		typeDocumentC = new TypeDocument();
		
		when(documentRepository.findById((long) 1)).thenReturn(Optional.of(document));
		when(documentRepository.findById((long) 0)).thenReturn(Optional.empty());
		
		when(typeDocumentRepository.findByTypeName("B")).thenReturn(Optional.of(typeDocument));
		when(typeDocumentRepository.findByTypeName("C")).thenReturn(Optional.of(typeDocumentC));
		when(typeDocumentRepository.findByTypeName("A")).thenReturn(Optional.empty());
		
		when(documentRepository.findByTypeDocumentAndDocumentStatutEncours(typeDocument, EnumStatutDocument.ENCOURS)).thenReturn(Optional.of(document));
		when(documentRepository.findByTypeDocumentAndDocumentStatutEncours(typeDocumentC, EnumStatutDocument.ENCOURS)).thenReturn(Optional.empty());
		
		when(adherentsProxy.consulterCompteAdherent("B")).thenReturn(user);
		
		when(documentMapper.documentDTOToDocument(documentDTO)).thenReturn(document);
		
		when(documentRepository.save(any(Document.class))).thenReturn(document);
		
	
	}	

	
	@Test
	public void testCreateStaticDocument_whenEntityNotFoundException() {
		
		typeDocument.setTypeName("A");
		documentDTO.setTypeDocument(typeDocument.getTypeName());
		
		
		try {
			documentTest = documentService.createStaticDocument(documentDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Le type de document que vous souhaitez créer n'existe pas");
		}
		
		
		
	}
	
	@Test
	public void testCreateStatic_whenEntityAlreadyExistsException_withTypeDocumentAndNoStatutEncours() {
		
		typeDocument.setTypeName("B");
		documentDTO.setTypeDocument(typeDocument.getTypeName());
		
		
		
		try {
			documentTest = documentService.createStaticDocument(documentDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
					.hasMessage("Il ne peut y exister qu'un seul document de ce type avec le statut en cours");
		}
		
		
		
	}
	
	@Test
	public void testCreateStatic_whenEntityNotFoundException_withWrongAdherent() {
		
		typeDocumentC.setTypeName("C");
		documentDTO.setTypeDocument(typeDocumentC.getTypeName());
		documentDTO.setAuteurUsername("B");
		documentDTO.setAuteurId("B");
		user.setId("A");
		
		
		
		try {
			documentTest = documentService.createStaticDocument(documentDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
		}
		
		
		
	}
	
	@Test
	public void testCreateDocument_withoutException() throws EntityAlreadyExistsException, EntityNotFoundException {
		
		typeDocumentC.setTypeName("C");
		documentDTO.setTypeDocument(typeDocumentC.getTypeName());
		documentDTO.setAuteurUsername("B");
		documentDTO.setAuteurId("B");
		user.setId("B");
		
		
		documentTest = documentService.createStaticDocument(documentDTO);
		verify(documentRepository, times(1)).save(any(Document.class));
		Assert.assertTrue(documentTest.equals(document));
		
		
		
		
		
	}
	
	

}
