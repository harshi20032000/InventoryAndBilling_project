package com.harshi_solution.inventorymate.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	//@Value("${com.harshi.flightReservation.itinerary.email.body}")
	private String EMAIL_BODY;

	//@Value("${com.harshi.flightReservation.itinerary.email.subject}")
	private String EMAIL_SUBJECT;

	@Autowired
	private JavaMailSender mailSender;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
	
	/**
	 * put logic to segregate saveReservation itinerary and updatereservation
	 * itinerary mail should be more clear about what happened.
	 */
	
	public void sendItinerary(String toAddress, String filePath) {
		LOGGER.info("Inside sendItinerary() on EmailUtil");
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setTo(toAddress);
			mimeMessageHelper.setSubject(EMAIL_SUBJECT);
			mimeMessageHelper.setText(EMAIL_BODY);
			mimeMessageHelper.addAttachment("Itinerary", new File(filePath));
			mailSender.send(mimeMessage);
			LOGGER.info("Mail sent successfully to email - "+toAddress);
		} catch (MessagingException e) {
			LOGGER.error("errorMessage:"+e);
			e.printStackTrace();
		}
		;
	}
}
