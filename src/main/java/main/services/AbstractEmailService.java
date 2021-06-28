package main.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import main.domain.OrdemServico;

public abstract class AbstractEmailService implements EmailService {
//	@Autowired
//	private TemplateEngine templateEngine;
//
//	@Autowired
//	private JavaMailSender javaMailSender;
//	
//	
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(OrdemServico obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromOrdem(obj);
		;
		sm = prepareSimpleMailMessageFromOrdem(obj);
		sendEmail(sm);
	}

	/**
	 * Cria um email baseada na classe ordem pedido
	 */
	protected SimpleMailMessage prepareSimpleMailMessageFromOrdem(OrdemServico obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject(obj.getEstado() + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
//	protected String htmlFromTemplatePedido(OrdemServico obj) {
//		Context context = new Context();
//		context.setVariable("Ordem", obj);
//		return templateEngine.process("email/confirmacao", context);
//	}
//
//	@Override
//	public void sendOrderConfirmationHtmlEmail(OrdemServico obj) {
//		try {
//			MimeMessage mm = prepareMimeMessageFromPedido(obj);
//			sendHtmlEmail(mm);
//		}
//		catch (MessagingException e) {
//			sendOrderConfirmationEmail(obj);
//		}
//	}

//	protected MimeMessage prepareMimeMessageFromPedido(OrdemServico obj) throws MessagingException {
//		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
//		mmh.setTo(obj.getCliente().getEmail());
//		mmh.setFrom(sender);
//		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
//		mmh.setSentDate(new Date(System.currentTimeMillis()));
//		mmh.setText(htmlFromTemplatePedido(obj), true);
//		return mimeMessage;
//	}

}
