package main.services;

import org.springframework.mail.SimpleMailMessage;

import main.domain.OrdemServico;

public interface EmailService {

	void sendOrderConfirmationEmail(OrdemServico obj);

	void sendEmail(SimpleMailMessage msg);

}
