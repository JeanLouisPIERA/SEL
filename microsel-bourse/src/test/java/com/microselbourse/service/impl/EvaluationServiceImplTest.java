package com.microselbourse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IEvaluationRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EvaluationServiceImplTest {
	
	@Mock
	private IMicroselUsersProxy userProxy;
	

	@Mock
	private IEvaluationRepository evaluationRepository;
	
	@Mock
	private IEchangeRepository echangeRepository;
	

	@InjectMocks
	private EvaluationServiceImpl evaluationService;
	
	

	
	private UserBean user;
	private Echange echange;
	private Evaluation evaluationTest;
	private Evaluation evaluation;
	private EvaluationDTO evaluationDTO;
	
	
	@Before
	public void setUp() {

		user = new UserBean();
		echange = new Echange();
		evaluationTest = new Evaluation();
		evaluation = new Evaluation();
		evaluationDTO = new EvaluationDTO();
	
		when(userProxy.consulterCompteAdherent((String) "A")).thenReturn(user);
		when(userProxy.consulterCompteAdherent((String) "B")).thenReturn(user);
		
		when(evaluationRepository.findById((long) 1)).thenReturn(Optional.of(evaluation));
		when(evaluationRepository.findById((long) 0)).thenReturn(Optional.empty());
		
		when(echangeRepository.findById((long)1)).thenReturn(Optional.of(echange));
		when(echangeRepository.findById((long)0)).thenReturn(Optional.empty());
		
		when(evaluationRepository.findByEchangeAndAdherentId(echange, "A")).thenReturn(Optional.of(evaluation));
	}
	
	@Test
	public void testCreateEvaluation_whenEntityNotFoundException_NoEchange() {
		
		echange.setId((long) 0);
		user.setId("A");
		echange.setEmetteurId("A");
		evaluationDTO.setAdherentId("A");
		evaluation.setEchange(echange);
		
		
		try {
			evaluationTest = evaluationService.createEvaluation((long) 0, evaluationDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'échange que vous voulez évaluer n'existe pas");
		}
	}
	
	@Test
	public void testCreateEvaluation_whenEntityNotFoundException_withWrongAdherent() {

		
		echange.setId((long) 1);
		user.setId("A");
		echange.setEmetteurId("A");
		echange.setRecepteurId("A");
		evaluationDTO.setAdherentId("B");	
		
		evaluation.setEchange(echange);
		
		try {
			evaluationTest = evaluationService.createEvaluation((long) 1, evaluationDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage(" Seul un adhérent participant à l'échange peut évaluer l'échange");
		}
	}
	
	@Test
	public void testCreateEvaluation_whenAlreadyExistsException() {
		
		echange.setId((long) 1);
		user.setId("A");
		echange.setEmetteurId("A");
		echange.setRecepteurId("A");
		evaluationDTO.setAdherentId("A");		
		
		evaluation.setEchange(echange);
		
		try {
			evaluationTest = evaluationService.createEvaluation((long) 1, evaluationDTO);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityAlreadyExistsException.class)
					.hasMessage("Chaque participant à un echange ne peut apporter qu'une seule évaluation de cet échange");
		}
		
	}
	
	@Test
	public void testModererEvaluation_whenEntityNotFoundException() {
		
		echange.setId((long) 1);
		user.setId("A");
		echange.setEmetteurId("A");
		evaluationDTO.setAdherentId("A");
		evaluation.setEchange(echange);
		
		evaluation.setId((long)0);
		
		try {
			evaluationTest = evaluationService.modererEvaluation((long) 0);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(EntityNotFoundException.class)
					.hasMessage("L'évaluation que vous souhaitez modérer n'existe pas.");
		}
		
		
	}
	
	@Test
	public void testModererEvaluation_whenEvaluationIsModerated() {
		
		echange.setId((long) 1);
		user.setId("A");
		echange.setEmetteurId("A");
		evaluationDTO.setAdherentId("A");
		evaluation.setEchange(echange);
		
		evaluation.setId((long)1);
		evaluation.setIsModerated(false);
		
		try {
			evaluationTest = evaluationService.modererEvaluation((long) 1);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(DeniedAccessException.class)
					.hasMessage("Vous ne pouvez pas modérer un article qui est déjà modéré");
		}
	}
	
	

}
