package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.criteria.EchangeCriteria;
import com.microselbourse.dao.IBlocageRepository;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.specs.EchangeSpecification;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutBlocage;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumTradeType;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.exceptions.DeniedAccessException;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IPropositionService;
import com.microselbourse.service.IReponseService;
import com.microselbourse.service.ITransactionService;
import com.microselbourse.service.IWalletService;

@Service
public class EchangeServiceImpl implements IEchangeService{
	
	@Autowired
	private IReponseRepository reponseRepository;
	
	@Autowired
	private IEchangeRepository echangeRepository;
	
	@Autowired
	private IMicroselAdherentsProxy adherentsProxy;
	
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
	
	
	@Override
	public Echange createEchange(long id) throws EntityAlreadyExistsException, EntityNotFoundException {
		Optional<Echange> echangeAlreadyCreated = echangeRepository.findById(id);
				if(!echangeAlreadyCreated.isEmpty())
					throw new EntityAlreadyExistsException("Un échange existe déjà pour cette réponse à cette proposition");
				
		Optional<Reponse> reponse = reponseRepository.findById(id);
			if(reponse.isEmpty())
				throw new EntityNotFoundException("Un échange ne peut pas être créé s'il n'existe pas déjà une réponse à une proposition");
				
		Echange echangeToCreate = new Echange();
		
		echangeToCreate.setId(reponse.get().getId());
		echangeToCreate.setDateEnregistrement(LocalDate.now());
		echangeToCreate.setDateEcheance(reponse.get().getDateEcheance());
		echangeToCreate.setStatutEchange(EnumStatutEchange.ENREGISTRE);
		echangeToCreate.setTitre(reponse.get().getTitre());
		
		echangeToCreate.setEmetteurId(reponse.get().getProposition().getEmetteurId());	
		echangeToCreate.setRecepteurId(reponse.get().getRecepteurId());
		
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(reponse.get().getProposition().getEmetteurId());
		echangeToCreate.setEmetteurUsername(emetteur.getUsername());
		echangeToCreate.setEmetteurMail(emetteur.getEmail());
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(reponse.get().getRecepteurId());
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
		  
			/*
			 * List<Echange> echangesList = echanges.getContent(); for(Echange
			 * echangeToCheck : echangesList) { this.solderEchange(echangeToCheck.getId());
			 * }
			 */
		  return echanges;
	}
	

	@Override
	public Echange readEchange(Long id) throws EntityNotFoundException {
		
		Optional<Echange> echangeToRead = echangeRepository.findById(id);
			if(echangeToRead.isEmpty())
				throw new EntityNotFoundException("L'échange que vous voulez consulter n'existe pas");
			
			if(echangeToRead.get().getStatutEchange().equals(EnumStatutEchange.ENREGISTRE) 
					&& echangeToRead.get().getDateEcheance().isBefore(LocalDate.now()))
				{
					echangeToRead.get().setStatutEchange(EnumStatutEchange.ECHU);
					return echangeRepository.save(echangeToRead.get());
				}
			return echangeToRead.get();

	}

	@Override
	public Echange refuserEchangeEmetteur(@Valid Long id) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		
		Echange echangeToValidate = this.readEchange(id);
		
