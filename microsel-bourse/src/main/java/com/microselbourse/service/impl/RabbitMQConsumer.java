package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.Reponse;
import com.microselbourse.service.IMailSenderService;

@Component
public class RabbitMQConsumer {

	@Autowired
	private IMailSenderService mailSender;

	/*
	 * @RabbitListener(queues = "${microselbourse.rabbitmq.queue}") public void
	 * recievedMessage(PropositionDTO propositionDTO) {
	 * System.out.println("Recieved Message From RabbitMQ: " + propositionDTO); }
	 */

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue}")
	public void recievedMessageMailReponse(MessageMailReponse messageMailReponse) throws UnsupportedEncodingException {

		System.out.println("Recieved Message From RabbitMQ: " + messageMailReponse);

		Reponse reponseToSend = messageMailReponse.getReponse();
		UserBean destinataire = messageMailReponse.getDestinataire();
		String subject = messageMailReponse.getSubject();
		String template = messageMailReponse.getMicroselBourseMailTemplate();

		try {
			mailSender.sendMailEchangeCreation(reponseToSend, destinataire, subject, template);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@RabbitListener(queues = "${microselbourse.rabbitmq.queue}")
	public void recievedMessageMailEchange(MessageMailEchange messageMailEchange) throws UnsupportedEncodingException {

		System.out.println("Recieved Message From RabbitMQ: " + messageMailEchange);

		Echange echangeToSend = messageMailEchange.getEchange();
		UserBean destinataire = messageMailEchange.getDestinataire();
		String subject = messageMailEchange.getSubject();
		String template = messageMailEchange.getMicroselBourseMailTemplate();

		try {
			mailSender.sendMailEchangeConfirmation(echangeToSend, destinataire, subject, template);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
