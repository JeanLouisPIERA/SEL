package com.microselbourse.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.microselbourse.entities.MessageMailEchange;
import com.microselbourse.entities.MessageMailReponse;

@Service
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Value("${microselbourse.rabbitmq.exchange}")
	private String exchange;

	@Value("${microselbourse.rabbitmq.routingkey}")
	private String routingkey;

	/*
	 * public void send(PropositionDTO propositionDTO) {
	 * rabbitTemplate.convertAndSend(exchange, routingkey, propositionDTO);
	 * System.out.println("Send msg = " + propositionDTO);
	 * 
	 * }
	 */

	public void sendMessageMailReponse(MessageMailReponse messageMailReponse) {
		rabbitTemplate.convertAndSend(exchange, routingkey, messageMailReponse);
		System.out.println("Send msg Reponse= " + messageMailReponse);

	}

	public void sendMessageMailEchange(MessageMailEchange messageMailEchange) {
		rabbitTemplate.convertAndSend(exchange, routingkey, messageMailEchange);
		System.out.println("Send msg Echange = " + messageMailEchange);

	}

}
