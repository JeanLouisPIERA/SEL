package com.microselbourse.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.microselbourse.entities.MessageMailReponse;
import com.microselbourse.entities.MessageMailScheduler;
import com.microselbourse.service.IMailService;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
	private JavaMailSender eMailSender;

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;
	
	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Value("${application.mail}")
	private String mail;

	@Value("${application.nameFrom}")
	private String nameFrom;

	@Value("${application.template.echange.suppress}")
	private String templateSuppress;
	@Value("${application.template.echange.forcevalid}")
	private String templateForceValid;
	@Value("${application.template.echange.forcerefus}")
	private String templateForceRefus;

	/**
	 * Cette méthode permet de customiser le message envoyé en utilisant le template
	 * Thymeleaf indiqué (paramètre String mailTemplate)
	 * 
	 * @param to
	 * @param subject
	 * @param templateModel
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public void sendMessageUsingThymeleafTemplate(String to, String name, String subject,
			Map<String, Object> templateModel, String microselBourseMailTemplate)
			throws MessagingException, UnsupportedEncodingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process(microselBourseMailTemplate, thymeleafContext);
		
		//sendHtmlMessage(to, name, subject, htmlBody);
		
		MessageMailScheduler messageMailScheduler = new MessageMailScheduler();
		messageMailScheduler.setTo(to);
		messageMailScheduler.setSubject(subject);
		messageMailScheduler.setName(name);
		messageMailScheduler.setHtmlBody(htmlBody);
		rabbitMQSender.sendMessageMailScheduler(messageMailScheduler);;//--------------------------------------------------------->RMQ
	}
	
	@Override
	public void sendMessageUsingThymeleafTemplateSuppress(String to, String name, String subject,
			Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process(templateSuppress, thymeleafContext);
		
		//sendHtmlMessage(to, name, subject, htmlBody);
		
		MessageMailScheduler messageMailScheduler = new MessageMailScheduler();
		messageMailScheduler.setTo(to);
		messageMailScheduler.setSubject(subject);
		messageMailScheduler.setName(name);
		messageMailScheduler.setHtmlBody(htmlBody);
		rabbitMQSender.sendMessageMailScheduler(messageMailScheduler);;//--------------------------------------------------------->RMQ
	}
	
	@Override
	public void sendMessageUsingThymeleafTemplateForceValid(String to, String name, String subject,
			Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process(templateForceValid, thymeleafContext);

		//sendHtmlMessage(to, name, subject, htmlBody);
		
		MessageMailScheduler messageMailScheduler = new MessageMailScheduler();
		messageMailScheduler.setTo(to);
		messageMailScheduler.setSubject(subject);
		messageMailScheduler.setName(name);
		messageMailScheduler.setHtmlBody(htmlBody);
		rabbitMQSender.sendMessageMailScheduler(messageMailScheduler);;//--------------------------------------------------------->RMQ
	}
	
	
	@Override
	public void sendMessageUsingThymeleafTemplateForceRefus(String to, String name, String subject,
			Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException {

		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);

		String htmlBody = thymeleafTemplateEngine.process(templateForceRefus, thymeleafContext);
		
		//sendHtmlMessage(to, name, subject, htmlBody);
		
		MessageMailScheduler messageMailScheduler = new MessageMailScheduler();
		messageMailScheduler.setTo(to);
		messageMailScheduler.setSubject(subject);
		messageMailScheduler.setName(name);
		messageMailScheduler.setHtmlBody(htmlBody);
		rabbitMQSender.sendMessageMailScheduler(messageMailScheduler);;//--------------------------------------------------------->RMQ
	}

	/**
	 * Cette méthode permet de créer un message HTML en renseignant le destinataire,
	 * le sujet, l'émetteur et en créant le htmlBody du mail
	 * 
	 * @param to
	 * @param subject
	 * @param htmlBody
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public void sendHtmlMessage(String to, String name, String subject, String htmlBody)
			throws MessagingException, UnsupportedEncodingException {

		MimeMessage message = eMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(new InternetAddress(to, name));
		helper.setSubject(subject);
		helper.setFrom(new InternetAddress(mail, nameFrom));

		helper.setText(htmlBody, true);

		eMailSender.send(message);
		
	}

}
