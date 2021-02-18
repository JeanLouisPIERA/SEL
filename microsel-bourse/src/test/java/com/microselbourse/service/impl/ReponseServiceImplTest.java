package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IReponseMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IEchangeService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReponseServiceImplTest {
	
	  @Mock
	  private IMicroselAdherentsProxy adherentProxy;
	  
	  @Mock
	  private IPropositionRepository propositionRepository;
	  
	  @Mock
	  private ICategorieRepository categorieRepository;
	
	  @Mock
	  private IPropositionMapper propositionMapper;
	  
	  @Mock
	  private IReponseRepository reponseRepository;
	  
	  @Mock
	  private IReponseMapper reponseMapper;
	  
	  @InjectMocks
	  private ReponseServiceImpl reponseService;
	  
	  private Echange echange;
	  private Reponse reponse; 
	  private Reponse reponseTest;
	  private ReponseDTO reponseDTO;
	  private Proposition propositionTest;
	  private UserBean user;
	  private Categorie categorie;
	  private Proposition proposition;
	  private PropositionDTO propositionDTO;
	  private List<Proposition> propositionList;
	  private Page<Proposition> propositionPage;
	  private PropositionCriteria propositionCriteria;
	  private Pageable pageable;
	  
	  @Before 
	  public void setUp() throws EntityNotFoundException {
		  
		  propositionTest = new Proposition();
		  user = new UserBean();
		  categorie = new Categorie();
		  propositionDTO = new PropositionDTO();
		  proposition = new Proposition();
		  reponse = new Reponse();
		  reponseTest = new Reponse();
		  reponseDTO = new ReponseDTO();
		  echange = new Echange();
	

		 //Mocks CREATE REPONSE
		 when(adherentProxy.consulterCompteAdherent((long)1)).thenReturn(user); 
		 when(propositionRepository.findById((long)0)).thenReturn(Optional.empty()); 
		 when(propositionRepository.findById((long)1)).thenReturn(Optional.of(proposition));
		 when(reponseRepository.save(any(Reponse.class))).thenReturn(reponse);
		 when(reponseMapper.reponseDTOToReponse(reponseDTO)).thenReturn(reponse);
		  
		  //Mocks CREATE 
		
		 when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((long)1,"Wrong", EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		 when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((long)1,"Correct", EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		 when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((long)1,"Correct", EnumTradeType.DEMANDE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		 when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((long)1,"Correct", EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.empty());
		 when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((long)1,"Correct", EnumTradeType.DEMANDE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.empty());
		 when(categorieRepository.findById((long)0)).thenReturn(Optional.empty()); //UPDATE
		 when(categorieRepository.findById((long)1)).thenReturn(Optional.of(categorie));
		 when(propositionMapper.propositionDTOToProposition(propositionDTO)).thenReturn(proposition);
		 when(propositionRepository.save(any(Proposition.class))).thenReturn(proposition);
		 
		 //Mocks READ 
		 when(propositionRepository.findById((long)0)).thenReturn(Optional.empty()); // Mock UPDATE
		 when(propositionRepository.findById((long)1)).thenReturn(Optional.of(proposition));
		 
		
	  }
	  
	  //TESTS CREATE REPONSE ***********************************************************************************************
	  
	  @Test
		public void testCreateReponse_whenEntityNotFoundException_withWrongUser() {
		  
		  user.setId((long)0);
		  reponseDTO.setRecepteurId((long)1);
	  
		  try {
				reponseTest = reponseService.createReponse((long)1, reponseDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
			}
	  }
	  
	  
	  @Test
	  public void testCreateReponse_whenEntityNotFoundException_withWrongPropositionId() {
		  
		  user.setId((long)1);
		  reponseDTO.setRecepteurId((long)1);
		  proposition.setId((long)0);
		  
		  try {
				reponseTest = reponseService.createReponse((long)0, reponseDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("L'offre ou la demande à laquelle vous vouslez répondre n'existe pas");
			}
		 
	  }
	  
	  @Test
	  public void testCreateReponse_whenDeniedAccessException_withWrongStatutProposition() {
		  
		  user.setId((long)1);
		  reponseDTO.setRecepteurId((long)1);
		  proposition.setId((long)1);
		  proposition.setStatut(EnumStatutProposition.CLOTUREE);
		  
		  try {
				reponseTest = reponseService.createReponse((long)1, reponseDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage("Vous ne pouvez répondre qu'à une offre ou une demande en cours");
			}
		 
		  proposition.setStatut(EnumStatutProposition.ECHUE);
		  
		  try {
				reponseTest = reponseService.createReponse((long)1, reponseDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage("Vous ne pouvez répondre qu'à une offre ou une demande en cours");
			}
	  }
	  
	  @Test
	  public void testCreateReponse_whenDeniedAccessException_withWrongRecepteur() {
		  
		  user.setId((long)1);
		  reponseDTO.setRecepteurId((long)1);
		  proposition.setId((long)1);
		  proposition.setStatut(EnumStatutProposition.ENCOURS);
		  proposition.setEmetteurId((long)1);
		  
		  try {
				reponseTest = reponseService.createReponse((long)1, reponseDTO);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage("Vous ne pouvez pas répondre à une de vos propres offres ou demandes");	  
		  }
	  
	  } 
	  
		/*
		 * @Test public void testCreateReponse_withoutException() throws Exception {
		 * 
		 * user.setId((long)1); reponseDTO.setRecepteurId((long)1);
		 * proposition.setId((long)1);
		 * proposition.setStatut(EnumStatutProposition.ENCOURS);
		 * proposition.setEmetteurId((long)2);
		 * 
		 * proposition.setEnumTradeType(EnumTradeType.OFFRE);
		 * 
		 * reponseTest = reponseService.createReponse((long)1, reponseDTO);
		 * verify(reponseRepository, times(1)).save(any(Reponse.class));
		 * Assert.assertTrue(reponseTest.equals(reponse));
		 * 
		 * proposition.setEnumTradeType(EnumTradeType.DEMANDE);
		 * 
		 * reponseTest = reponseService.createReponse((long)1, reponseDTO);
		 * verify(reponseRepository, times(2)).save(any(Reponse.class));
		 * Assert.assertTrue(reponseTest.equals(reponse));
		 * 
		 * }
		 */
	  
	  

}
