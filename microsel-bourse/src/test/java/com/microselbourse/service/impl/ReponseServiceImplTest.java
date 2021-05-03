package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
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
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.IWalletRepository;
import com.microselbourse.dto.PropositionDTO;
import com.microselbourse.dto.ReponseDTO;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Categorie;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutProposition;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IReponseMapper;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IEchangeService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReponseServiceImplTest {

	@Mock
	private IMicroselUsersProxy userProxy;

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
	
	@Mock
	private IBlocageRepository blocageRepository;
	
	@Mock
	private IWalletRepository walletRepository;
	
	@Mock
	private IEchangeService echangeService;
	
	@Mock
	private RabbitMQSender rabbitMQSender;
	
	
	

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
	private Blocage blocage;
	private Wallet wallet;
	private List<Reponse> reponses;

	@Before
	public void setUp() throws EntityNotFoundException {
		
		MockitoAnnotations.initMocks(this);

		propositionTest = new Proposition();
		user = new UserBean();
		categorie = new Categorie();
		propositionDTO = new PropositionDTO();
		proposition = new Proposition();
		reponse = new Reponse();
		reponseTest = new Reponse();
		reponseDTO = new ReponseDTO();
		echange = new Echange();
		blocage = new Blocage();
		wallet=new Wallet();
		reponses = new ArrayList<Reponse>();

		// Mocks CREATE REPONSE
		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "B")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "C")).thenReturn(user);
		when(propositionRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(propositionRepository.findById((long) 1)).thenReturn(Optional.of(proposition));
		when(reponseRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(reponseRepository.findById((long) 1)).thenReturn(Optional.of(reponse));
		when(reponseRepository.save(any(Reponse.class))).thenReturn(reponse);
		when(reponseMapper.reponseDTOToReponse(reponseDTO)).thenReturn(reponse);

		when(blocageRepository.findByAdherentIdAndStatutBlocage("A", EnumStatutBlocage.ENCOURS)).thenReturn(Optional.of(blocage));
		when(blocageRepository.findByAdherentIdAndStatutBlocage("B", EnumStatutBlocage.ENCOURS)).thenReturn(Optional.empty());
		when(blocageRepository.findByAdherentIdAndStatutBlocage("C", EnumStatutBlocage.ENCOURS)).thenReturn(Optional.empty());
		when(walletRepository.readByTitulaireId("B")).thenReturn(Optional.of(wallet));
		try {
			when(echangeService.createEchange((long)1)).thenReturn(echange);
		} catch (EntityAlreadyExistsException | EntityNotFoundException e) {
		}
		
		MessageMailReponse messageToRecepteur = new MessageMailReponse();
		Mockito.doNothing().when(rabbitMQSender).sendMessageMailReponse(messageToRecepteur);
		
		MessageMailReponse messageToEmetteur = new MessageMailReponse();
		Mockito.doNothing().when(rabbitMQSender).sendMessageMailReponse(messageToRecepteur);

	}

	// TESTS CREATE REPONSE
	// ***********************************************************************************************

	@Test
	public void testCreateReponse_whenEntityNotFoundException_withWrongUser() {

		user.setId((String) "000.000.000");
		reponseDTO.setRecepteurId((String) "A");

		try {
			reponseTest = reponseService.createReponse((long) 1, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Vous n'êtes pas identifié comme adhérent de l'association");
		}
	}
	
	@Test
	public void testCreateReponse_whenDeniedAccessException_withBlocage() {
		
		user.setId("A");
		reponseDTO.setRecepteurId("A");
		
		try {
			reponseTest = reponseService.createReponse((long) 1, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("La réponse ne peut pas être créée : il existe un blocage encours concernant l'adherent récepteur.");
		}
	
	}
	
	

	@Test
	public void testCreateReponse_whenEntityNotFoundException_withWrongPropositionId() {

		user.setId((String) "B");
		reponseDTO.setRecepteurId((String) "B");
		proposition.setId((long) 0);
		reponse.setProposition(proposition);

		try {
			reponseTest = reponseService.createReponse((long) 0, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'offre ou la demande à laquelle vous voulez répondre n'existe pas");
		}

	}

	@Test
	public void testCreateReponse_whenDeniedAccessException_withWrongStatutProposition() {

		user.setId((String) "B");
		reponseDTO.setRecepteurId((String) "B");
		proposition.setId((long) 1);
		proposition.setStatut(EnumStatutProposition.CLOTUREE);
		reponse.setProposition(proposition);

		

		try {
			reponseTest = reponseService.createReponse((long) 1, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez répondre qu'à une offre ou une demande en cours");
		}

		proposition.setStatut(EnumStatutProposition.ECHUE);

		try {
			reponseTest = reponseService.createReponse((long) 1, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez répondre qu'à une offre ou une demande en cours");
		}
	}

	@Test
	public void testCreateReponse_whenDeniedAccessException_withWrongRecepteur() {

		user.setId((String) "B");
		reponseDTO.setRecepteurId((String) "B");
		proposition.setId((long) 1);
		proposition.setStatut(EnumStatutProposition.ENCOURS);
		proposition.setEmetteurId((String) "B");
		reponse.setProposition(proposition);

		try {
			reponseTest = reponseService.createReponse((long) 1, reponseDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez pas répondre à une de vos propres offres ou demandes");
		}

	}
	
	@Test
	public void readRepônse_withEntityNotFoundException() {
		
		try {
			reponseTest = reponseService.readReponse((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'offre ou la demande que vous voulez consulter n'existe pas");
		}
		
	}
	
	@Test
	public void readRepônse_withoutException() throws EntityNotFoundException {
		
		reponseTest = reponseService.readReponse((long) 1);
		Assert.assertTrue(reponseTest.equals(reponse));
		
	}
	
	@Test
	public void findAllByWalletId_withEntityNotFoundException() {
		
		pageable = PageRequest.of(0, 6);
		
		
		
		try {
			Page<Reponse> reponses = reponseService.findAllByWalletId((long)0, pageable);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("Il n'existe aucune proposition enregistrée pour l'identifiant indiqué");
		}
		
	}
	
	@Test
	public void createReponse_withoutException() throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, EntityAlreadyExistsException, MessagingException {
		
		user.setId("C");
		
		List<Blocage> blocages = new ArrayList<Blocage>();
		
		propositionDTO.setEmetteurId("B"); 
		proposition.setEmetteurId("B");
		proposition.setStatut(EnumStatutProposition.ENCOURS);
		proposition.setDateEcheance(LocalDate.of(2021, 12, 31));
		proposition.setEnumTradeType(EnumTradeType.OFFRE);
		proposition.setReponses(reponses);
		
		reponseDTO.setCodePostal(34000);
		reponseDTO.setDescription("description");
		reponseDTO.setImage("image");
		reponseDTO.setRecepteurId("C");
		reponseDTO.setRecepteurUsername("usernameC");
		reponseDTO.setTitre("titre");
		reponseDTO.setValeur(20);
		reponseDTO.setVille("Montpellier");
		reponseDTO.setDateEcheance(LocalDate.of(2021, 12, 31).toString());
	
		
		reponse.setCodePostal(34000);
		reponse.setDateEcheance(LocalDate.of(2021, 12, 31));
		reponse.setDateReponse(LocalDate.now().plusDays(1));
		reponse.setDescription("description");
		reponse.setEchange(null);
		reponse.setEnumTradeType(EnumTradeType.DEMANDE);
		reponse.setId((long) 1);
		reponse.setImage("image");
		reponse.setProposition(proposition);
		reponse.setRecepteurId("C");
		reponse.setRecepteurUsername("usernameC");
		reponse.setTitre("titre");
		reponse.setValeur(20);
		reponse.setVille("Montpellier");
		
		
		
		reponseTest = reponseService.createReponse((long)1,reponseDTO);
		verify(reponseRepository, times(1)).save(any(Reponse.class));
		Assert.assertTrue(reponseTest.equals(reponse));
		
		proposition.setEnumTradeType(EnumTradeType.DEMANDE);
		reponse.setEnumTradeType(EnumTradeType.OFFRE);
		
		reponseTest = reponseService.createReponse((long)1,reponseDTO);
		verify(reponseRepository, times(3)).save(any(Reponse.class));
		Assert.assertTrue(reponseTest.equals(reponse));
		
	}
	
		

}