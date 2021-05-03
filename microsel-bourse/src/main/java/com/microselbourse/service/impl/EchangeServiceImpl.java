package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.specs.EchangeSpecification;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselUsersProxy;
import com.microselbourse.service.IBlocageService;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.ITransactionService;
import com.microselbourse.service.IWalletService;

@Service
@Transactional
public class EchangeServiceImpl implements IEchangeService {

	
	@Autowired
	private IReponseRepository reponseRepository;

	@Autowired
	private IEchangeRepository echangeRepository;

	@Autowired
	private IMicroselUsersProxy usersProxy;

	@Autowired
	private IMailSenderService mailSender;

	@Autowired
	private ITransactionService transactionService;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IBlocageRepository blocageRepository;

	@Autowired
	RabbitMQSender rabbitMQSender;

	@Autowired
	private IBlocageService blocageService;

	@Value("${application.dateTimezone}")
	private Integer dateTimezone;

	@Value("${application.delaiMin}")
	private Integer delaiMin;

	@Value("${application.delaiMax}")
	private Integer delaiMax;

	@Override
	public Echange createEchange(long id) throws EntityAlreadyExistsException, EntityNotFoundException {
		Optional<Echange> echangeAlreadyCreated = echangeRepository.findById(id);
		if (!echangeAlreadyCreated.isEmpty())
			throw new EntityAlreadyExistsException("Un échange existe déjà pour cette réponse à cette proposition");

		Optional<Reponse> reponse = reponseRepository.findById(id);
		if (reponse.isEmpty())
			throw new EntityNotFoundException(
					"Un échange ne peut pas être créé s'il n'existe pas déjà une réponse à une proposition");

		Echange echangeToCreate = new Echange();

		echangeToCreate.setId(reponse.get().getId());
		echangeToCreate.setDateEnregistrement(LocalDate.now().plusDays(dateTimezone));
		System.out.println("dateEnregistrement" + LocalDate.now());
		echangeToCreate.setDateEcheance(reponse.get().getDateEcheance().plusDays(dateTimezone));
		echangeToCreate.setStatutEchange(EnumStatutEchange.ENREGISTRE);
		echangeToCreate.setTitre(reponse.get().getTitre());

		echangeToCreate.setEmetteurId(reponse.get().getProposition().getEmetteurId());
		echangeToCreate.setRecepteurId(reponse.get().getRecepteurId());

		UserBean emetteur = usersProxy.consulterCompteAdherent(reponse.get().getProposition().getEmetteurId());
		echangeToCreate.setEmetteurUsername(emetteur.getUsername());
		echangeToCreate.setEmetteurMail(emetteur.getEmail());

		UserBean recepteur = usersProxy.consulterCompteAdherent(reponse.get().getRecepteurId());
		echangeToCreate.setRecepteurUsername(recepteur.getUsername());
		echangeToCreate.setRecepteurMail(recepteur.getEmail());

		echangeToCreate.setAvisEmetteur(EnumEchangeAvis.SANS);
		echangeToCreate.setAvisRecepteur(EnumEchangeAvis.SANS);

		return echangeRepository.save(echangeToCreate);

	}

	@Override
	public Page<Echange> searchAllEchangesByCriteria(EchangeCriteria echangeCriteria, Pageable pageable) {
		Specification<Echange> echangeSpecification = new EchangeSpecification(echangeCriteria);
		Page<Echange> echanges = echangeRepository.findAll(echangeSpecification, pageable);

		return echanges;
	}

	@Override
	public Echange readEchange(Long id) throws EntityNotFoundException {

		Optional<Echange> echangeToRead = echangeRepository.findById(id);
		if (echangeToRead.isEmpty())
			throw new EntityNotFoundException("L'échange que vous voulez consulter n'existe pas");

		if (echangeToRead.get().getStatutEchange().equals(EnumStatutEchange.ENREGISTRE)
				&& echangeToRead.get().getDateEcheance().isBefore(LocalDate.now())) {
			echangeToRead.get().setStatutEchange(EnumStatutEchange.ECHU);
			return echangeRepository.save(echangeToRead.get());
		}
		return echangeToRead.get();

	}

