package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.microselbourse.beans.UserBean;
import com.microselbourse.dao.IEchangeRepository;
import com.microselbourse.entities.Blocage;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.MessageMailDeblocage;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailEchangeEvaluation;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.MessageMailScheduler;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Transaction;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEchangeService;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IMailService;
import com.microselbourse.service.ITransactionService;
import com.microselbourse.service.IWalletService;

@Component
public class RabbitMQConsumer {

	@Autowired
	private IMailSenderService mailSender;

	@Autowired
	private IWalletService walletService;

	@Autowired
	private IEchangeService echangeService;
	
	@Autowired
	private IEchangeRepository echangeRepository;
	
	@Autowired
	private ITransactionService transactionService;
	
	@Autowired
	private IMailService mailService;


	@RabbitListener(queues = "${microselbourse.rabbitmq.queue1}")
	public void recievedMessageMailReponse(MessageMailReponse messageMailReponse) {

		try {
			System.out.println("Recieved MessageMailReponse From RabbitMQ: " + messageMailReponse);
			Reponse reponseToSend = messageMailReponse.getReponse();
			UserBean destinataire = messageMailReponse.getDestinataire();
			String subject = messageMailReponse.getSubject();
			String template = messageMailReponse.getMicroselBourseMailTemplate();
			mailSender.sendMailEchangeCreation(reponseToSend, destinataire, subject, template);
			System.out.println("Recieved MessageMailReponse Success From RabbitMQ: " + messageMailReponse);
		} catch (MessagingException | UnsupportedEncodingException e) {
			System.out
					.println("Recieved MessageMailReponse Failed From RabbitMQ: " + messageMailReponse + e.getCause());
		}
	}

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue2}")
	public void recievedMessageMailEchange(MessageMailEchange messageMailEchange) {

		try {
			System.out.println("Recieved MessageMailEchange From RabbitMQ: " + messageMailEchange);
			Echange echangeToSend = messageMailEchange.getEchange();
			UserBean destinataire = messageMailEchange.getDestinataire();
			String subject = messageMailEchange.getSubject();
			String template = messageMailEchange.getMicroselBourseMailTemplate();
			mailSender.sendMailEchangeConfirmation(echangeToSend, destinataire, subject, template);
			System.out.println("Recieved MessageMailEchange Success From RabbitMQ: " + messageMailEchange);
		} catch (MessagingException | UnsupportedEncodingException e) {
			System.out
					.println("Recieved MessageMailEchange Failed From RabbitMQ: " + messageMailEchange + e.getCause());
		}

	}

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue3}")
	public void recievedMessageCreateWallet(UserBean emetteur) throws EntityAlreadyExistsException {

		System.out.println("Recieved MessageCreateWallet From RabbitMQ: " + emetteur.getId());
		Wallet emetteurWalletCreated = walletService.createWallet(emetteur.getId());
		System.out.println("Recieved MessageCreateWallet Success From RabbitMQ: " + emetteur.getId());

	}

	
	
	@RabbitListener(queues = "${microselbourse.rabbitmq.queue4}")
	public void recievedMessageMailEchangeEvaluation(MessageMailEchangeEvaluation messageMailEchangeEvaluation)
			throws EntityAlreadyExistsException, EntityNotFoundException, UnsupportedEncodingException, MessagingException {

		System.out.println("Recieved MessageMailEchangeEvaluation From RabbitMQ: " + messageMailEchangeEvaluation);
		Evaluation evaluation = messageMailEchangeEvaluation.getEvaluation();
		UserBean destinataire=messageMailEchangeEvaluation.getDestinataire();
		String subject =messageMailEchangeEvaluation.getSubject();
		String microselBourseMailTemplate=messageMailEchangeEvaluation.getMicroselBourseMailTemplate();
		
		mailSender.sendMailEchangeEvaluation(evaluation, destinataire,subject, microselBourseMailTemplate);
		
		System.out.println("Recieved MessageMailEchangeEvaluation Success From RabbitMQ: " + messageMailEchangeEvaluation);

	}
	
	@RabbitListener(queues = "${microselbourse.rabbitmq.queue5}")
	public void recievedMessageMailScheduler(MessageMailScheduler messageMailScheduler)
			throws EntityAlreadyExistsException, EntityNotFoundException, UnsupportedEncodingException, MessagingException {

		System.out.println("Recieved MessageMailScheduler From RabbitMQ: " + messageMailScheduler);
		
		String to = messageMailScheduler.getTo();
		String name = messageMailScheduler.getName();
		String subject = messageMailScheduler.getSubject();
		String htmlBody = messageMailScheduler.getHtmlBody();
		
		mailService.sendHtmlMessage(to, name, subject, htmlBody);
		
		System.out.println("Recieved MessageMailScheduler Success From RabbitMQ: " + messageMailScheduler);

	}
	
	@RabbitListener(queues = "${microselbourse.rabbitmq.queue6}")
	public void recievedMessageMailDeblocage(MessageMailDeblocage messageMailDeblocage)
			throws EntityAlreadyExistsException, EntityNotFoundException, UnsupportedEncodingException, MessagingException {

		System.out.println("Recieved MessageMailDeblocage From RabbitMQ: " + messageMailDeblocage);
		Blocage blocageToAnnuler = messageMailDeblocage.getBlocage();
		UserBean destinataire=messageMailDeblocage.getAdherent();
		String subject =messageMailDeblocage.getSubject();
		String microselBourseMailTemplate=messageMailDeblocage.getMicroselBourseMailTemplate();
		
		
		mailSender.sendMailAnnulationBlocage(blocageToAnnuler,destinataire, subject, microselBourseMailTemplate);
		 
		
		System.out.println("Recieved MessageMailEchangeEvaluation Success From RabbitMQ: " + messageMailDeblocage);

	}

}
