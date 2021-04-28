package com.microselbourse.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.BlocageCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.specs.BlocageSpecification;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.MessageMailDeblocage;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.Proposition;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IBlocageService;

@Service
public class BlocageServiceImpl implements IBlocageService {

	@Autowired
	private IEchangeRepository echangeRepository;

	@Autowired
	private IMicroselUsersProxy usersProxy;

	@Autowired
	private IBlocageRepository blocageRepository;
	
	@Autowired
	RabbitMQSender rabbitMQSender;

	
	@Override
	public Blocage createBlocage(Long echangeId, String adherentId, String adherentUsername) throws EntityNotFoundException {

		Optional<Echange> echangeToCheck = echangeRepository.findById(echangeId);
		if (!echangeToCheck.isPresent())
			throw new EntityNotFoundException("L'échange à l'origine du blocage n'existe pas");

		/*
		 * UserBean adherentToBlock = usersProxy.consulterCompteAdherent(adherentId); if
		 * (adherentToBlock == null) throw new
		 * EntityNotFoundException("L'identifiant de l'adhérent que vous souhaitez bloquer n'existe pas"
		 * );
		 */

		Blocage blocageToCreate = new Blocage();
		blocageToCreate.setAdherentId(adherentId);
		blocageToCreate.setAdherentUsername(adherentUsername);
		blocageToCreate.setDateDebutBlocage(LocalDate.now());
		blocageToCreate.setEchange(echangeToCheck.get());
		blocageToCreate.setStatutBlocage(EnumStatutBlocage.ENCOURS);

		return blocageRepository.save(blocageToCreate);

	}
	
	@Override
	public Blocage createBlocageFromConflit(Long echangeId, String adherentId, String adherentUsername) throws EntityNotFoundException {

		Optional<Echange> echangeToCheck = echangeRepository.findById(echangeId);
		if (!echangeToCheck.isPresent())
			throw new EntityNotFoundException("L'échange à l'origine du blocage n'existe pas");

		Blocage blocageToCreate = new Blocage();
		blocageToCreate.setAdherentId(adherentId);
		blocageToCreate.setAdherentUsername(adherentUsername);
		blocageToCreate.setDateDebutBlocage(LocalDate.now());
		blocageToCreate.setEchange(echangeToCheck.get());
		blocageToCreate.setStatutBlocage(EnumStatutBlocage.ENCOURS);

		return blocageRepository.save(blocageToCreate);

	}
	

	@Override
	public Blocage readBlocage(String adherentId) throws EntityNotFoundException {

		Optional<Blocage> blocageToRead = blocageRepository.findByAdherentId(adherentId);
		if (!blocageToRead.isPresent())
			throw new EntityNotFoundException("Le blocage que vous recherchez n'existe pas");

		return blocageToRead.get();
	}

	@Override
	public Page<Blocage> searchAllPropositionsByCriteria(BlocageCriteria blocageCriteria, Pageable pageable) {
		Specification<Blocage> blocageSpecification = new BlocageSpecification(blocageCriteria);
		Page<Blocage> blocages = blocageRepository.findAll(blocageSpecification, pageable);
		return blocages;
	}

	@Override
	public Blocage annulerBlocage(@Valid Long id) throws EntityNotFoundException {
		Optional<Blocage> blocageToAnnuler = blocageRepository.findById(id);
		if (!blocageToAnnuler.isPresent())
			throw new EntityNotFoundException("Aucun blocage n'existe pour cet identifiant");

		blocageToAnnuler.get().setDateFinBlocage(LocalDate.now());
		blocageToAnnuler.get().setStatutBlocage(EnumStatutBlocage.ANNULE);
		
		UserBean adherent = usersProxy.consulterCompteAdherent(blocageToAnnuler.get().getAdherentId());
				
		/*
		 * mailSender.sendMailEchangeConfirmation(blocageToAnnuler,adherent,
		 * "Deblocage de votre compte",
		 * "11_AdherentBlocage_BlocageAnnulation");
		 */
		
		MessageMailDeblocage messageMailDeblocage = new MessageMailDeblocage();
		messageMailDeblocage.setBlocage(blocageToAnnuler.get());
		messageMailDeblocage.setAdherent(adherent);
		messageMailDeblocage.setSubject("Deblocage de votre compte");
		messageMailDeblocage.setMicroselBourseMailTemplate("11_AdherentBlocage_BlocageAnnulation");
		rabbitMQSender.sendMessageMailDeblocage(messageMailDeblocage);//---------------------------------------------------------->RMQ

		return blocageRepository.save(blocageToAnnuler.get());
	}

}
