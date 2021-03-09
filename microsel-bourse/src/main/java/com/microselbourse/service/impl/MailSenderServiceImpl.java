package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.Proposition;
import com.microselbourse.entities.Reponse;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IMailService;


@Service
public class MailSenderServiceImpl implements IMailSenderService {
	
	@Autowired
	IMailService mailService;
	
	@Autowired
	private IMicroselAdherentsProxy microselAdherentsProxy;
	
	@Value("${application.mail}")
	private String mailFrom;
	
	private Map<String, Object> model= new HashMap<String, Object>();

	private void populateModel(String string, Object object) {
        model.put(string, object);
	}
	
	@Override
	public void sendMailEchangeCreation(Reponse reponse, UserBean destinataire, String subject, String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException{
	    
		    	String mailTo = destinataire.getEmail(); 
		    	String nomUser = destinataire.getUsername();
		    	
		    	this.populateModel("codePostal", reponse.getCodePostal()); 
		    	this.populateModel("dateEcheance", reponse.getDateEcheance()); 
		    	this.populateModel("dateReponse", reponse.getDateReponse()); 
		    	this.populateModel("description", reponse.getDescription()); 
		    	this.populateModel("enum_trade_type_reponse", reponse.getEnumTradeType().toString()); 
		    	this.populateModel("reponse_id", reponse.getId()); 
		    	this.populateModel("image", reponse.getImage()); 	
		    	this.populateModel("recepteurId", reponse.getRecepteurId()); 
		    	this.populateModel("titre_reponse", reponse.getTitre()); 
		    	this.populateModel("valeur", reponse.getValeur()); 
		    	this.populateModel("ville", reponse.getVille()); 
		    	this.populateModel("categorie", reponse.getProposition().getCategorie().getName().toString()); 
		    	this.populateModel("dateFinPublication", reponse.getProposition().getDateFin()); 
		    	this.populateModel("emetteurId", reponse.getProposition().getEmetteurId()); 
		    	this.populateModel("enum_trade_type_proposition", reponse.getProposition().getEnumTradeType().toString()); 
		    	this.populateModel("proposition_id", reponse.getProposition().getId()); 
		    	this.populateModel("proposition_statut", reponse.getProposition().getStatut().toString());
		    	this.populateModel("titre_proposition", reponse.getProposition().getTitre());
		    	this.populateModel("dateDebutPublication", reponse.getProposition().getDateDebut());
		    	
		    	this.populateModel("destinataire_username", nomUser);
		    	
		        mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model, microselBourseMailTemplate);
		        
	   
	    }

