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

	@Scheduled(cron = "${application.cron}")
	@Override
	public void sendMailsEchangesASupprimerToEmetteurList() throws MessagingException, UnsupportedEncodingException {

		List<Echange> echangesASupprimerList = echangeService.searchAndUpdateEchangesASupprimer();

		for (Echange echangeASupprimer : echangesASupprimerList) {
			String mailTo = echangeASupprimer.getEmetteurMail();
			String nomEmetteur = echangeASupprimer.getEmetteurUsername();

			this.populateModel("Référence de l'échange à supprimer", echangeASupprimer.getId());

			mailService.sendMessageUsingThymeleafTemplateSuppress(mailTo, nomEmetteur, subjectSuppress, model);

		}

	}

	@Scheduled(cron = "${application.cron}")
	@Override
	public void sendMailsEchangesASupprimerToRecepteurList() throws MessagingException, UnsupportedEncodingException {
		List<Echange> echangesASupprimerList = echangeService.searchAndUpdateEchangesASupprimer();

		for (Echange echangeASupprimer : echangesASupprimerList) {
			String mailTo = echangeASupprimer.getRecepteurMail();
			String nomRecepteur = echangeASupprimer.getRecepteurUsername();

			this.populateModel("Référence de l'échange à supprimer", echangeASupprimer.getId());

			mailService.sendMessageUsingThymeleafTemplateSuppress(mailTo, nomRecepteur, subjectSuppress, model);

		}

	}

	@Override
	public void sendMailsEchangesAForceValiderList() throws MessagingException, UnsupportedEncodingException {
		// FIXME Auto-generated method stub

	}

	@Override
	public void sendMailsEchangesAForceRefuserList() throws MessagingException, UnsupportedEncodingException {
		// FIXME Auto-generated method stub

	}

}
