package com.microselbourse.configurations;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microselbourse.service.impl.RabbitMQListner;
import com.microselbourse.service.impl.RabbitMQListnerMessageMail;

@Configuration
public class RabbitMQConfig {
	
	@Value("${microselbourse.rabbitmq.queue}")
	String queueName;

	@Value("${microselbourse.rabbitmq.exchange}")
	String exchange;

	@Value("${microselbourse.rabbitmq.routingkey}")
	private String routingkey;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
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
	
	//create MessageListenerContainer using default connection factory
	/*
	 * @Bean MessageListenerContainer messageListenerContainer(ConnectionFactory
	 * connectionFactory ) { SimpleMessageListenerContainer
	 * simpleMessageListenerContainer = new SimpleMessageListenerContainer();
	 * simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
	 * simpleMessageListenerContainer.setQueues(queue());
	 * simpleMessageListenerContainer.setMessageListener(new
	 * RabbitMQListnerMessageMail()); return simpleMessageListenerContainer;
	 * 
	 * }
	 */
	    
	    //create custom connection factory
		/*@Bean
		ConnectionFactory connectionFactory() {
			CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
			cachingConnectionFactory.setUsername(username);
			cachingConnectionFactory.setUsername(password);
			return cachingConnectionFactory;
		}*/
		
	    //create MessageListenerContainer using custom connection factory
		/*@Bean
		MessageListenerContainer messageListenerContainer() {
			SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
			simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
			simpleMessageListenerContainer.setQueues(queue());
			simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
			return simpleMessageListenerContainer;

		}*/

}