	@Override
	public void sendMailEchangeConfirmation(Echange echange, UserBean destinataire, String subject,
		String microselBourseMailTemplate) throws MessagingException, UnsupportedEncodingException {
		
		String mailTo = destinataire.getEmail(); 
    	String nomUser = destinataire.getUsername();
    	
    	this.populateModel("avis_emetteur", echange.getAvisEmetteur()); 
    	this.populateModel("avis_recepteur", echange.getAvisRecepteur()); 
    	this.populateModel("commentaire_emetteur", echange.getCommentaireEmetteur()); 
    	this.populateModel("commentaire_recepteur", echange.getCommentaireRecepteur()); 
    	this.populateModel("dateAnnulation", echange.getDateAnnulation()); 
    	this.populateModel("dateConfirmation", echange.getDateConfirmation()); 
    	this.populateModel("dateEcheance", echange.getDateEcheance()); 	
    	this.populateModel("titre", echange.getTitre()); 	
    	this.populateModel("emetteur_mail", echange.getEmetteurMail()); 
    	this.populateModel("recepteur_mail", echange.getRecepteurMail()); 
    	this.populateModel("dateEnregistrement", echange.getDateEnregistrement()); 
    	this.populateModel("dateFin", echange.getDateFin()); 
    	this.populateModel("emetteur_id", echange.getEmetteurId()); 
    	this.populateModel("emetteur_username", echange.getEmetteurUsername()); 
    	this.populateModel("echange_id", echange.getId()); 
    	this.populateModel("note_emetteur", echange.getNoteEmetteur()); 
    	this.populateModel("note_recepteur", echange.getNoteRecepteur()); 
    	this.populateModel("recepteur_id", echange.getRecepteurId()); 
    	this.populateModel("recepteur_username", echange.getRecepteurUsername()); 
    	this.populateModel("statut_echange", echange.getStatutEchange().getText());
    	//this.populateModel("transaction_", echange.getTransaction());
    	   	
    	this.populateModel("destinataire_username", nomUser);
    	
        mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model, microselBourseMailTemplate);
		
	}

	@Override
	public void sendMailEchangeEvaluation(Evaluation evaluation, UserBean destinataire, String subject,
			String microselBourseMailTemplate) throws UnsupportedEncodingException, MessagingException {
		
		String mailTo = destinataire.getEmail(); 
    	String nomUser = destinataire.getUsername();
    	
    	this.populateModel("adherent_id", evaluation.getAdherentId()); 
    	this.populateModel("adherent_username", evaluation.getAdherentUsername()); 
    	this.populateModel("commentaire", evaluation.getCommentaire());
    	this.populateModel("date_evaluation", evaluation.getDateEvaluation());
    	this.populateModel("date_enregistrement_echange", evaluation.getEchange().getDateEnregistrement());
    	this.populateModel("echange_emetteur_username", evaluation.getEchange().getEmetteurUsername());
    	this.populateModel("echange_recepteur_username", evaluation.getEchange().getRecepteurUsername());
    	this.populateModel("echange_statut", evaluation.getEchange().getStatutEchange());
    	this.populateModel("echange_id", evaluation.getEchange().getId());
    	this.populateModel("echange_titre", evaluation.getEchange().getTitre());
    	this.populateModel("evaluation_note", evaluation.getEnumNoteEchange().toString());
    	this.populateModel("evaluation_id", evaluation.getId());
    	
    	this.populateModel("destinataire_username", nomUser);
    	
        mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject, model, microselBourseMailTemplate);
		
	}

	/*
	 * @Override public void sendMessageMailEchangeCreation(Reponse reponse, Long
	 * destinataireId, String subject, String microselBourseMailTemplate) throws
	 * MessagingException, UnsupportedEncodingException {
	 * 
	 * UserBean destinataire =
	 * microselAdherentsProxy.consulterCompteAdherent(destinataireId);
	 * 
	 * String mailTo = destinataire.getEmail(); String nomUser =
	 * destinataire.getUsername();
	 * 
	 * this.populateModel("codePostal", reponse.getCodePostal());
	 * this.populateModel("dateEcheance", reponse.getDateEcheance());
	 * this.populateModel("dateReponse", reponse.getDateReponse());
	 * this.populateModel("description", reponse.getDescription());
	 * this.populateModel("enum_trade_type_reponse",
	 * reponse.getEnumTradeType().toString()); this.populateModel("reponse_id",
	 * reponse.getId()); this.populateModel("image", reponse.getImage());
	 * this.populateModel("recepteurId", reponse.getRecepteurId());
	 * this.populateModel("titre_reponse", reponse.getTitre());
	 * this.populateModel("valeur", reponse.getValeur());
	 * this.populateModel("ville", reponse.getVille());
	 * this.populateModel("categorie",
	 * reponse.getProposition().getCategorie().getName().toString());
	 * this.populateModel("dateFinPublication",
	 * reponse.getProposition().getDateFin()); this.populateModel("emetteurId",
	 * reponse.getProposition().getEmetteurId());
	 * this.populateModel("enum_trade_type_proposition",
	 * reponse.getProposition().getEnumTradeType().toString());
	 * this.populateModel("proposition_id", reponse.getProposition().getId());
	 * this.populateModel("proposition_statut",
	 * reponse.getProposition().getStatut().toString());
	 * this.populateModel("titre_proposition", reponse.getProposition().getTitre());
	 * this.populateModel("dateDebutPublication",
	 * reponse.getProposition().getDateDebut());
	 * 
	 * this.populateModel("destinataire_username", nomUser);
	 * 
	 * mailService.sendMessageUsingThymeleafTemplate(mailTo, nomUser, subject,
	 * model, microselBourseMailTemplate);
	 * 
	 * }
	 */
	
	
	
}

