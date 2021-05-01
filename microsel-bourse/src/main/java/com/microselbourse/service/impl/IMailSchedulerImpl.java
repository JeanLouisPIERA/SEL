package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.microselbourse.entities.Echange;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailScheduler;
import com.microselbourse.service.IMailService;

@Service
public class IMailSchedulerImpl implements IMailScheduler {

	private Map<String, Object> model = new HashMap<String, Object>();

	@Autowired
	IMailService mailService;

	@Autowired
	IEchangeService echangeService;

	private void populateModel(String string, Object object) {
		model.put(string, object);
	}

	@Value("${application.subject.echange.suppress}")
	private String subjectSuppress;

	@Value("${application.subject.echange.forcevalid}")
	private String subjectForceValid;

	@Value("${application.subject.echange.forcerefus}")
	private String subjectForceRefus;

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

	@Scheduled(cron = "${application.cron}")
	@Override
	public void sendMailsEchangesASupprimer() throws MessagingException, UnsupportedEncodingException {

		List<Echange> echangesASupprimerList = echangeService.searchAndUpdateEchangesASupprimer();

		for (Echange echangeASupprimer : echangesASupprimerList) {
			String mailTo = echangeASupprimer.getEmetteurMail();
			String nomEmetteur = echangeASupprimer.getEmetteurUsername();

			this.populateModel("echangeId", echangeASupprimer.getId().toString());
			this.populateModel("nomUser", echangeASupprimer.getEmetteurUsername());

			mailService.sendMessageUsingThymeleafTemplateSuppress(mailTo, nomEmetteur, subjectSuppress, model);

			String nomRecepteur = echangeASupprimer.getRecepteurUsername();
			this.populateModel("nomUser", echangeASupprimer.getRecepteurUsername());

			mailService.sendMessageUsingThymeleafTemplateSuppress(mailTo, nomRecepteur, subjectSuppress, model);

		}

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
	@Scheduled(cron = "${application.cron}")
	public void sendMailsEchangesAForceValiderList() throws MessagingException, UnsupportedEncodingException,
			EntityNotFoundException, EntityAlreadyExistsException {
		List<Echange> echangesAForceValiderListEmetteur = echangeService.searchAndUpdateEchangesAForceValiderEmetteur();

		for (Echange echangeAForceValider : echangesAForceValiderListEmetteur) {
			String mailTo = echangeAForceValider.getEmetteurMail();
			String nomEmetteur = echangeAForceValider.getEmetteurUsername();

			this.populateModel("echangeId", echangeAForceValider.getId().toString());
			this.populateModel("nomUser", echangeAForceValider.getEmetteurUsername());

			mailService.sendMessageUsingThymeleafTemplateForceValid(mailTo, nomEmetteur, subjectForceValid, model);

		}

		List<Echange> echangesAForceValiderListRecepteur = echangeService
				.searchAndUpdateEchangesAForceValiderRecepteur();

		for (Echange echangeAForceValider : echangesAForceValiderListRecepteur) {
			String mailTo = echangeAForceValider.getEmetteurMail();
			String nomRecepteur = echangeAForceValider.getRecepteurUsername();

			this.populateModel("echangeId", echangeAForceValider.getId().toString());
			this.populateModel("nomUser", echangeAForceValider.getRecepteurUsername());

			mailService.sendMessageUsingThymeleafTemplateForceValid(mailTo, nomRecepteur, subjectForceValid, model);

		}
	}

	/**
	 * Si seul le récepteur ou seul l’émetteur a renseigné un avis REFUSE sur *
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
	@Scheduled(cron = "${application.cron}")
	public void sendMailsEchangesAForceRefuserList()
			throws MessagingException, UnsupportedEncodingException, EntityNotFoundException {
		List<Echange> echangesAForceRefuserListEmetteur = echangeService.searchAndUpdateEchangesAForceRefuserEmetteur();

		for (Echange echangeAForceRefuser : echangesAForceRefuserListEmetteur) {
			String mailTo = echangeAForceRefuser.getEmetteurMail();
			String nomEmetteur = echangeAForceRefuser.getEmetteurUsername();

			this.populateModel("echangeId", echangeAForceRefuser.getId().toString());
			this.populateModel("nomUser", echangeAForceRefuser.getEmetteurUsername());

			mailService.sendMessageUsingThymeleafTemplateForceRefus(mailTo, nomEmetteur, subjectForceValid, model);

		}

		List<Echange> echangesAForceRefuserListRecepteur = echangeService
				.searchAndUpdateEchangesAForceRefuserRecepteur();

		for (Echange echangeAForceRefuser : echangesAForceRefuserListRecepteur) {
			String mailTo = echangeAForceRefuser.getEmetteurMail();
			String nomRecepteur = echangeAForceRefuser.getRecepteurUsername();

			this.populateModel("echangeId", echangeAForceRefuser.getId().toString());
			this.populateModel("nomUser", echangeAForceRefuser.getRecepteurUsername());

			mailService.sendMessageUsingThymeleafTemplateForceRefus(mailTo, nomRecepteur, subjectForceValid, model);

		}

	}

}
