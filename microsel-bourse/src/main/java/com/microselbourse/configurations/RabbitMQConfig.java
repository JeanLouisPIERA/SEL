package com.microselbourse.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

	@Value("${microselbourse.rabbitmq.queue1}")
	String queueName1;

	@Value("${microselbourse.rabbitmq.queue2}")
	String queueName2;

	@Value("${microselbourse.rabbitmq.queue3}")
	String queueName3;

	@Value("${microselbourse.rabbitmq.exchange}")
	String exchange;

	/*
	 * @Value("${microselbourse.rabbitmq.routingkey1}") private String routingkey1;
	 * 
	 * @Value("${microselbourse.rabbitmq.routingkey2}") private String routingkey2;
	 * 
	 * @Value("${microselbourse.rabbitmq.routingkey3}") private String routingkey3;
	 */
	
	
	

	@Bean
	Queue queue1() {
		return new Queue(queueName1, false);
	}
	
	@Bean
	Queue queue2() {
		return new Queue(queueName2, false);
	}
	
	@Bean
	Queue queue3() {
		return new Queue(queueName3, false);
	}



	/*
	 * @Bean DirectExchange exchange() { return new DirectExchange(exchange); }
	 */

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	
	

	/*
	 * @Bean Binding binding1(Queue queue, TopicExchange exchange) { return
	 * BindingBuilder.bind(queue).to(exchange).with(routingkey1); }
	 */

	@Bean
	Binding binding1(TopicExchange exchange) {
		return BindingBuilder.bind(queue1()).to(exchange).with(queueName1);
	}
	
	@Bean
	Binding binding2(TopicExchange exchange) {
		return BindingBuilder.bind(queue2()).to(exchange).with(queueName2);
	}

	@Bean
	Binding binding3(TopicExchange exchange) {
		return BindingBuilder.bind(queue3()).to(exchange).with(queueName3);
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
