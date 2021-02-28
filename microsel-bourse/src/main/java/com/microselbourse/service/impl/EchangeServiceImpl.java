package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
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
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.dao.IReponseRepository;
import com.microselbourse.dao.specs.EchangeSpecification;
import com.microselbourse.dao.specs.PropositionSpecification;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.EnumEchangeAvis;
import com.microselbourse.entities.EnumStatutEchange;
import com.microselbourse.entities.EnumTradeType;
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
		
		//HYPOTHESE 3 : REFUS VALIDATION EMETTEUR
			echangeToValidate.setAvisEmetteur(EnumEchangeAvis.REFUSE);
			mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Refus de valider l'echange par l'emetteur", "07_EmetteurProposition_EchangeRefus");
			//On vérifie si le récepteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
				
		   //HYPOTHESE 3A : REFUS EMETTEUR / RECEPTEUR DEJA VALIDE
				if(echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					//TO DO envoi d'un mail de clôture de l'échange à l'émetteur et au récepteur
					//TO DO enregistrement d'une transaction
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Litige");
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else if (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)){
					
		  //HYPOTHESE 3B : REFUS EMETTEUR / RECEPTEUR DEJA REFUSE
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					//TO DO envoi d'un mail de litige de l'échange à l'émetteur et au récepteur
					// TO DO gestion de la transaction : pas d'enregistrement pour le récepteur qui a refusé, enregistrement pour
					// l'eémetteur qui a accepté avec contrepartie sur le compte monétaire COUNTERPART de l'association
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
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
		
		//HYPOTHESE 4 : REFUS VALIDATION RECEPTEUR 	
			echangeToValidate.setAvisRecepteur(EnumEchangeAvis.REFUSE);
			mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Refus de valider l'echange par le Recepteur", "08_RecepteurReponse_EchangeRefus");
			//On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
				
		//HYPOTHESE 4A : REFUS RECEPTEUR / EMETTEUR DEJA VALIDE
				if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Litige");
					Transaction transactionCreated = transactionService.createTransaction(id);
					echangeToValidate.setTransaction(transactionCreated);
					return echangeRepository.save(echangeToValidate);
				}else if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
					
		//HYPOTHESE 4B : REFUS RECEPTEUR / EMETTEUR DEJA REFUSE
					echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
					echangeToValidate.setDateFin(LocalDate.now());
					mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
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
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
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
				
			//HYPOTHESE 2 : ACCORD VALIDATION RECEPTEUR
					echangeToValidate.setAvisRecepteur(EnumEchangeAvis.VALIDE);
					mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Validation de l'echange par le Recepteur", "06_RecepteurReponse_EchangeValidation");
					//On vérifie si l'émetteur a déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
						
					//HYPOTHESE 1A : VALIDATION RECEPTEUR / EMETTEUR DEJA VALIDE
						if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
							echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
							echangeToValidate.setDateFin(LocalDate.now());
							//TO DO envoi d'un mail de clôture de l'échange à l'émetteur et au récepteur
							//TO DO enregistrement d'une transaction
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Cloture");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Cloture");
							Transaction transactionCreated = transactionService.createTransaction(id);
							System.out.println("hop"+transactionCreated);
							echangeToValidate.setTransaction(transactionCreated);
							return echangeRepository.save(echangeToValidate);
						}else if (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
							
					//HYPOTHESE 1B : VALIDATION RECEPTEUR / RECEPTEUR DEJA REFUSE
							echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
							echangeToValidate.setDateFin(LocalDate.now());
							//TO DO envoi d'un mail de litige de l'échange à l'émetteur et au récepteur
							// TO DO gestion de la transaction : pas d'enregistrement pour le récepteur qui a refusé, enregistrement pour
							// l'eémetteur qui a accepté avec contrepartie sur le compte monétaire COUNTERPART de l'association
							mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur, "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
							mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur, "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
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
		Echange echangeToConfirm = this.readEchange(id);
		
		if(!echangeToConfirm.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de demander son annulation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToConfirm.getRecepteurId());
		
		echangeToConfirm.setStatutEchange(EnumStatutEchange.ANNULE);
		echangeToConfirm.setDateAnnulation(LocalDate.now());
		mailSender.sendMailEchangeConfirmation(echangeToConfirm, recepteur, "Annulation de l'echange", "04_RecepteurReponse_EchangeAnnulation");
		return echangeRepository.save(echangeToConfirm);	
	}


	@Override
	public Echange confirmerEchange(@Valid Long id) throws UnsupportedEncodingException, MessagingException, DeniedAccessException, EntityNotFoundException {
		Echange echangeToConfirm = this.readEchange(id);
		
		if(!echangeToConfirm.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
			throw new DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre confirmation");
		
		UserBean recepteur = adherentsProxy.consulterCompteAdherent(echangeToConfirm.getRecepteurId());
		
	
		echangeToConfirm.setStatutEchange(EnumStatutEchange.CONFIRME);
		echangeToConfirm.setDateConfirmation(LocalDate.now());
		mailSender.sendMailEchangeConfirmation(echangeToConfirm, recepteur, "Confirmation de l'echange", "03_RecepteurReponse_EchangeConfirmation");
		return echangeRepository.save(echangeToConfirm);
	
		
	}

	@Override
	public Echange solderEchange(@Valid Long id) {
		// FIXME Auto-generated method stub
		return null;
	}


	/*
	 * @Override public Echange confirmerEchange(Long id, Long emetteurId, Boolean
	 * decision) throws EntityNotFoundException, DeniedAccessException,
	 * UnsupportedEncodingException, MessagingException {
	 * 
	 * Echange echangeToConfirm = this.readEchange(id);
	 * 
	 * if(emetteurId!=echangeToConfirm.getEmetteurId()) throw new
	 * DeniedAccessException("Vous ne pouvez confirmer qu'un échange qui répond à une de vos propositions"
	 * );
	 * if(!echangeToConfirm.getStatutEchange().equals(EnumStatutEchange.ENREGISTRE))
	 * throw new
	 * DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre confirmation"
	 * );
	 * 
	 * UserBean recepteur =
	 * adherentsProxy.consulterCompteAdherent(echangeToConfirm.getRecepteurId());
	 * 
	 * if (decision == true) {
	 * echangeToConfirm.setStatutEchange(EnumStatutEchange.CONFIRME);
	 * echangeToConfirm.setDateConfirmation(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToConfirm, recepteur,
	 * "Confirmation de l'echange", "03_RecepteurReponse_EchangeConfirmation");
	 * return echangeRepository.save(echangeToConfirm); }
	 * echangeToConfirm.setStatutEchange(EnumStatutEchange.ANNULE);
	 * echangeToConfirm.setDateAnnulation(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToConfirm, recepteur,
	 * "Annulation de l'echange", "04_RecepteurReponse_EchangeAnnulation"); return
	 * echangeRepository.save(echangeToConfirm); }
	 */


	/*
	 * @Override public Echange validerEchange(@Valid Long id, Long validateurId,
	 * Boolean decision) throws UnsupportedEncodingException, MessagingException,
	 * EntityNotFoundException, DeniedAccessException, EntityAlreadyExistsException
	 * {
	 * 
	 * Echange echangeToValidate = this.readEchange(id);
	 * 
	 * if(validateurId!=echangeToValidate.getEmetteurId() &&
	 * validateurId!=echangeToValidate.getRecepteurId()) throw new
	 * DeniedAccessException("Vous ne pouvez valider qu'un échange auquel vous êtes participant"
	 * );
	 * if(!echangeToValidate.getStatutEchange().equals(EnumStatutEchange.CONFIRME))
	 * throw new
	 * DeniedAccessException("Le statut de cet échange ne vous permet pas de donner votre validation"
	 * );
	 * 
	 * UserBean recepteur =
	 * adherentsProxy.consulterCompteAdherent(echangeToValidate.getRecepteurId());
	 * UserBean emetteur =
	 * adherentsProxy.consulterCompteAdherent(echangeToValidate.getEmetteurId());
	 * 
	 * //SCENARII AVIS DE VALIDATION PAR L'EMETTEUR OU LE RECEPTEUR --------< BOUCLE
	 * DECISION = TRUE if (decision == true) {
	 * 
	 * //HYPOTHESE 1 : AVIS VALIDATION EMETTEUR
	 * if(validateurId==echangeToValidate.getEmetteurId()) {
	 * echangeToValidate.setAvisEmetteur(EnumEchangeAvis.VALIDE);
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Validation de l'echange par l'emetteur",
	 * "05_EmetteurProposition_EchangeValidation"); //On vérifie si le récepteur a
	 * déjà donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
	 * 
	 * //HYPOTHESE 1A : VALIDATION EMETTEUR / RECEPTEUR DEJA VALIDE
	 * if(echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
	 * echangeToValidate.setDateFin(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Cloture");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Cloture");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else if
	 * (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)){
	 * 
	 * //HYPOTHESE 1B : VALIDATION EMETTEUR / RECEPTEUR DEJA REFUSE
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else {
	 * 
	 * //HYPOTHESE 1C : VALIDATION EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE
	 * L'ECHANGE reste CONFIRME
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange relance pour avis", "09_Relance_pour_avis"); return
	 * echangeRepository.save(echangeToValidate); } }else {
	 * 
	 * //HYPOTHESE 2 : ACCORD VALIDATION RECEPTEUR
	 * if(validateurId==echangeToValidate.getRecepteurId()) {
	 * echangeToValidate.setAvisRecepteur(EnumEchangeAvis.VALIDE);
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Validation de l'echange par le Recepteur",
	 * "06_RecepteurReponse_EchangeValidation"); //On vérifie si l'émetteur a déjà
	 * donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
	 * 
	 * //HYPOTHESE 1A : VALIDATION RECEPTEUR / EMETTEUR DEJA VALIDE
	 * if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.CLOTURE);
	 * echangeToValidate.setDateFin(LocalDate.now()); //TO DO envoi d'un mail de
	 * clôture de l'échange à l'émetteur et au récepteur //TO DO enregistrement
	 * d'une transaction mailSender.sendMailEchangeConfirmation(echangeToValidate,
	 * recepteur, "Cloture de l'echange",
	 * "05A_EmetteurProposition_EchangeValidation_Cloture");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Cloture");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else if
	 * (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
	 * 
	 * //HYPOTHESE 1B : VALIDATION RECEPTEUR / RECEPTEUR DEJA REFUSE
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now()); //TO DO envoi d'un mail de
	 * litige de l'échange à l'émetteur et au récepteur // TO DO gestion de la
	 * transaction : pas d'enregistrement pour le récepteur qui a refusé,
	 * enregistrement pour // l'eémetteur qui a accepté avec contrepartie sur le
	 * compte monétaire COUNTERPART de l'association
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else {
	 * 
	 * //HYPOTHESE 1C : VALIDATION RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE
	 * L'ECHANGE reste CONFIRME
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange relance pour avis", "09_Relance_pour_avis"); return
	 * echangeRepository.save(echangeToValidate); } } } }
	 * 
	 * //SCENARII REFUS DE VALIDATION PAR L'EMETTEUR OU LE RECEPTEUR ------->
	 * DECISION = FALSE
	 * 
	 * //HYPOTHESE 3 : REFUS VALIDATION EMETTEUR
	 * if(validateurId==echangeToValidate.getEmetteurId()) {
	 * echangeToValidate.setAvisEmetteur(EnumEchangeAvis.REFUSE);
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Refus de valider l'echange par l'emetteur",
	 * "07_EmetteurProposition_EchangeRefus"); //On vérifie si le récepteur a déjà
	 * donné un avis et si OUI lequel pour faire évoluer le statut de l'échange
	 * 
	 * //HYPOTHESE 3A : REFUS EMETTEUR / RECEPTEUR DEJA VALIDE
	 * if(echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.VALIDE)) {
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now()); //TO DO envoi d'un mail de
	 * clôture de l'échange à l'émetteur et au récepteur //TO DO enregistrement
	 * d'une transaction mailSender.sendMailEchangeConfirmation(echangeToValidate,
	 * recepteur, "Cloture de l'echange",
	 * "05A_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else if
	 * (echangeToValidate.getAvisRecepteur().equals(EnumEchangeAvis.REFUSE)){
	 * 
	 * //HYPOTHESE 3B : REFUS EMETTEUR / RECEPTEUR DEJA REFUSE
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now()); //TO DO envoi d'un mail de
	 * litige de l'échange à l'émetteur et au récepteur // TO DO gestion de la
	 * transaction : pas d'enregistrement pour le récepteur qui a refusé,
	 * enregistrement pour // l'eémetteur qui a accepté avec contrepartie sur le
	 * compte monétaire COUNTERPART de l'association
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else {
	 * 
	 * //HYPOTHESE 3C : REFUS EMETTEUR / RECEPTEUR SANS AVIS = le STATUT DE
	 * L'ECHANGE reste CONFIRME
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange relance pour avis", "09_Relance_pour_avis"); return
	 * echangeRepository.save(echangeToValidate); } }else {
	 * 
	 * //HYPOTHESE 4 : REFUS VALIDATION RECEPTEUR
	 * if(validateurId==echangeToValidate.getRecepteurId()) {
	 * echangeToValidate.setAvisRecepteur(EnumEchangeAvis.REFUSE);
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Refus de valider l'echange par le Recepteur",
	 * "08_RecepteurReponse_EchangeRefus"); //On vérifie si l'émetteur a déjà donné
	 * un avis et si OUI lequel pour faire évoluer le statut de l'échange
	 * 
	 * //HYPOTHESE 4A : REFUS RECEPTEUR / EMETTEUR DEJA VALIDE
	 * if(echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.VALIDE)) {
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Cloture de l'echange", "05A_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Cloture de l'echange", "05A_RecepteurReponse_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else if
	 * (echangeToValidate.getAvisEmetteur().equals(EnumEchangeAvis.REFUSE)){
	 * 
	 * //HYPOTHESE 4B : REFUS RECEPTEUR / EMETTEUR DEJA REFUSE
	 * echangeToValidate.setStatutEchange(EnumStatutEchange.LITIGE);
	 * echangeToValidate.setDateFin(LocalDate.now());
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, recepteur,
	 * "Echange en litige", "05B_EmetteurProposition_EchangeValidation_Litige");
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange en litige", "05B_RecepteurProposition_EchangeValidation_Litige");
	 * Transaction transactionCreated = transactionService.createTransaction(id);
	 * echangeToValidate.setTransaction(transactionCreated); return
	 * echangeRepository.save(echangeToValidate); }else {
	 * 
	 * //HYPOTHESE 4C : REFUS RECEPTEUR / EMETTEUR SANS AVIS = le STATUT DE
	 * L'ECHANGE reste CONFIRME
	 * mailSender.sendMailEchangeConfirmation(echangeToValidate, emetteur,
	 * "Echange relance pour avis", "09_Relance_pour_avis"); return
	 * echangeRepository.save(echangeToValidate); } } } return
	 * echangeRepository.save(echangeToValidate); }
	 */




	
	}
	
	

	
	
	
	
	


