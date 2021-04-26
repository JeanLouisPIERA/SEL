package com.microselbourse.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.Echange;
import com.microselbourse.entities.Evaluation;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailEchangeEvaluation;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.MessageMailScheduler;
import com.microselbourse.entities.Reponse;
import com.microselbourse.exceptions.EntityAlreadyExistsException;
import com.microselbourse.exceptions.EntityNotFoundException;
import com.microselbourse.service.IEchangeService;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Autowired
	private IEchangeService echangeService;

	@Value("${microselbourse.rabbitmq.queue1}")
	String queueName1;

	@Value("${microselbourse.rabbitmq.queue2}")
	String queueName2;

	@Value("${microselbourse.rabbitmq.queue3}")
	String queueName3;
	
	@Value("${microselbourse.rabbitmq.queue4}")
	String queueName4;
	
	@Value("${microselbourse.rabbitmq.queue5}")
	String queueName5;


	@Value("${microselbourse.rabbitmq.exchange}")
	private String exchange;

	public void sendMessageMailReponse(MessageMailReponse messageMailReponse) {
		rabbitTemplate.convertAndSend(exchange, queueName1, messageMailReponse);
		System.out.println("Send msg Reponse= " + messageMailReponse);

	}

	public void sendMessageMailEchange(MessageMailEchange messageMailEchange) {
		rabbitTemplate.convertAndSend(exchange, queueName2, messageMailEchange);
		System.out.println("Send msg Echange = " + messageMailEchange);

	}

	public void sendMessageCreateWallet(UserBean emetteur) {
		rabbitTemplate.convertAndSend(exchange, queueName3, emetteur);
		System.out.println("Send msg Create Wallet = " + emetteur);

	}
	
	public void sendMessageMailEchangeEvaluation(MessageMailEchangeEvaluation messageMailEchangeEvaluation) {
		rabbitTemplate.convertAndSend(exchange, queueName4, messageMailEchangeEvaluation);
		System.out.println("Send msg Create Evaluation = " + messageMailEchangeEvaluation);
	}

	public void sendMessageMailScheduler(MessageMailScheduler messageMailScheduler) {
		rabbitTemplate.convertAndSend(exchange, queueName5, messageMailScheduler);
		System.out.println("Send msg Mail Scheduler = " + messageMailScheduler);
		
	}
	
	

}
