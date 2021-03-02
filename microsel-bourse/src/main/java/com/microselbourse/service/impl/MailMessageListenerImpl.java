package com.microselbourse.service.impl;


import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.microselbourse.entities.Reponse;
import com.microselbourse.messaging.MailMessagingConfig;
import com.microselbourse.proxies.IMicroselAdherentsProxy;
import com.microselbourse.service.IMailSenderService;

@Service
public class MailMessageListenerImpl {
	
    @Autowired
    IMailSenderService mailSenderService;
    
    @Autowired
	private IMicroselAdherentsProxy microselAdherentsProxy;

    @RabbitListener(queues = MailMessagingConfig.MAIL_MESSAGE_QUEUE)
    public void receiveMessage(Reponse reponse, Long destinataireId, String subject, String microselBourseMailTemplate) throws UnsupportedEncodingException, MessagingException {
    	System.out.println("reponseId=" + reponse.getId());
    	mailSenderService.sendMessageMailEchangeCreation(reponse, destinataireId, subject, microselBourseMailTemplate);
    }

}
