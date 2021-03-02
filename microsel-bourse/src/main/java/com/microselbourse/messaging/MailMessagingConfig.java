package com.microselbourse.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableAutoConfiguration
@EnableRabbit
public class MailMessagingConfig implements RabbitListenerConfigurer {
	
	 public static final String MAIL_MESSAGE_QUEUE = "mail.message.queue";

	    @Bean
	    public TopicExchange appExchange() {
	        return new TopicExchange("");
	    }

	    @Bean
	    public Queue appQueueSpecific() {
	        return new Queue(MAIL_MESSAGE_QUEUE);
	    }

	    @Bean
	    public Binding declareBindingSpecific() {
	        return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with("");
	    }

	    @Bean
	    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
	        return new MappingJackson2MessageConverter();
	    }

	    @Bean
	    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
	        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
	        factory.setMessageConverter(consumerJackson2MessageConverter());
	        return factory;
	    }

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
		
	}
	
	

}
