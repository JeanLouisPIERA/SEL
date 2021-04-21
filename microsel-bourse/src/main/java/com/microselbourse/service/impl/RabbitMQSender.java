package com.microselbourse.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailReponse;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${microselbourse.rabbitmq.queue1}")
	String queueName1;

	@Value("${microselbourse.rabbitmq.queue2}")
	String queueName2;

	@Value("${microselbourse.rabbitmq.queue3}")
	String queueName3;

	@Value("${microselbourse.rabbitmq.exchange}")
	private String exchange;

	/*
	 * @Value("${microselbourse.rabbitmq.routingkey1}") private String routingkey1;
	 * 
	 * @Value("${microselbourse.rabbitmq.routingkey2}") private String routingkey2;
	 * 
	 * @Value("${microselbourse.rabbitmq.routingkey3}") private String routingkey3;
	 */
	/*
	 * public void send(PropositionDTO propositionDTO) {
	 * rabbitTemplate.convertAndSend(exchange, routingkey, propositionDTO);
	 * System.out.println("Send msg = " + propositionDTO);
	 * 
	 * }
	 */

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

}
