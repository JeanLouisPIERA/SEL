package com.microselbourse.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de créations des Beans des queues et binding de RabbitMQ
 * @author jeanl
 *
 */

@Configuration
public class RabbitMQConfig {

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

	@Value("${microselbourse.rabbitmq.queue6}")
	String queueName6;

	@Value("${microselbourse.rabbitmq.exchange}")
	String exchange;

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	/**
	 * Bean de Queue & Binding pour gérer l'envoi des mails de création d'une
	 * réponse à une proposition
	 * 
	 * @return
	 */

	@Bean
	Queue queue1() {
		return new Queue(queueName1, false);
	}

	@Bean
	Binding binding1(TopicExchange exchange) {
		return BindingBuilder.bind(queue1()).to(exchange).with(queueName1);
	}

	/**
	 * Bean de Queue & Binding pour gérer l'envoi des mails de création d'un échange
	 * suite à la réponse à une proposition
	 * 
	 * @return
	 */

	@Bean
	Queue queue2() {
		return new Queue(queueName2, false);
	}

	@Bean
	Binding binding2(TopicExchange exchange) {
		return BindingBuilder.bind(queue2()).to(exchange).with(queueName2);
	}

	/**
	 * Bean de Queue et Binding pour la gestion en arrière plan de la création du
	 * portefeuille d'un adhérent lorsqu'il n'existe pas
	 * 
	 * @return
	 */

	@Bean
	Queue queue3() {
		return new Queue(queueName3, false);
	}

	@Bean
	Binding binding3(TopicExchange exchange) {
		return BindingBuilder.bind(queue3()).to(exchange).with(queueName3);
	}

	/**
	 * Bean de Queue et Binding pour la création en arrière plan de l'évaluation
	 * d'un échange
	 * 
	 * @return
	 */

	@Bean
	Queue queue4() {
		return new Queue(queueName4, false);
	}

	@Bean
	Binding binding4(TopicExchange exchange) {
		return BindingBuilder.bind(queue4()).to(exchange).with(queueName4);
	}

	/**
	 * Bean de Queue et Binding pour la création en arrière plan des mails envoyés
	 * par le MailScheduler
	 * 
	 * @return
	 */

	@Bean
	Queue queue5() {
		return new Queue(queueName5, false);
	}

	@Bean
	Binding binding5(TopicExchange exchange) {
		return BindingBuilder.bind(queue5()).to(exchange).with(queueName5);
	}

	/**
	 * Bean de Queue et Binding pour gérer l'envoi des mails de déblocage du compte
	 * d'un adhérent bloqué
	 * 
	 * @return
	 */

	@Bean
	Queue queue6() {
		return new Queue(queueName6, false);
	}

	@Bean
	Binding binding6(TopicExchange exchange) {
		return BindingBuilder.bind(queue6()).to(exchange).with(queueName6);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

}
