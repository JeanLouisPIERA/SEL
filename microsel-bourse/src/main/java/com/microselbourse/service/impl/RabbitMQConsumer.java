package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.Reponse;
import com.microselbourse.entities.Wallet;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.service.IMailSenderService;
import com.microselbourse.service.IWalletService;

@Component
public class RabbitMQConsumer {

	@Autowired
	private IMailSenderService mailSender;

	@Autowired
	private IWalletService walletService;


	@RabbitListener(queues = "${microselbourse.rabbitmq.queue1}")
	public void recievedMessageMailReponse(MessageMailReponse messageMailReponse) throws UnsupportedEncodingException {

		if(messageMailReponse.getReponse()==null)
			throw new UnsupportedEncodingException("Recieved MessageMailReponse Null From RabbitMQ: " + messageMailReponse);
			
		try {
			
			System.out.println("Recieved MessageMailReponse Success From RabbitMQ: " + messageMailReponse);
			Reponse reponseToSend = messageMailReponse.getReponse();
			UserBean destinataire = messageMailReponse.getDestinataire();
			String subject = messageMailReponse.getSubject();
			String template = messageMailReponse.getMicroselBourseMailTemplate();
			mailSender.sendMailEchangeCreation(reponseToSend, destinataire, subject, template);
		} catch (MessagingException e) {
			// e.printStackTrace();
			System.out.println("Recieved MessageMailReponse Failed From RabbitMQ: " + messageMailReponse);
		}
	}

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue2}")
	public void recievedMessageMailEchange(MessageMailEchange messageMailEchange) throws UnsupportedEncodingException {

		if(messageMailEchange.getEchange()==null) 
			throw new UnsupportedEncodingException("Recieved MessageMailEchange Null From RabbitMQ: " + messageMailEchange);
			
		
		try {
			System.out.println("Recieved MessageMailEchange Success From RabbitMQ: " + messageMailEchange);	
			Echange echangeToSend = messageMailEchange.getEchange();
			UserBean destinataire = messageMailEchange.getDestinataire();
			String subject = messageMailEchange.getSubject();
			String template = messageMailEchange.getMicroselBourseMailTemplate();
			mailSender.sendMailEchangeConfirmation(echangeToSend, destinataire, subject, template);
		} catch (MessagingException e) {
			// e.printStackTrace();
			System.out.println("Recieved MessageMailEchange Failed From RabbitMQ: " + messageMailEchange);
		}
		

	}

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue3}")
	public void recievedMessageCreateWallet(UserBean emetteur) throws UnsupportedEncodingException {

		if(emetteur.getId()==null) 
			throw new UnsupportedEncodingException ("Recieved MessageCreateWallet Null From RabbitMQ: " + emetteur.getId());
			
		try {
			System.out.println("Recieved MessageCreateWallet Success From RabbitMQ: " + emetteur.getId());
			Wallet emetteurWalletCreated = walletService.createWallet(emetteur.getId());
			
		} catch (EntityAlreadyExistsException e) {
			// FIXME Auto-generated catch block
			// e.printStackTrace();
			System.out.println("Recieved MessageCreateWallet Failed From RabbitMQ: " + emetteur.getId());
		}
	}

}