		if(!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
		
		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageEmetteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être refusé par l'émetteur : il existe un blocage encours concernant l'émetteur de la proposition.");
		
		
		//HYPOTHESE 3 : REFUS VALIDATION EMETTEUR
			echangeToValidate.setAvisEmetteur(EnumEchangeAvis.REFUSE);
			mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Refus de valider l'echange par l'emetteur", "07_EmetteurProposition_EchangeRefus");
			//On vérifie si le récepteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
				
		   //HYPOTHESE 3A : REFUS EMETTEUR / RECEPTEUR DEJA VALIDE
				if(echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					//mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05B_EmetteurProposition_EchangeValidation_Litige");
					//mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05B_RecepteurReponse_EchangeValidation_Litige");
					
					MessageMailEchange messageToRecepteur = new MessageMailEchange();
					messageToRecepteur.setEchange(echangeToValidate);
					messageToRecepteur.setDestinataire(recepteur);
					messageToRecepteur.setSubject("Cloture de l'echange");
					messageToRecepteur.setMicroselBourseMailTemplate("05B_EmetteurProposition_EchangeValidation_Litige");
					rabbitMQSender.sendMessageMailEchange(messageToRecepteur);
					
					MessageMailEchange messageToEmetteur = new MessageMailEchange();
					messageToEmetteur.setEchange(echangeToValidate);
					messageToEmetteur.setDestinataire(emetteur);
					messageToEmetteur.setSubject("Cloture de l'echange");
					messageToEmetteur.setMicroselBourseMailTemplate("05B_RecepteurReponse_EchangeValidation_Litige");
					rabbitMQSender.sendMessageMailEchange(messageToEmetteur);
					
					
					
					
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else if (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)){
					
		  //HYPOTHESE 3B : REFUS EMETTEUR / RECEPTEUR DEJA REFUSE
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurReponse_EchangeValidation_Litige");
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else {
					
		  //HYPOTHESE 3C : REFUS EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE L'ECHANGE reste CONFIRME
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange relance pour avis", "09_Relance_pour_avis");
					return echangeRepository.save(echangeToValidate);
				}
		}
		
	
	@Override
	public Echange refuserEchangeRecepteur(@Valid Long id) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		
		Echange echangeToValidate = this.readEchange(id);
		
		if(!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
		
		Optional<Blocage> blocageRecepteurId = blocageRepository.findByAdherentIdAndStatutBlocage(recepteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageRecepteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être refusé par le récepteur : il existe un blocage encours concernant le récepteur de la proposition.");
		
		
		//HYPOTHESE 4 : REFUS VALIDATION RECEPTEUR 	
			echangeToValidate.setAvisRecepteur(EnumEchangeAvis.REFUSE);
			mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Refus de valider l'echange par le Recepteur", "08_RecepteurReponse_EchangeRefus");
			//On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
				
		//HYPOTHESE 4A : REFUS RECEPTEUR / EMETTEUR DEJA VALIDE
				if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05B_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05B_RecepteurReponse_EchangeValidation_Litige");
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
					
		//HYPOTHESE 4B : REFUS RECEPTEUR / EMETTEUR DEJA REFUSE
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurReponse_EchangeValidation_Litige");
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else {
					
		//HYPOTHESE 4C : REFUS RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE L'ECHANGE reste CONFIRME
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange relance pour avis", "09_Relance_pour_avis");
					return echangeRepository.save(echangeToValidate);
				}
		}
		
	
	@Override
	public Echange validerEchangeEmetteur(@Valid Long id) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		Echange echangeToValidate = this.readEchange(id);
		
		if(!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
		
		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageEmetteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être accepté par l'émetteur : il existe un blocage encours concernant l'émetteur de la proposition.");
		
				
			//HYPOTHESE 1 : AVIS VALIDATION EMETTEUR
					echangeToValidate.setAvisEmetteur(EnumEchangeAvis.VALIDE);
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Validation de l'echange par l'emetteur", "05_EmetteurProposition_EchangeValidation");
					//On vérifie si le récepteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
					
					//HYPOTHESE 1A : VALIDATION EMETTEUR / RECEPTEUR DEJA VALIDE
						if(echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
							echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
							echangeToValidate.setDateFin(LocalDate.now());
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Cloture");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Cloture");
							Transaction transactionCreated = transactionService.createTransaction(id);
							echangeToValidate.setTransaction(transactionCreated);
							return echangeRepository.save(echangeToValidate);
						}else if (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)){
					
					//HYPOTHESE 1B : VALIDATION EMETTEUR / RECEPTEUR DEJA REFUSE
							echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
							echangeToValidate.setDateFin(LocalDate.now());
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurReponse_EchangeValidation_Litige");
							Transaction transactionCreated = transactionService.createTransaction(id);
							echangeToValidate.setTransaction(transactionCreated);
							return echangeRepository.save(echangeToValidate);
						}else {
							
					//HYPOTHESE 1C : VALIDATION EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE L'ECHANGE reste CONFIRME 
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange relance pour avis", "09_Relance_pour_avis");
							return echangeRepository.save(echangeToValidate);
						}
				
			}	
	
