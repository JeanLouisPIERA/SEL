package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumCategorie;
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
	  private IPropositionMapper propositionMapper;

	  @InjectMocks
	  private PropositionServiceImpl propositionService;
	  
	  private Proposition propositionTest;
	  private PropositionDTO propositionDTOPiano;
	  private UserBean user1;
	  private Categorie cours; 
	  private Proposition propositionPiano;
	  
	  
	  @Before 
	  public void setUp() {
		  
		  propositionDTOPiano = new PropositionDTO(
				  "OFFRE", (long) 1, "Cours de piano","Donne des cours de piano", "Paris", 75000, 25, 
				  LocalDate.of(2021, 03, 10), LocalDate.of(2021, 05, 06));
		  
		  user1 = new UserBean(); 
		  user1.setId((long)1);
		  
		  cours = new Categorie((long)1, EnumCategorie.COURS);
		  
		  /*Proposition(Long id, Long emetteurId, EnumTradeType enumTradeType, String titre, String description,
					String ville, Integer codePostal, Integer valeur, LocalDate dateFin, LocalDate dateEcheance,
					Categorie categorie, LocalDate dateDebut)*/
		  
		 propositionPiano = new Proposition((long) 1, (long) 1, EnumTradeType.OFFRE, "Cours de Piano", "Donne cours de piano",
				 "Paris", 75000, 25, LocalDate.of(2021, 03, 10), LocalDate.of(2021, 05, 06), cours, LocalDate.of(2021, 02, 06));
		  
		
	  }
	  
	  //TESTS CREATE PROPOSITION **********************************************************************************************
	  
	  @Test
		public void testCreateProposition_whenEntityNotFoundException_withWrongUser() {
		  
		  when(adherentProxy.consulterCompteAdherent((long)1)).thenReturn(null);
	  
		  try {
				propositionTest = propositionService.createProposition(propositionDTOPiano);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
			}
		  
	  }
	  
	  
	  @Test
		public void testCreateProposition_whenEntityAlreadyExistsException_withWrongTitre() {
		  
		  when(adherentProxy.consulterCompteAdherent((long)1)).thenReturn(user1);
		  
		  when(propositionRepository.findByIdAndTitre((long)1,"Cours de piano")).thenReturn(Optional.of(propositionPiano));
		  
		  try {
				propositionTest = propositionService.createProposition(propositionDTOPiano);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityAlreadyExistsException.class).hasMessage("Ce titre de proposition existe déjà");
			}
		  
	  }
	  
		/*
		 * @Test public void
		 * testCreateProposition_whenEntityNotFoundException_withWrongCategorie() {
		 * 
		 * }
		 * 
		 * @Test public void
		 * testCreateProposition_whenEntityAlreadyExistsException_withWrongTradeType() {
		 * 
		 * }
		 * 
		 * @Test public void testCreateAccount_withoutException() throws Exception {
		 * 
		 * }
		 */
	  
}