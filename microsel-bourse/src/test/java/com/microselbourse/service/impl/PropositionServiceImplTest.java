package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.PropositionCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.PropositionUpdateDTO;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IPropositionUpdateMapper;
import com.microselbourse.proxies.IMicroselUsersProxy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PropositionServiceImplTest {

	@Mock
	private IMicroselUsersProxy userProxy;

	@Mock
	private IPropositionRepository propositionRepository;

	@Mock
	private ICategorieRepository categorieRepository;

	@Mock
	private IPropositionMapper propositionMapper;
	
	@Mock
	private IBlocageRepository blocageRepository;
	
	@Mock
	private IWalletRepository walletRepository;
	
	@Mock
	private IPropositionUpdateMapper propositionUpdateMapper;

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
	private Pageable pageable;
	private Blocage blocage;
	private Wallet wallet;
	private PropositionUpdateDTO propositionUpdateDTO;
	private Proposition propositionUpdated;
	
	

	@Before
	public void setUp() {

		propositionTest = new Proposition();

		user = new UserBean();

		categorie = new Categorie();
		propositionDTO = new PropositionDTO();
		proposition = new Proposition();
		blocage = new Blocage();
		wallet = new Wallet();
		propositionUpdateDTO = new PropositionUpdateDTO();
		propositionUpdated = new Proposition();
		

		// Mocks CREATE
		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "B")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "C")).thenReturn(user);
		
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((String) "A", "Wrong",
				EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((String) "A", "Correct",
				EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((String) "A", "Correct",
				EnumTradeType.DEMANDE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((String) "A", "Correct",
				EnumTradeType.OFFRE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.empty());
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours((String) "A", "Correct",
				EnumTradeType.DEMANDE, EnumStatutProposition.ENCOURS)).thenReturn(Optional.empty());
		when(categorieRepository.findByName(EnumCategorie.INCONNUE)).thenReturn(Optional.empty()); // UPDATE
		when(categorieRepository.findByName((EnumCategorie.BRICOLAGE))).thenReturn(Optional.of(categorie));
		when(propositionMapper.propositionDTOToProposition(propositionDTO)).thenReturn(proposition);
		when(propositionRepository.save(any(Proposition.class))).thenReturn(proposition);
		when(blocageRepository
		.findByAdherentIdAndStatutBlocage("B", EnumStatutBlocage.ENCOURS)).thenReturn(Optional.of(blocage));
		when(blocageRepository
				.findByAdherentIdAndStatutBlocage("C", EnumStatutBlocage.ENCOURS)).thenReturn(Optional.empty());
		when(propositionMapper.propositionDTOToProposition(propositionDTO)).thenReturn(proposition);
		when(walletRepository.readByTitulaireId("A")).thenReturn(Optional.of(wallet));
		
		when(propositionRepository.findById((long)0)).thenReturn(Optional.empty());
		when(propositionRepository.findById((long)1)).thenReturn(Optional.of(proposition));
		when(propositionRepository.findById((long)2)).thenReturn(Optional.of(proposition));
		
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours("A",
							"B", EnumTradeType.DEMANDE,	EnumStatutProposition.ENCOURS)).thenReturn(Optional.of(proposition));
		
		when(propositionUpdateMapper.propositionUpdateDTOToProposition(propositionUpdateDTO)).thenReturn(propositionUpdated);
		
		when(propositionRepository.findByEmetteurIdAndTitreAndEnumTradeTypeAndStatutEnCours("C",
				"C", EnumTradeType.OFFRE,	EnumStatutProposition.ENCOURS)).thenReturn(Optional.empty());

		// Mocks READ
		when(propositionRepository.findById((long) 0)).thenReturn(Optional.empty()); // Mock UPDATE
		when(propositionRepository.findById((long) 1)).thenReturn(Optional.of(proposition));

	}
	//************************************************************************************************************
	
	
	  @Test public void testCloseProposition_withEntityNotFoundException() {
		  
		  try {
				propositionTest = propositionService.closeProposition((long)0, "A");
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
						.hasMessage("L'Offre ou la Demande que vous souhaitez clôturer n'existe pas.");
			}
		  
	  }
	  
	  @Test public void testCloseProposition_withDeniedAccessException_withWrongEmetteurId() {
		  
		  proposition.setId((long)2);
		  proposition.setEmetteurId("B");
		  
		  try {
				propositionTest = propositionService.closeProposition((long)2, "A");
			} catch (Exception e) {
				assertThat(e).isInstanceOf(DeniedAccessException.class)
						.hasMessage("Vous ne pouvez pas clôturer la proposition d'un autre adhérent");
			}
		  
	  }
	  
	  
	 
	//UPDATE
	
	@Test
	public void testUpdateProposition_withEntityNotFoundException() {
		
		try {
			propositionTest = propositionService.updateProposition((long)0, "A", propositionUpdateDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'Offre ou la Demande que vous souhaitez modifier n'existe pas.");
		}
	}
	
	@Test
	public void testUpdateProposition_withDeniedAccessException_withWrongEmetteurId() {
		
		proposition.setId((long)1);
		proposition.setEmetteurId("B");
		
		try {
			propositionTest = propositionService.updateProposition((long)1, "A", propositionUpdateDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez pas modifier la proposition d'un autre adhérent");
		}
	}
	
	@Test
	public void testUpdateProposition_withDeniedAccessException_withDatePublicationEchue() {
		proposition.setEmetteurId("A");
		proposition.setDateFin(LocalDate.now().minusDays(10));
		try {
			propositionTest = propositionService.updateProposition((long)1, "A", propositionUpdateDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez pas modifier une OFFRE ou une DEMANDE dont la date de fin de publication est échue");
		}
		
	}
	
	@Test
	public void testUpdateProposition_withDeniedAccessException_withStatutCloture() {
		
		proposition.setEmetteurId("A");
		proposition.setDateFin(LocalDate.now().plusDays(10));
		proposition.setStatut(EnumStatutProposition.CLOTUREE);
		
		try {
			propositionTest = propositionService.updateProposition((long)1, "A", propositionUpdateDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez pas modifier une OFFRE ou une DEMANDE déjà clôturée");
		}
		
	}
	
	@Test 
	public void testUpdateProposition_withEntityAlreadyExistsException() {
		proposition.setEmetteurId("A");
		proposition.setDateFin(LocalDate.now().plusDays(10));
		proposition.setStatut(EnumStatutProposition.ENCOURS);
		proposition.setTitre("A");
		propositionUpdateDTO.setTitre("B");
		proposition.setEnumTradeType(EnumTradeType.DEMANDE);
		
		try {
			propositionTest = propositionService.updateProposition((long)1, "A", propositionUpdateDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
					.hasMessage("Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre");
		}
		
	}
	
	
	
	@Test
	public void testUpdateProposition_withoutException() throws EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException {
		
		proposition.setEmetteurId("A");
		proposition.setDateFin(LocalDate.now().plusDays(10));
		proposition.setStatut(EnumStatutProposition.ENCOURS);
		proposition.setTitre("A");
		propositionUpdateDTO.setTitre("A");
		
		
		propositionUpdated.setDateFin(LocalDate.now().plusDays(15));
		propositionUpdated.setDateFin(LocalDate.now().plusDays(15));
		
		propositionTest = propositionService.updateProposition((long)1, "A", propositionUpdateDTO);
		verify(propositionRepository, times(1)).save(any(Proposition.class));
		Assert.assertTrue(propositionTest.equals(proposition));
	}
	
	

	// TESTS CREATE PROPOSITION
	// **********************************************************************************************

	@Test
	public void testCreateProposition_whenEntityNotFoundException_withWrongUser() {

		user.setId((String) "000.000.000");
		propositionDTO.setEmetteurId((String) "A");

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
		}
	}
	
	@Test
	public void testCreateProposition_whenDeniedAccessException_withBlocage() {

		user.setId((String) "B");
		propositionDTO.setEmetteurId((String) "B");
		

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage(
					"La proposition ne peut pas être créée : il existe un blocage encours concernant l'émetteur de la proposition.");
		}
	}

	@Test
	public void testCreateProposition_whenEntityNotFoundException_withWrongTradeType() {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("Erreur");

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage(
					"Votre proposition ne peut être qu'une OFFRE ou une DEMANDE : merci de renseigner une des 2 valeurs");
		}
	}

	@Test
	public void testCreateProposition_whenEntityAlreadyExistsException_withWrongTitre() {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Wrong");

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
					.hasMessage("Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre");
		}
	}

	@Test
	public void testCreateProposition_whenEntityNotFoundException_withWrongCategorie() {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Correct");
		categorie.setName(EnumCategorie.INCONNUE);
		propositionDTO.setCategorieName(categorie.getName().getCode());

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("La catégorie dans laquelle vous avez choisi de publier n'existe pas");
		}
	}
	
	@Test
	public void testCreateProposition_whenEntityNotFoundException_withWrongDateEcheance() {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Correct");
		categorie.setName(EnumCategorie.BRICOLAGE);
		propositionDTO.setCategorieName(categorie.getName().getCode());
		proposition.setDateEcheance(LocalDate.now().minusDays(2));
		
		

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
			
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("La date d'échéance et la date de fin de publication doivent être postérieures à la date d'aujourd'hui");
		}
	}
	
	@Test
	public void testCreateProposition_whenEntityNotFoundException_withWrongDateFin() {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Correct");
		categorie.setName(EnumCategorie.BRICOLAGE);
		propositionDTO.setCategorieName(categorie.getName().getCode());
		proposition.setDateEcheance(LocalDate.now().plusDays(2));
		proposition.setDateFin(LocalDate.now().minusDays(2));
		

		try {
			propositionTest = propositionService.createProposition(propositionDTO);
			
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("La date d'échéance et la date de fin de publication doivent être postérieures à la date d'aujourd'hui");
		}
	}

	@Test
	public void testCreateProposition_withoutException() throws Exception {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Correct");
		categorie.setName(EnumCategorie.BRICOLAGE);
		propositionDTO.setCategorieName(categorie.getName().getCode());

		//propositionDTO.setEnumTradeTypeCode(EnumTradeType.OFFRE.getCode());
		proposition.setDateEcheance(LocalDate.now().plusDays(2));
		proposition.setDateFin(LocalDate.now().plusDays(2));
		
		proposition.setEmetteurId((String) "A");
		
		propositionTest = propositionService.createProposition(propositionDTO);
		verify(propositionRepository, times(1)).save(any(Proposition.class));
		Assert.assertTrue(propositionTest.equals(proposition));

	}

	// TESTS READ PROPOSITION
	// ****************************************************************************************************

	@Test
	public void searchAllPropositionsByCriteria() {

		propositionList = new ArrayList<Proposition>();
		propositionTest.setId((long) 1);
		proposition.setId((long) 2);
		propositionList.add(propositionTest);
		propositionList.add(proposition);
		pageable = PageRequest.of(0, 6);
		propositionCriteria = new PropositionCriteria();
		propositionPage = new PageImpl<Proposition>(propositionList);

		when(propositionRepository.findAll(any(PropositionSpecification.class), any(Pageable.class)))
				.thenReturn(propositionPage);

		Page<Proposition> propositionPageTest = propositionService.searchAllPropositionsByCriteria(propositionCriteria,
				pageable);
		verify(propositionRepository, times(1)).findAll(any(PropositionSpecification.class), any(Pageable.class));
		Assertions.assertTrue(propositionPageTest.getNumberOfElements() == 2);
		Assertions.assertTrue(propositionPageTest.getTotalPages() == 1);
		Assertions.assertTrue(propositionPageTest.getContent().get(0).getId() == (long) 1);
		Assertions.assertTrue(propositionPageTest.getContent().get(1).getId() == (long) 2);
	}

	@Test
	public void readProposition_whenEntitityNotFoundException() {

		try {
			propositionTest = propositionService.readProposition((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'offre ou la demande que vous voulez consulter n'existe pas");
		}
	}

	@Test
	public void testReadProposition_withoutException() throws Exception {

		propositionTest = propositionService.readProposition((long) 1);
		Assert.assertTrue(propositionTest.equals(proposition));
	}

	
	
	
}