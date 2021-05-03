package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.ICategorieRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IPropositionRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IPropositionMapper;
import com.microselbourse.mapper.IReponseMapper;
import com.microselbourse.proxies.IMicroselUsersProxy;


@SpringBootTest
@RunWith(SpringRunner.class)
public class EchangeServiceImplTest {

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
	private IEchangeRepository echangeRepository;

	@InjectMocks
	private EchangeServiceImpl echangeService;

	private UserBean emetteur1;
	private UserBean recepteur2;
	private Echange echangeTest;
	private Echange echange;
	private Reponse reponse;
	private Proposition proposition;

	@Before
	public void setUp() {

		recepteur2 = new UserBean();
		emetteur1 = new UserBean();
		echangeTest = new Echange();
		echange = new Echange();
		proposition = new Proposition();
		reponse = new Reponse();

		// Mocks READ ECHANGE
		when(echangeRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(reponseRepository.findById((long) 0)).thenReturn(Optional.empty());
		when(echangeRepository.findById((long) 1)).thenReturn(Optional.of(echange));
		when(reponseRepository.findById((long) 1)).thenReturn(Optional.of(reponse));
		when(echangeRepository.findById((long) 2)).thenReturn(Optional.empty());
		when(reponseRepository.findById((long) 2)).thenReturn(Optional.of(reponse));
		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(emetteur1);
		when(userProxy.consulterCompteAdherent((String) "B")).thenReturn(recepteur2);
		when(echangeRepository.save(any(Echange.class))).thenReturn(echange);

	}
	
	// TESTS CREATE ECHANGE
		// ***************************************************************************************

		@Test
		public void testCreateEchange_whenEntityAlreadyException() {
			
			echange.setId((long) 1);
			
			try {
				echangeTest = echangeService.createEchange((long) 1);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
						.hasMessage("Un échange existe déjà pour cette réponse à cette proposition");
			}
			
		}
		
		@Test
		public void testCreateEchange_whenEntityNotFoundException() {
			
			echange.setId((long) 0);
			reponse.setId((long) 0);

			try {
				echangeTest = echangeService.createEchange((long) 0);
			} catch (Exception e) {
				assertThat(e).isInstanceOf(EntityNotFoundException.class)
						.hasMessage("Un échange ne peut pas être créé s'il n'existe pas déjà une réponse à une proposition");
			}
			
		}

	// TESTS READ ECHANGE
	// ***********************************************************************************************

	@Test
	public void testReadEchange_whenEntityNotFoundException() {

		echange.setId((long) 0);
		reponse.setId((long) 0);

		try {
			echangeTest = echangeService.readEchange((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'échange que vous voulez consulter n'existe pas");
		}
	}

	@Test
	public void testReadEchange_withoutException_withAlreadyExistingEchange() throws Exception {

		echange.setId((long) 1);
		echange.setStatutEchange(EnumStatutEchange.ENREGISTRE);
		echange.setDateEcheance(LocalDate.of(2000, 01, 01));

		echangeTest = echangeService.readEchange((long) 1);
		Assert.assertTrue(echangeTest.equals(echange));

		echange.setStatutEchange(EnumStatutEchange.ENREGISTRE);
		echange.setDateEcheance(LocalDate.of(2100, 01, 01));

		echangeTest = echangeService.readEchange((long) 1);
		Assert.assertTrue(echangeTest.equals(echange));

		echange.setStatutEchange(EnumStatutEchange.CONFIRME);
		echange.setDateEcheance(LocalDate.of(2000, 01, 01));

		echangeTest = echangeService.readEchange((long) 1);
		Assert.assertTrue(echangeTest.equals(echange));
	}

	

	
	
	
}