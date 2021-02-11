package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;


import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PropositionServiceImplTest {
	
	  @Mock
	  private IMicroselAdherentsProxy adherentProxy;
	  
	  @Mock
	  private IPropositionRepository propositionRepository;
	  
	  @Mock
	  private ICategorieRepository categorieRepository;
	
 	  @Mock
	  private IPropositionMapper propositionMapper;

	  @InjectMocks
	  private PropositionServiceImpl propositionService;
	  
	  private Proposition propositionTest;
	  private UserBean user;
	  private Categorie categorie;
	  private Proposition proposition;
	  private PropositionDTO propositionDTO;
	  private List<Proposition> propositionList;
	  private Page<Proposition> propositionPage;
	  private PropositionCriteria propositionCriteria;
	  
	  @Before 
	  public void setUp() {
		  
		  propositionTest = new Proposition();
		  user = new UserBean();
		  categorie = new Categorie();
		  propositionDTO = new PropositionDTO();
		  proposition = new Proposition();
		  
			/* propositionPage = new PageImpl<Proposition>(propositionList); */
		 
		 //Mocks CREATE 
		 when(adherentProxy.consulterCompteAdherent((long)1)).thenReturn(user);
		 when(propositionRepository.findByIdAndTitre((long)1,"Wrong")).thenReturn(Optional.of(proposition));
		 when(categorieRepository.findById((long)0)).thenReturn(Optional.empty());
		 when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie));
		 when(propositionMapper.propositionDTOToProposition(propositionDTO)).thenReturn(proposition);
		 when(propositionRepository.save(any(Proposition.class))).thenReturn(proposition);
		 
			/*
			 * //Mocks READ
			 * when(propositionRepository.findAll(any(PropositionSpecification.class),
			 * any(Pageable.class))).thenReturn(propositionPage); propositionCriteria = new
			 * PropositionCriteria();
			 */
	  }
	  
	  //TESTS CREATE PROPOSITION **********************************************************************************************
	  
	  @Test
		public void testCreateProposition_whenEntityNotFoundException_withWrongUser() {
		  
		  user.setId((long)0);
	  
		  try {
				propositionTest = propositionService.createProposition(propositionDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
			}
	  }
	  
	  
	  @Test
		public void testCreateProposition_whenEntityAlreadyExistsException_withWrongTitre() {
		  
		  user.setId((long)1);
		  propositionDTO.setTitre("Wrong");
		  
		  try {
				propositionTest = propositionService.createProposition(propositionDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityAlreadyExistsException.class).hasMessage("Vous avez déjà créé une proposition avec le même titre");
			}
	  }
	  
	 
	  @Test public void
	  testCreateProposition_whenEntityNotFoundException_withWrongCategorie() {
		  
		  user.setId((long)1);
		  propositionDTO.setTitre("Correct");
		  categorie.setId((long)0);
		  propositionDTO.setIdCategorie(categorie.getId());
		  
		  try {
				propositionTest = propositionService.createProposition(propositionDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("La catégorie dans laquelle vous avez choisi de publier n'existe pas");
			}
	  }
	 
	  @Test public void
	  testCreateProposition_whenEntityAlreadyExistsException_withWrongTradeType() {
		  
		  user.setId((long)1);
		  propositionDTO.setTitre("Correct");
		  categorie.setId((long)1);
		  propositionDTO.setIdCategorie(categorie.getId());
		  propositionDTO.setEnumTradeTypeCode("Wrong");
		  
		  try {
				propositionTest = propositionService.createProposition(propositionDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("La saisie du type OFFRE ou DEMANDE de votre proposition est incorrecte");
			}
	  }
	  
	  @Test public void testCreateProposition_withoutException() throws Exception {
		  
		  user.setId((long)1);
		  propositionDTO.setTitre("Correct");
		  categorie.setId((long)1);
		  propositionDTO.setIdCategorie(categorie.getId());
		  
		  propositionDTO.setEnumTradeTypeCode(EnumTradeType.OFFRE.getCode());
			  propositionTest = propositionService.createProposition(propositionDTO);
			  verify(propositionRepository, times(1)).save(any(Proposition.class));
			  Assert.assertTrue(propositionTest.equals(proposition));
		  
		  propositionDTO.setEnumTradeTypeCode(EnumTradeType.DEMANDE.getCode());
			  propositionTest = propositionService.createProposition(propositionDTO);
			  verify(propositionRepository, times(2)).save(any(Proposition.class));
			  Assert.assertTrue(propositionTest.equals(proposition));
		  
	  }
	  
	  //TESTS READ PROPOSITION ****************************************************************************************************
	  
		/*
		 * @Test public void searchAllPropositionsByCriteria( ) {
		 * 
		 * propositionTest.setId((long)1); proposition.setId((long)2);
		 * propositionList.add(propositionTest); propositionList.add(proposition);
		 * Pageable pageable = PageRequest.of(0,6);
		 * 
		 * Page<Proposition> propositionPageTest =
		 * propositionService.searchAllPropositionsByCriteria(propositionCriteria,
		 * pageable); verify(propositionRepository,
		 * times(1)).findAll(any(PropositionSpecification.class), any(Pageable.class));
		 * Assertions.assertTrue(propositionPageTest.getNumberOfElements()==2);
		 * Assertions.assertTrue(propositionPageTest.getTotalPages()==1);
		 * Assertions.assertTrue(propositionPageTest.getContent().get(0).getId()==
		 * (long)1);
		 * Assertions.assertTrue(propositionPageTest.getContent().get(1).getId()==(long)
		 * 2); }
		 */
	  
	  
		 
	  
}