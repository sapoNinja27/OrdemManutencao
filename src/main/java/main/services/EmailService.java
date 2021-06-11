package main.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import main.domain.OrdemServico;

public interface EmailService {

	void sendOrderConfirmationEmail(OrdemServico obj);

	void sendEmail(SimpleMailMessage msg);
	
//	void sendOrderConfirmationHtmlEmail(OrdemServico obj);
//
//	void sendHtmlEmail(MimeMessage msg);
}
