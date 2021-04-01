package com.microselbourse.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microselbourse.beans.UserBean;
import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.Reponse;
import com.microselbourse.service.IMailSenderService;

/*@Service*/
public class RabbitMQListnerMessageMail {

/*implements MessageListener {
	
	@Autowired
	private IMailSenderService mailSender;

	@Override
	public void onMessage(Message message) {
		//Item itemWithOwner = new ObjectMapper().readValue(json, Item.class);
		
		  try { 
			  
				
				  MessageMailReponse messageMailReponseWithReponse = new ObjectMapper()
				  .readValue(message.getBody(), MessageMailReponse.class);
				 
				  Reponse reponseToSend = messageMailReponseWithReponse.getReponse();
				  UserBean destinataire = messageMailReponseWithReponse.getDestinataire();
				  String subject =  messageMailReponseWithReponse.getSubject();
				  String  template= messageMailReponseWithReponse.getMicroselBourseMailTemplate();
			  
				
				 * ObjectMapper objectMapper = new ObjectMapper();
				 * objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				 * false); objectMapper.registerModule(new JavaTimeModule());
				 
				
				 * ObjectMapper mapper = new ObjectMapper(); mapper.registerModule(new
				 * JavaTimeModule());
				 * mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
				 * 
				 * MessageMailReponse messageMailReponseWithReponse =
				 * mapper.readValue(message.getBody(), MessageMailReponse.class);
				 
		  
				
				
				  try { mailSender.sendMailEchangeCreation( reponseToSend,destinataire,subject, template); } catch
				  (MessagingException e) { e.printStackTrace(); }
				 
		  
				
				 * System.out.println("Consuming Message Mail - " +
				 * messageMailReponseWithReponse.toString() ); System.out.println("Reponse - " +
				 * messageMailReponseWithReponse.getReponse().getId() );
				 * System.out.println("Destinataire - " +
				 * messageMailReponseWithReponse.getDestinataire() );
				 * System.out.println("Subject - " + messageMailReponseWithReponse.getSubject()
				 * ); System.out.println("Template - " +
				 * messageMailReponseWithReponse.getMicroselBourseMailTemplate());
				 
				  
		  } catch (JsonParseException e) { e.printStackTrace(); 
		  } catch
		  (JsonMappingException e) { e.printStackTrace(); 
		  } catch (IOException e) {
		  e.printStackTrace(); }
		 
		
		
	}*/
}
