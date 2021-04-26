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
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.EnumCategorie;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
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

	@Before
	public void setUp() {

		propositionTest = new Proposition();

		user = new UserBean();

		categorie = new Categorie();
		propositionDTO = new PropositionDTO();
		proposition = new Proposition();

		// Mocks CREATE
		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(user);
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

		// Mocks READ
		when(propositionRepository.findById((long) 0)).thenReturn(Optional.empty()); // Mock UPDATE
		when(propositionRepository.findById((long) 1)).thenReturn(Optional.of(proposition));

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
	public void testCreateProposition_withoutException() throws Exception {

		user.setId((String) "A");
		propositionDTO.setEmetteurId((String) "A");
		propositionDTO.setEnumTradeTypeCode("OFFRE");
		propositionDTO.setTitre("Correct");
		categorie.setName(EnumCategorie.BRICOLAGE);
		propositionDTO.setCategorieName(categorie.getName().getCode());

		propositionDTO.setEnumTradeTypeCode(EnumTradeType.OFFRE.getCode());
		propositionTest = propositionService.createProposition(propositionDTO);
		verify(propositionRepository, times(1)).save(any(Proposition.class));
		Assert.assertTrue(propositionTest.equals(proposition));

		propositionDTO.setEnumTradeTypeCode("DEMANDE");

		propositionDTO.setEnumTradeTypeCode(EnumTradeType.DEMANDE.getCode());
		propositionTest = propositionService.createProposition(propositionDTO);
		verify(propositionRepository, times(2)).save(any(Proposition.class));
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

	/*
	 * // TESTS UPDATE //
	 * *****************************************************************************
	 * ******************************
	 * 
	 * @Test public void
	 * testUpdateProposition_whenEntityNotFoundException_withUnknownPropositionToUpdate
	 * () {
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 0,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityNotFoundException.class)
	 * .hasMessage("L'Offre ou la Demande que vous souhaitez modifier n'existe pas."
	 * ); }
	 * 
	 * }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenDeniedAccessException_withAnotherUserProposition()
	 * {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "000.000.000");
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(DeniedAccessException.class)
	 * .hasMessage("Vous ne pouvez pas modifier la proposition d'un autre adhérent"
	 * ); } }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenDeniedAccessException_withStatutEchue() {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2020, 12, 31));
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage(
	 * "Vous ne pouvez pas modifier une OFFRE ou une DEMANDE dont la date de fin de publication est échue"
	 * ); } }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenDeniedAccessException_withStatutCloturee() {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.CLOTUREE);
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(DeniedAccessException.class)
	 * .hasMessage("Vous ne pouvez pas modifier une OFFRE ou une DEMANDE déjà clôturée"
	 * ); } }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenEntityNotFoundException_withUnknownCategorie() {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.ENCOURS);
	 * propositionDTO.setCategorieName(EnumCategorie.INCONNUE.getCode());
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityNotFoundException.class)
	 * .hasMessage("Votre modification est impossible : cette catégorie n'existe pas"
	 * ); } }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenEntityNotFoundException_withWrongTradeType() {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.ENCOURS);
	 * propositionDTO.setCategorieName(EnumCategorie.BRICOLAGE.getCode());
	 * propositionDTO.setEnumTradeTypeCode("Erreur");
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityNotFoundException.class).hasMessage(
	 * "Votre proposition ne peut être qu'une OFFRE ou une DEMANDE : merci de renseigner une des 2 valeurs"
	 * ); } }
	 * 
	 * @Test public void
	 * testUpdateProposition_whenEntityAlreadyExistsException_withSameUserAndSameTitle
	 * () {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.ENCOURS);
	 * propositionDTO.setCategorieName(EnumCategorie.BRICOLAGE.getCode());
	 * propositionDTO.setEnumTradeTypeCode("OFFRE");
	 * propositionDTO.setTitre("Wrong"); proposition.setTitre("Wrong");
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
	 * .hasMessage("Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre"
	 * ); }
	 * 
	 * propositionDTO.setTitre("Correct");
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
	 * .hasMessage("Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre"
	 * ); }
	 * 
	 * propositionDTO.setTitre("Wrong"); proposition.setTitre("Correct");
	 * 
	 * try { propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); } catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
	 * .hasMessage("Vous avez déjà une OFFRE ou une DEMANDE encours de publication avec le même titre"
	 * ); }
	 * 
	 * }
	 * 
	 * @Test public void testUpdateProposition_withoutException() throws Exception {
	 * 
	 * user.setId((String) "A"); propositionDTO.setEmetteurId((String) "A");
	 * proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.ENCOURS);
	 * propositionDTO.setCategorieName(EnumCategorie.BRICOLAGE.getCode());
	 * propositionDTO.setEnumTradeTypeCode("OFFRE");
	 * propositionDTO.setTitre("Correct"); proposition.setTitre("Wrong");
	 * 
	 * propositionTest = propositionService.updateProposition((long) 1,
	 * propositionDTO); verify(propositionRepository,
	 * times(1)).save(any(Proposition.class));
	 * Assert.assertTrue(propositionTest.equals(proposition));
	 * 
	 * }
	 * 
	 * // TESTS CLOSE //
	 * *****************************************************************************
	 * **************************
	 * 
	 * @Test public void
	 * testCloseProposition_whenEntityNotFoundException_withUnknownPropositionToUpdate
	 * () {
	 * 
	 * try { propositionTest = propositionService.closeProposition((long) 0); }
	 * catch (Exception e) {
	 * assertThat(e).isInstanceOf(EntityNotFoundException.class)
	 * .hasMessage("L'Offre ou la Demande que vous souhaitez clôturer n'existe pas."
	 * ); }
	 * 
	 * }
	 * 
	 * @Test public void
	 * testCloseProposition_whenDeniedAccessException_withStatutEchue() {
	 * 
	 * user.setId((String) "A"); proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2020, 12, 31));
	 * 
	 * try { propositionTest = propositionService.closeProposition((long) 1); }
	 * catch (Exception e) {
	 * assertThat(e).isInstanceOf(DeniedAccessException.class).hasMessage(
	 * "Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE dont la date de fin de publication est échue"
	 * ); } }
	 * 
	 * @Test public void
	 * testCloseProposition_whenDeniedAccessException_withStatutCloturee() {
	 * 
	 * user.setId((String) "A"); proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.CLOTUREE);
	 * 
	 * try { propositionTest = propositionService.closeProposition((long) 1); }
	 * catch (Exception e) { assertThat(e).isInstanceOf(DeniedAccessException.class)
	 * .hasMessage("Vous ne pouvez pas clôturer une OFFRE ou une DEMANDE déjà clôturée"
	 * ); } }
	 * 
	 * @Test public void testCloseProposition_withoutException() throws Exception {
	 * 
	 * user.setId((String) "A"); proposition.setEmetteurId((String) "A");
	 * proposition.setDateFin(LocalDate.of(2200, 12, 31));
	 * proposition.setStatut(EnumStatutProposition.ENCOURS);
	 * 
	 * propositionTest = propositionService.closeProposition((long) 1);
	 * verify(propositionRepository, times(1)).save(any(Proposition.class));
	 * Assert.assertTrue(propositionTest.equals(proposition));
	 * 
	 * }
	 */
}