	@Override
	public Echange refuserEchange(@Valid Long id, String intervenantId) throws EntityNotFoundException,
			DeniedAccessException, UnsupportedEncodingException, EntityAlreadyExistsException, MessagingException {

		Echange echangeToRefuse = this.readEchange(id);

		if (!echangeToRefuse.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de le refuser");

		if (!intervenantId.equals(echangeToRefuse.getEmetteurId())
				&& !intervenantId.equals(echangeToRefuse.getRecepteurId()))
			throw new DeniedAccessException("Seul un participant à l'échange peut le refuser");

		Optional<Blocage> blocageIntervenantId = blocageRepository.findByAdherentIdAndStatutBlocage(intervenantId,
				EnumStatutBlocage.ENCOURS);
		if (blocageIntervenantId.isPresent())
			throw new DeniedAccessException(
					"La réponse ne peut pas être créée : il existe un blocage encours concernant l'adherent récepteur.");

		UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToRefuse.getEmetteurId());
		UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToRefuse.getRecepteurId());

		Echange echangeRefused = new Echange();

		if (intervenantId.equals(echangeToRefuse.getEmetteurId())) {

			if (echangeToRefuse.getAvisEmetteur().ordinal() != 2)
				throw new DeniedAccessException(
						"Votre refus ne peut pas être enregistré = vous avez déjà refusé cet échange");

			echangeRefused = this.refuserEchangeEmetteur(id, echangeToRefuse, emetteur, recepteur);

		}

		if (intervenantId.equals(echangeToRefuse.getRecepteurId())) {

			if (echangeToRefuse.getAvisRecepteur().ordinal() != 2)
				throw new DeniedAccessException(
						"Votre refus ne peut pas être enregistré = vous avez déjà refusé cet échange");

			echangeRefused = this.refuserEchangeRecepteur(id, echangeToRefuse, emetteur, recepteur);

		}

