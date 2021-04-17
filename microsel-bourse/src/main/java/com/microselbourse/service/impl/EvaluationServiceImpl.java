package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IEvaluationRepository;
import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IEvaluationMapper;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IEvaluationService;
import com.microselbourse.service.IMailSenderService;

@Service
public class EvaluationServiceImpl implements IEvaluationService {

	@Autowired
	private IMicroselUsersProxy usersProxy;

	@Autowired
	private IEvaluationRepository evaluationRepository;

	@Autowired
	private IEchangeRepository echangeRepository;

	@Autowired
	private IEvaluationMapper evaluationMapper;

	@Autowired
	private IMailSenderService mailSender;

	@Override
	public Evaluation createEvaluation(Long echangeId, @Valid EvaluationDTO evaluationDTO)
			throws EntityNotFoundException, EntityAlreadyExistsException, UnsupportedEncodingException,
			MessagingException {

		Optional<Echange> echangeToEvaluate = echangeRepository.findById(echangeId);
		if (!echangeToEvaluate.isPresent())
			throw new EntityNotFoundException("L'échange que vous voulez évaluer n'existe pas");
		if (!echangeToEvaluate.get().getEmetteurId().equals(evaluationDTO.getAdherentId())
				&& !echangeToEvaluate.get().getRecepteurId().equals(evaluationDTO.getAdherentId()))
			throw new EntityNotFoundException(" Seul un adhérent participant à l'échange peut évaluer l'échange");

		/*
		 * UserBean adherent =
		 * adherentsProxy.consulterCompteAdherent(evaluationDTO.getAdherentId());
		 * if(adherent.getId()!= evaluationDTO.getAdherentId()) throw new
		 * EntityNotFoundException(
		 * " L'auteur de l'évaluation n'est pas identifié comme adhérent de l'association"
		 * );
		 */

		Optional<Evaluation> evaluationBySameAdherent = evaluationRepository
				.findByEchangeAndAdherentId(echangeToEvaluate.get(), evaluationDTO.getAdherentId());
		if (evaluationBySameAdherent.isPresent())
			throw new EntityAlreadyExistsException(
					"Chaque participant à un echange ne peut apporter qu'une seule évaluation de cet échange");

		Evaluation evaluationToCreate = evaluationMapper.evaluationDTOToEvaluation(evaluationDTO);

		evaluationToCreate.setEchange(echangeToEvaluate.get());
		evaluationToCreate.setDateEvaluation(LocalDate.now());

		System.out.println(evaluationDTO.getAdherentId() == echangeToEvaluate.get().getEmetteurId());
		System.out.println(evaluationDTO.getAdherentId());
		System.out.println(echangeToEvaluate.get().getEmetteurId());

		if (evaluationDTO.getAdherentId() == echangeToEvaluate.get().getEmetteurId()) {
			evaluationToCreate.setAdherentUsername(echangeToEvaluate.get().getEmetteurUsername());
			UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToEvaluate.get().getRecepteurId());
			Evaluation evaluationCreated = evaluationRepository.save(evaluationToCreate);
			mailSender.sendMailEchangeEvaluation(evaluationCreated, recepteur, "Evaluation de l'echange",
					"10A_EmetteurProposition_EchangeEvaluation");
			return evaluationCreated;
		} else {
			evaluationToCreate.setAdherentUsername(echangeToEvaluate.get().getRecepteurUsername());
			UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToEvaluate.get().getEmetteurId());
			Evaluation evaluationCreated = evaluationRepository.save(evaluationToCreate);
			mailSender.sendMailEchangeEvaluation(evaluationCreated, emetteur, "Evaluation de l'echange",
					"10B_RecepteurReponse_EchangeEvaluation");
			return evaluationCreated;
		}

	}

	@Override
	public List<Evaluation> findAllByEchangeId(Long id) {
		List<Evaluation> evaluations = evaluationRepository.findAllByEchangeId(id);
		return evaluations;
	}

}
