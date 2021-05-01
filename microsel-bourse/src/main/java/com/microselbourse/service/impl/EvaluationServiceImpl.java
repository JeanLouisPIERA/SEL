package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.EvaluationCriteria;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IEvaluationRepository;
import com.microselbourse.dao.specs.EvaluationSpecification;
import com.microselbourse.dto.EvaluationDTO;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.MessageMailEchangeEvaluation;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.mapper.IEvaluationMapper;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IEvaluationService;
import com.microselbourse.service.IMailSenderService;

@Service
@Transactional
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

	@Autowired
	private RabbitMQSender rabbitMQSender;

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

		Optional<Evaluation> evaluationBySameAdherent = evaluationRepository
				.findByEchangeAndAdherentId(echangeToEvaluate.get(), evaluationDTO.getAdherentId());
		if (evaluationBySameAdherent.isPresent())
			throw new EntityAlreadyExistsException(
					"Chaque participant à un echange ne peut apporter qu'une seule évaluation de cet échange");

		Evaluation evaluationToCreate = evaluationMapper.evaluationDTOToEvaluation(evaluationDTO);

		evaluationToCreate.setEchange(echangeToEvaluate.get());
		evaluationToCreate.setDateEvaluation(LocalDate.now());
		evaluationToCreate.setIsModerated(Boolean.FALSE);

		Evaluation evaluationCreated = evaluationRepository.save(evaluationToCreate);

		if (evaluationDTO.getAdherentId() == echangeToEvaluate.get().getEmetteurId()) {

			UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToEvaluate.get().getRecepteurId());

			MessageMailEchangeEvaluation messageEvaluationToRecepteur = new MessageMailEchangeEvaluation();
			messageEvaluationToRecepteur.setEvaluation(evaluationCreated);
			messageEvaluationToRecepteur.setDestinataire(recepteur);
			messageEvaluationToRecepteur.setSubject("Evaluation de l'echange");
			messageEvaluationToRecepteur.setMicroselBourseMailTemplate("10A_EmetteurProposition_EchangeEvaluation");
			rabbitMQSender.sendMessageMailEchangeEvaluation(messageEvaluationToRecepteur);

			return evaluationRepository.save(evaluationCreated);

		} else {

			UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToEvaluate.get().getEmetteurId());

			MessageMailEchangeEvaluation messageEvaluationToEmetteur = new MessageMailEchangeEvaluation();
			messageEvaluationToEmetteur.setEvaluation(evaluationCreated);
			messageEvaluationToEmetteur.setDestinataire(emetteur);
			messageEvaluationToEmetteur.setSubject("Evaluation de l'echange");
			messageEvaluationToEmetteur.setMicroselBourseMailTemplate("10B_RecepteurReponse_EchangeEvaluation");
			rabbitMQSender.sendMessageMailEchangeEvaluation(messageEvaluationToEmetteur);

			return evaluationRepository.save(evaluationCreated);

		}

	}

	@Override
	public List<Evaluation> findAllByEchangeIdAndNotModerated(Long id) {
		List<Evaluation> evaluations = evaluationRepository.findAllByEchangeIdAndNotModerated(id, Boolean.FALSE);
		return evaluations;
	}

	@Override
	public Evaluation modererEvaluation(@Valid Long id) throws EntityNotFoundException, DeniedAccessException {

		Optional<Evaluation> evaluationToModerate = evaluationRepository.findById(id);
		if (!evaluationToModerate.isPresent())
			throw new EntityNotFoundException("L'évaluation que vous souhaitez modérer n'existe pas.");

		if (evaluationToModerate.get().getIsModerated() == true)
			throw new DeniedAccessException("Vous ne pouvez pas modérer un article qui est déjà modéré");

		evaluationToModerate.get().setIsModerated(true);

		return evaluationRepository.save(evaluationToModerate.get());
	}

	@Override
	public Page<Evaluation> searchAllEvaluationsByCriteria(EvaluationCriteria evaluationCriteria, Pageable pageable) {
		Specification<Evaluation> evaluationSpecification = new EvaluationSpecification(evaluationCriteria);

		Page<Evaluation> evaluations = evaluationRepository.findAll(evaluationSpecification, pageable);
		return evaluations;
	}

}