	@Override
	public Echange validerEchangeRecepteur(@Valid Long id) throws EntityNotFoundException, DeniedAccessException, UnsupportedEncodingException, MessagingException, EntityAlreadyExistsException {
		Echange echangeToValidate = this.readEchange(id);
		
		if(!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
		
		Optional<Blocage> blocageRecepteurId = blocageRepository.findByAdherentIdAndStatutBlocage(recepteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageRecepteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être validé par le récepteur : il existe un blocage encours concernant le récepteur de la proposition.");
		
				
			//HYPOTHESE 2 : ACCORD VALIDATION RECEPTEUR
					echangeToValidate.setAvisRecepteur(EnumEchangeAvis.VALIDE);
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Validation de l'echange par le Recepteur", "06_RecepteurReponse_EchangeValidation");
					//On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
						
					//HYPOTHESE 1A : VALIDATION RECEPTEUR / EMETTEUR DEJA VALIDE
						if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
							echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
							echangeToValidate.setDateFin(LocalDate.now());
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Cloture");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Cloture");
							Transaction transactionCreated = transactionService.createTransaction(id);
							echangeToValidate.setTransaction(transactionCreated);
							return echangeRepository.save(echangeToValidate);
						}else if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
							
					//HYPOTHESE 1B : VALIDATION RECEPTEUR / RECEPTEUR DEJA REFUSE
							echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
							echangeToValidate.setDateFin(LocalDate.now());
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurReponse_EchangeValidation_Litige");
							Transaction transactionCreated = transactionService.createTransaction(id);
							echangeToValidate.setTransaction(transactionCreated);
							return echangeRepository.save(echangeToValidate);
						}else {
							
					//HYPOTHESE 1C : VALIDATION RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE L'ECHANGE reste CONFIRME
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange relance pour avis", "09_Relance_pour_avis");
							return echangeRepository.save(echangeToValidate);
						}
			}	
	

	@Override
	public Echange annulerEchange(@Valid Long id) throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException {
		Echange echangeToAnnuler = this.readEchange(id);
		
		if(!echangeToAnnuler.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de demander son annulation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToAnnuler.getRecepteurId());
		
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToAnnuler.getEmetteurId());
		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageEmetteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être annulé par l'émetteur : il existe un blocage encours concernant l'émmeteur de la proposition.");
		
		
		echangeToAnnuler.setStatutEchange(EnumStatutEchange.ANNULE);
		echangeToAnnuler.setDateAnnulation(LocalDate.now());
		//mailSender.sendMailEchangeConfirmation(echangeToAnnuler, recepteur, "Annulation de l'echange", "04_RecepteurReponse_EchangeAnnulation");
		
		MessageMailEchange messageToRecepteur = new MessageMailEchange();
		messageToRecepteur.setEchange(echangeToAnnuler);
		messageToRecepteur.setDestinataire(recepteur);
		messageToRecepteur.setSubject("Annulation de l'echange");
		messageToRecepteur.setMicroselBourseMailTemplate("04_RecepteurReponse_EchangeAnnulation");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteur);
		
		
		return echangeRepository.save(echangeToAnnuler);	
	}


	@Override
	public Echange confirmerEchange(@Valid Long id) throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException {
		Echange echangeToConfirm = this.readEchange(id);
		
		if(!echangeToConfirm.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre confirmation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToConfirm.getRecepteurId());
		
		UserBean emetteur = adherentsProxy.consulterCompteAdherent(echangeToConfirm.getEmetteurId());
		Optional<Blocage> blocageEmetteurId = blocageRepository.findByAdherentIdAndStatutBlocage(emetteur.getId(), EnumStatutBlocage.ENCOURS);
		if(blocageEmetteurId.isPresent())
			throw new DeniedAccessException("L'échange ne peut pas être confirmé par l'émetteur : il existe un blocage encours concernant l'émmeteur de la proposition.");
		
		echangeToConfirm.setStatutEchange(EnumStatutEchange.CONFIRME);
		echangeToConfirm.setDateConfirmation(LocalDate.now());
		//mailSender.sendMailEchangeConfirmation(echangeToConfirm, recepteur, "Confirmation de l'echange", "03_RecepteurReponse_EchangeConfirmation");
		MessageMailEchange messageToRecepteur = new MessageMailEchange();
		messageToRecepteur.setEchange(echangeToConfirm);
		messageToRecepteur.setDestinataire(recepteur);
		messageToRecepteur.setSubject("Confirmation de l'echange");
		messageToRecepteur.setMicroselBourseMailTemplate("03_RecepteurReponse_EchangeConfirmation");
		rabbitMQSender.sendMessageMailEchange(messageToRecepteur);
		
		
		return echangeRepository.save(echangeToConfirm);
	
		
	}


	@Override
	public List<Echange> searchAndUpdateEchangesASupprimer() {
		List<Echange> echangesASupprimerList = new ArrayList<Echange>();
		
		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAnd2AvisEchangeSans = echangeRepository.findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange (
				LocalDate.now(),
				EnumStatutEchange.CONFIRME, 
				EnumEchangeAvis.SANS, 
				EnumEchangeAvis.SANS
				);
		
		if(echangesDateEcheanceBeforeNowAndStatutConfirmeAnd2AvisEchangeSans.isPresent()) {
			for(Echange echangeASupprimer : echangesDateEcheanceBeforeNowAndStatutConfirmeAnd2AvisEchangeSans.get()) {
					  echangeASupprimer.setStatutEchange(EnumStatutEchange.SUPPRIME);
					  echangeASupprimer.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
					  echangeASupprimer.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
					  echangesASupprimerList.add(echangeASupprimer);
					  echangeRepository.save(echangeASupprimer);
				  }	 
			}
		return echangesASupprimerList;
	}


	@Override
	public List<Echange> searchAndUpdateEchangesAForceValider() {
		List<Echange> echangesAForceValiderList = new ArrayList<Echange>();
		
		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide = echangeRepository.findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange (
				LocalDate.now(),
				EnumStatutEchange.CONFIRME, 
				EnumEchangeAvis.VALIDE, 
				EnumEchangeAvis.SANS
				);
		
		if(echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide.isPresent()) {
			for(Echange echangeAForceValider : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide.get()) {
					  echangeAForceValider.setStatutEchange(EnumStatutEchange.FORCEVALID);
					  echangeAForceValider.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
					  echangesAForceValiderList.add(echangeAForceValider);
					  echangeRepository.save(echangeAForceValider);
				  }	 
			}
		
		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurValide = echangeRepository.findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange (
				LocalDate.now(),
				EnumStatutEchange.CONFIRME, 
				EnumEchangeAvis.SANS, 
				EnumEchangeAvis.VALIDE
				);
		
		if(echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurValide.isPresent()) {
			for(Echange echangeAForceValider : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurValide.get()) {
					  echangeAForceValider.setStatutEchange(EnumStatutEchange.FORCEVALID);
					  echangeAForceValider.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
					  echangesAForceValiderList.add(echangeAForceValider);
					  echangeRepository.save(echangeAForceValider);
				  }	 
			}
		
		return echangesAForceValiderList;
	}


	@Override
	public List<Echange> searchAndUpdateEchangesAForceRefuser() {
		List<Echange> echangesAForceRefuserList = new ArrayList<Echange>();
		
		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse = echangeRepository.findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange (
				LocalDate.now(),
				EnumStatutEchange.CONFIRME, 
				EnumEchangeAvis.REFUSE, 
				EnumEchangeAvis.SANS
				);
		
		if(echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse.isPresent()) {
			for(Echange echangeAForceRefuser : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse.get()) {
					  echangeAForceRefuser.setStatutEchange(EnumStatutEchange.FORCEREFUS);
					  echangeAForceRefuser.setAvisRecepteur(EnumEchangeAvis.ANOMALIE);
					  echangesAForceRefuserList.add(echangeAForceRefuser);
					  echangeRepository.save(echangeAForceRefuser);
				  }	 
			}
		
		Optional<List<Echange>> echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurRefuse = echangeRepository.findByDateEcheanceBeforeThisDateAndStatutEchangeConfirmeAnd2AvisEchange (
				LocalDate.now(),
				EnumStatutEchange.CONFIRME, 
				EnumEchangeAvis.SANS, 
				EnumEchangeAvis.REFUSE
				);
		
		if(echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisRecepteurRefuse.isPresent()) {
			for(Echange echangeAForceRefuser : echangesDateEcheanceBeforeNowAndStatutConfirmeAndAvisEmetteurRefuse.get()) {
					  echangeAForceRefuser.setStatutEchange(EnumStatutEchange.FORCEREFUS);
					  echangeAForceRefuser.setAvisEmetteur(EnumEchangeAvis.ANOMALIE);
					  echangesAForceRefuserList.add(echangeAForceRefuser);
					  echangeRepository.save(echangeAForceRefuser);
				  }	 
			}
		
		return echangesAForceRefuserList;
	}
	
	
	


	
	}
	
	

	
	
	
	
	