		return echangeRefused;

	}

	@Override
	public Echange refuserEchangeEmetteur(@Valid Long id, Echange echangeToRefuse, UserBean emetteur,
			UserBean recepteur) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException,
			MessagingException, EntityAlreadyExistsException {

		// HYPOTHESE 3 : REFUS VALIDATION EMETTEUR
		echangeToRefuse.setAvisEmetteur(EnumEchangeAvis.REFUSE);

		MessageMailEchange messageToRecepteurH3 = new MessageMailEchange();
		messageToRecepteurH3.setEchange(echangeToRefuse);
		messageToRecepteurH3.setDestinataire(recepteur);
		messageToRecepteurH3.setSubject("Refus de valider l'echange par l'emetteur");
		messageToRecepteurH3.setMicroselBourseMailTemplate("07_EmetteurProposition_EchangeRefus");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteurH3);// ---------------------------------------------------------->RMQ

		// On vérifie si le récepteur a déjà donné un avis et si OUI lequel pour faire
		// évoluer le statut de l'échange

		// HYPOTHESE 3A : REFUS EMETTEUR / RECEPTEUR DEJA VALIDE
		if (echangeToRefuse.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
			echangeToRefuse.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToRefuse.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToRefuse);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange en litige");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);// ------------------------------------------------------------->RmQ

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToRefuse);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange en litige");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);// --------------------------------------------------------------->RMQ

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToRefuse.setTransaction(transactionCreated);

			return echangeRepository.save(echangeToRefuse);

		} else if (echangeToRefuse.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)) {

			// HYPOTHESE 3B : REFUS EMETTEUR / RECEPTEUR DEJA REFUSE
			echangeToRefuse.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToRefuse.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToRefuse);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange en litige");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToRefuse);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange en litige");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToRefuse.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToRefuse);
		} else {

			// HYPOTHESE 3C : REFUS EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE L'ECHANGE
			// reste CONFIRME

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToRefuse);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange relance pour avis");
			messageToRecepteur.setMicroselBourseMailTemplate("09_Relance_pour_avis");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			return echangeRepository.save(echangeToRefuse);
		}
	}

	@Override
	public Echange refuserEchangeRecepteur(@Valid Long id, Echange echangeToRefuse, UserBean emetteur,
			UserBean recepteur) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException,
			MessagingException, EntityAlreadyExistsException {

		// HYPOTHESE 4 : REFUS VALIDATION RECEPTEUR
		echangeToRefuse.setAvisRecepteur(EnumEchangeAvis.REFUSE);

		MessageMailEchange messageToEmetteurH4 = new MessageMailEchange();
		messageToEmetteurH4.setEchange(echangeToRefuse);
		messageToEmetteurH4.setDestinataire(emetteur);
		messageToEmetteurH4.setSubject("Refus de valider l'echange par le Recepteur");
		messageToEmetteurH4.setMicroselBourseMailTemplate("08_RecepteurReponse_EchangeRefus");
		rabbitMQSender.sendMessageMailEchange(messageToEmetteurH4);

		// On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire
		// évoluer le statut de l'échange

		// HYPOTHESE 4A : REFUS RECEPTEUR / EMETTEUR DEJA VALIDE
		if (echangeToRefuse.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
			echangeToRefuse.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToRefuse.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToRefuse);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Cloture de l'echange");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToRefuse);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Cloture de l'echange");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToRefuse.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToRefuse);
		} else if (echangeToRefuse.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)) {

			// HYPOTHESE 4B : REFUS RECEPTEUR / EMETTEUR DEJA REFUSE
			echangeToRefuse.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToRefuse.setDateFin(LocalDate.now());

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToRefuse);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange en litige");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToRefuse);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange en litige");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToRefuse.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToRefuse);
		} else {

			// HYPOTHESE 4C : REFUS RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE L'ECHANGE
			// reste CONFIRME

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToRefuse);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange relance pour avis");
			messageToEmetteur.setMicroselBourseMailTemplate("09_Relance_pour_avis");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			return echangeRepository.save(echangeToRefuse);

		}
	}

	@Override
	public Echange validerEchange(@Valid Long id, String intervenantId) throws EntityNotFoundException,
			DeniedAccessException, UnsupportedEncodingException, EntityAlreadyExistsException, MessagingException {

		Echange echangeToValidate = this.readEchange(id);
		if (!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation");

		if (!intervenantId.equals(echangeToValidate.getEmetteurId())
				&& !intervenantId.equals(echangeToValidate.getRecepteurId()))
			throw new DeniedAccessException("Seul un participant à l'échange peut le valider");

		Optional<Blocage> blocageIntervenantId = blocageRepository.findByAdherentIdAndStatutBlocage(intervenantId,
				EnumStatutBlocage.ENCOURS);
		if (blocageIntervenantId.isPresent())
			throw new DeniedAccessException(
					"La réponse ne peut pas être créée : il existe un blocage encours concernant l'adherent récepteur.");

		UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
		UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());

		Echange echangeValidated = new Echange();

		if (intervenantId.equals(echangeToValidate.getEmetteurId())) {

			if (echangeToValidate.getAvisEmetteur().ordinal() != 2)
				throw new DeniedAccessException(
						"Votre validation ne peut pas être enregistrée = vous avez déjà validé cet échange");

			echangeValidated = this.validerEchangeEmetteur(id, echangeToValidate, emetteur, recepteur);

		}

		if (intervenantId.equals(echangeToValidate.getRecepteurId())) {

			if (echangeToValidate.getAvisRecepteur().ordinal() != 2)
				throw new DeniedAccessException(
						"Votre validation ne peut pas être enregistrée = vous avez déjà validé cet échange");

			echangeValidated = this.validerEchangeRecepteur(id, echangeToValidate, emetteur, recepteur);

		}

		return echangeValidated;

	}

	@Override
	public Echange validerEchangeEmetteur(@Valid Long id, Echange echangeToValidate, UserBean emetteur,
			UserBean recepteur) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException,
			MessagingException, EntityAlreadyExistsException {

		// HYPOTHESE 1 : AVIS VALIDATION EMETTEUR
		echangeToValidate.setAvisEmetteur(EnumEchangeAvis.VALIDE);

		MessageMailEchange messageToRecepteurH1 = new MessageMailEchange();
		messageToRecepteurH1.setEchange(echangeToValidate);
		messageToRecepteurH1.setDestinataire(recepteur);
		messageToRecepteurH1.setSubject("Validation de l'echange par l'emetteur");
		messageToRecepteurH1.setMicroselBourseMailTemplate("05_EmetteurProposition_EchangeValidation");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteurH1);

		// On vérifie si le récepteur a déjà donné un avis et si OUI lequel pour faire
		// évoluer le statut de l'échange

		// HYPOTHESE 1A : VALIDATION EMETTEUR / RECEPTEUR DEJA VALIDE
		if (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
			echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
			echangeToValidate.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToValidate);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Cloture de l'echange");
			messageToRecepteur.setMicroselBourseMailTemplate("05A_EmetteurProposition_EchangeValidation_Cloture");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToValidate);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Cloture de l'echange");
			messageToEmetteur.setMicroselBourseMailTemplate("05A_RecepteurReponse_EchangeValidation_Cloture");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToValidate.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToValidate);
		} else if (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)) {

			// HYPOTHESE 1B : VALIDATION EMETTEUR / RECEPTEUR DEJA REFUSE
			echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToValidate.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToValidate);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange en litige");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToValidate);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange en litige");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToValidate.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToValidate);
		} else {

			// HYPOTHESE 1C : VALIDATION EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE
			// L'ECHANGE reste CONFIRME

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToValidate);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange relance pour avis");
			messageToRecepteur.setMicroselBourseMailTemplate("09_Relance_pour_avis");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			return echangeRepository.save(echangeToValidate);
		}

	}

	@Override
	public Echange validerEchangeRecepteur(@Valid Long id, Echange echangeToValidate, UserBean emetteur,
			UserBean recepteur) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException,
			MessagingException, EntityAlreadyExistsException {

		// HYPOTHESE 2 : ACCORD VALIDATION RECEPTEUR
		echangeToValidate.setAvisRecepteur(EnumEchangeAvis.VALIDE);

		MessageMailEchange messageToEmetteurH2 = new MessageMailEchange();
		messageToEmetteurH2.setEchange(echangeToValidate);
		messageToEmetteurH2.setDestinataire(emetteur);
		messageToEmetteurH2.setSubject("Validation de l'echange par le Recepteur");
		messageToEmetteurH2.setMicroselBourseMailTemplate("06_RecepteurReponse_EchangeValidation");
		rabbitMQSender.sendMessageMailEchange(messageToEmetteurH2);

		// On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire
		// évoluer le statut de l'échange

		// HYPOTHESE 1A : VALIDATION RECEPTEUR / EMETTEUR DEJA VALIDE
		if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
			echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
			echangeToValidate.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToValidate);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Cloture de l'echange");
			messageToRecepteur.setMicroselBourseMailTemplate("05A_EmetteurProposition_EchangeValidation_Cloture");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToValidate);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Cloture de l'echange");
			messageToEmetteur.setMicroselBourseMailTemplate("05A_EmetteurProposition_EchangeValidation_Cloture");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToValidate.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToValidate);

		} else if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)) {

			// HYPOTHESE 1B : VALIDATION RECEPTEUR / RECEPTEUR DEJA REFUSE
			echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
			echangeToValidate.setDateFin(LocalDate.now().plusDays(dateTimezone));

			MessageMailEchange messageToRecepteur = new MessageMailEchange();
			messageToRecepteur.setEchange(echangeToValidate);
			messageToRecepteur.setDestinataire(recepteur);
			messageToRecepteur.setSubject("Echange en litige");
			messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToValidate);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange en litige");
			messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			Transaction transactionCreated = transactionService.createTransaction(id);
			echangeToValidate.setTransaction(transactionCreated);
			return echangeRepository.save(echangeToValidate);
		} else {

			// HYPOTHESE 1C : VALIDATION RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE
			// L'ECHANGE reste CONFIRME

			MessageMailEchange messageToEmetteur = new MessageMailEchange();
			messageToEmetteur.setEchange(echangeToValidate);
			messageToEmetteur.setDestinataire(emetteur);
			messageToEmetteur.setSubject("Echange relance pour avis");
			messageToEmetteur.setMicroselBourseMailTemplate("09_Relance_pour_avis");
			rabbitMQSender.sendMessageMailEchange(messageToEmetteur);

			return echangeRepository.save(echangeToValidate);
		}
	}

	@Override
	public Echange annulerEchange(@Valid Long id, String intervenantId)
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException {
		Echange echangeToAnnuler = this.readEchange(id);

		if (!echangeToAnnuler.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de demander son annulation");

		UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToAnnuler.getRecepteurId());

		UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToAnnuler.getEmetteurId());
		if (!intervenantId.equals(echangeToAnnuler.getEmetteurId()))
			throw new DeniedAccessException("Seul l'émetteur d'une proposition d'échange peut l'annuler");

		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(),
				EnumStatutBlocage.ENCOURS);
		if (blocageEmetteurId.isPresent())
			throw new DeniedAccessException(
					"L'échange ne peut pas être annulé par l'émetteur : il existe un blocage encours concernant l'émmeteur de la proposition.");

		echangeToAnnuler.setStatutEchange(EnumStatutEchange.ANNULE);
		echangeToAnnuler.setDateAnnulation(LocalDate.now().plusDays(dateTimezone));

		MessageMailEchange messageToRecepteur = new MessageMailEchange();
		messageToRecepteur.setEchange(echangeToAnnuler);
		messageToRecepteur.setDestinataire(recepteur);
		messageToRecepteur.setSubject("Annulation de l'echange");
		messageToRecepteur.setMicroselBourseMailTemplate("04_RecepteurReponse_EchangeAnnulation");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

		return echangeRepository.save(echangeToAnnuler);
	}

	@Override
	public Echange confirmerEchange(@Valid Long id, String intervenantId)
			throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException {
		Echange echangeToConfirm = this.readEchange(id);

		if (!echangeToConfirm.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre confirmation");

		UserBean recepteur = usersProxy.consulterCompteAdherent(echangeToConfirm.getRecepteurId());

		UserBean emetteur = usersProxy.consulterCompteAdherent(echangeToConfirm.getEmetteurId());
		if (!intervenantId.equals(echangeToConfirm.getEmetteurId()))
			throw new DeniedAccessException("Seul l'émetteur d'une proposition d'échange peut la confirmer");

		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(),
				EnumStatutBlocage.ENCOURS);
		if (blocageEmetteurId.isPresent())
			throw new DeniedAccessException(
					"L'échange ne peut pas être confirmé par l'émetteur : il existe un blocage encours concernant l'émmeteur de la proposition.");

		echangeToConfirm.setStatutEchange(EnumStatutEchange.CONFIRME);
		echangeToConfirm.setDateConfirmation(LocalDate.now().plusDays(dateTimezone));

		MessageMailEchange messageToRecepteur = new MessageMailEchange();
		messageToRecepteur.setEchange(echangeToConfirm);
		messageToRecepteur.setDestinataire(recepteur);
		messageToRecepteur.setSubject("Confirmation de l'echange");
		messageToRecepteur.setMicroselBourseMailTemplate("03_RecepteurReponse_EchangeConfirmation");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteur);

		return echangeRepository.save(echangeToConfirm);

	}

	/**
	 * Si la réalisation de l’échange n’est pas établie par au moins 1 avis
	 * (c’est-à-dire que le statut de l’échange est toujours en CONFIRME et que
	 * l’avis des 2 adhérents n’est pas renseigné), le système : • Passe le statut
	 * de l’échange en SUPPRIME • Passe le statut de l’avis des 2 adhérents en
	 * ANOMALIE mais ne bloque pas leur accès à la bourse d’échange • Aucun
	 * enregistrement de la transaction en unités de compte au débit ou au crédit de
	 * l’émetteur et du récepteur Pour éviter cette situation à cause d’un « oubli»,
	 * le système envoie un mail de rappel 48 heures avant la date d’échéance.
	 * 
	 * @return
	 */
	@Override
	public List<Echange> searchAndUpdateEchangesASupprimer() {
		List<Echange> echangesASupprimerList = new ArrayList<Echange>();

		LocalDate dateNow = LocalDate.now();

		Optional<List<Echange>> echangesDateEcheanceBetween2DatesAndStatutConfirmeAnd2AvisEchangeSans = echangeRepository
				.findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(dateNow.minusDays(delaiMin),
						dateNow.plusDays(delaiMax), EnumStatutEchange.CONFIRME, EnumEchangeAvis.SANS,
						EnumEchangeAvis.SANS);

		if (echangesDateEcheanceBetween2DatesAndStatutConfirmeAnd2AvisEchangeSans.isPresent()) {
			for (Echange echangeASupprimer : echangesDateEcheanceBetween2DatesAndStatutConfirmeAnd2AvisEchangeSans
					.get()) {
				if (echangeASupprimer.getDateEcheance().isBefore(LocalDate.now())) {
					echangeASupprimer.setStatutEchange(EnumStatutEchange.SUPPRIME);
					echangeASupprimer.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
					echangeASupprimer.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
					echangesASupprimerList.add(echangeASupprimer);
					echangeRepository.save(echangeASupprimer);
				}
			}
		}
		return echangesASupprimerList;
	}

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis VALIDE sur
	 * l’échange, le système : • Considère que l’échange est réputé « validé » et
	 * passe son statut en FORCEVALID • Enregistre la transaction en unités de
	 * compte au débit ou au crédit de l’émetteur et du récepteur qui a validé
	 * l’échange. La contrepartie est le compte interne COUNTERPART • Bloque l’accès
	 * à l’espace personnel de l’autre adhérent, passe son avis en ANOMALIE (=
	 * silencieux) et lui envoie un mail Lorsque le système bloque l’accès d’un
	 * adhérent à son espace personnel, il passe toutes les PROPOSITIONS et toutes
	 * les REPONSES de cet adhérent dans la bourse d’échanges en statut BLOQUE
	 * 
	 * @return
	 * @throws EntityAlreadyExistsException
	 * @throws EntityNotFoundException
	 */
	@Override
	public List<Echange> searchAndUpdateEchangesAForceValiderRecepteur()
			throws EntityNotFoundException, EntityAlreadyExistsException {
		List<Echange> echangesAForceValiderList = new ArrayList<Echange>();

		LocalDate dateNow = LocalDate.now();

		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide = echangeRepository
				.findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(dateNow.minusDays(delaiMin),
						dateNow.plusDays(delaiMax), EnumStatutEchange.CONFIRME, EnumEchangeAvis.VALIDE,
						EnumEchangeAvis.SANS);

		if (echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide.isPresent()) {
			for (Echange echangeAForceValider : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide
					.get()) {
				echangeAForceValider.setStatutEchange(EnumStatutEchange.FORCEVALID);
				echangeAForceValider.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
				Blocage blocageCreated = blocageService.createBlocage(echangeAForceValider.getId(),
						echangeAForceValider.getRecepteurId(), echangeAForceValider.getRecepteurUsername());
				Echange echangeSaved = echangeRepository.save(echangeAForceValider);
				Transaction transactionCreated = transactionService.createTransaction(echangeSaved.getId());
				echangeSaved.setTransaction(transactionCreated);
				echangeRepository.save(echangeSaved);
				echangesAForceValiderList.add(echangeSaved);
			}
		}
		return echangesAForceValiderList;
	}

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis VALIDE sur
	 * l’échange, le système : • Considère que l’échange est réputé « validé » et
	 * passe son statut en FORCEVALID • Enregistre la transaction en unités de
	 * compte au débit ou au crédit de l’émetteur et du récepteur qui a validé
	 * l’échange. La contrepartie est le compte interne COUNTERPART • Bloque l’accès
	 * à l’espace personnel de l’autre adhérent, passe son avis en ANOMALIE (=
	 * silencieux) et lui envoie un mail Lorsque le système bloque l’accès d’un
	 * adhérent à son espace personnel, il passe toutes les PROPOSITIONS et toutes
	 * les REPONSES de cet adhérent dans la bourse d’échanges en statut BLOQUE
	 * 
	 * @return
	 * @throws EntityAlreadyExistsException
	 * @throws EntityNotFoundException
	 */
	@Override
	public List<Echange> searchAndUpdateEchangesAForceValiderEmetteur()
			throws EntityNotFoundException, EntityAlreadyExistsException {
		List<Echange> echangesAForceValiderList = new ArrayList<Echange>();

		LocalDate dateNow = LocalDate.now();

		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurValide = echangeRepository
				.findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(dateNow.minusDays(delaiMin),
						dateNow.plusDays(delaiMax), EnumStatutEchange.CONFIRME, EnumEchangeAvis.SANS,
						EnumEchangeAvis.VALIDE);

		if (echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurValide.isPresent()) {
			for (Echange echangeAForceValider : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurValide
					.get()) {
				echangeAForceValider.setStatutEchange(EnumStatutEchange.FORCEVALID);
				echangeAForceValider.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
				Blocage blocageCreated = blocageService.createBlocage(echangeAForceValider.getId(),
						echangeAForceValider.getEmetteurId(), echangeAForceValider.getEmetteurUsername());
				Echange echangeSaved = echangeRepository.save(echangeAForceValider);
				Transaction transactionCreated = transactionService.createTransaction(echangeSaved.getId());
				echangeSaved.setTransaction(transactionCreated);
				echangeRepository.save(echangeSaved);
				echangesAForceValiderList.add(echangeSaved);
			}
		}

		return echangesAForceValiderList;
	}

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis REFUSE sur
	 * l’échange, le système : • Considère que l’échange est réputé « refusé » et
	 * passe son statut en FORCEREFUS • N’enregistre aucune transaction en unités de
	 * compte au débit ou au crédit de l’émetteur et du récepteur qui a refusé
	 * l’échange. • Bloque l’accès à l’espace personnel de l’autre adhérent, passe
	 * son avis en ANOMALIE (= silencieux) et lui envoie un mail
	 * 
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public List<Echange> searchAndUpdateEchangesAForceRefuserRecepteur() throws EntityNotFoundException {
		List<Echange> echangesAForceRefuserList = new ArrayList<Echange>();

		LocalDate dateNow = LocalDate.now();

		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse = echangeRepository
				.findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(dateNow.minusDays(delaiMin),
						dateNow.plusDays(delaiMax), EnumStatutEchange.CONFIRME, EnumEchangeAvis.REFUSE,
						EnumEchangeAvis.SANS);

		if (echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse.isPresent()) {
			for (Echange echangeAForceRefuser : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse
					.get()) {
				echangeAForceRefuser.setStatutEchange(EnumStatutEchange.FORCEREFUS);
				echangeAForceRefuser.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
				Blocage blocageCreated = blocageService.createBlocage(echangeAForceRefuser.getId(),
						echangeAForceRefuser.getRecepteurId(), echangeAForceRefuser.getRecepteurUsername());
				echangeRepository.save(echangeAForceRefuser);
				echangesAForceRefuserList.add(echangeAForceRefuser);
			}
		}

		return echangesAForceRefuserList;
	}

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis REFUSE sur
	 * l’échange, le système : • Considère que l’échange est réputé « refusé » et
	 * passe son statut en FORCEREFUS • N’enregistre aucune transaction en unités de
	 * compte au débit ou au crédit de l’émetteur et du récepteur qui a refusé
	 * l’échange. • Bloque l’accès à l’espace personnel de l’autre adhérent, passe
	 * son avis en ANOMALIE (= silencieux) et lui envoie un mail
	 * 
	 * @return
	 * @throws EntityNotFoundException
	 */
	@Override
	public List<Echange> searchAndUpdateEchangesAForceRefuserEmetteur() throws EntityNotFoundException {
		List<Echange> echangesAForceRefuserList = new ArrayList<Echange>();

		LocalDate dateNow = LocalDate.now();

		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurRefuse = echangeRepository
				.findByDateEcheanceBetween2DatesAndStatutEchangeConfirmeAnd2AvisEchange(dateNow.minusDays(delaiMin),
						dateNow.plusDays(delaiMax), EnumStatutEchange.CONFIRME, EnumEchangeAvis.SANS,
						EnumEchangeAvis.REFUSE);

		if (echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurRefuse.isPresent()) {
			for (Echange echangeAForceRefuser : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurRefuse
					.get()) {
				echangeAForceRefuser.setStatutEchange(EnumStatutEchange.FORCEREFUS);
				echangeAForceRefuser.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
				Blocage blocageCreated = blocageService.createBlocage(echangeAForceRefuser.getId(),
						echangeAForceRefuser.getEmetteurId(), echangeAForceRefuser.getEmetteurUsername());
				echangeRepository.save(echangeAForceRefuser);
				echangesAForceRefuserList.add(echangeAForceRefuser);
			}
		}

		return echangesAForceRefuserList;
	}

}
