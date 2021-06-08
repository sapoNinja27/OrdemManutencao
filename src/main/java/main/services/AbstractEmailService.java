package main.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import main.domain.OrdemServico;
import main.domain.enums.EstadoOrdemServico;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}")
	private String sender;
	@Override
	public void sendOrderConfirmationEmail(OrdemServico obj) {
		SimpleMailMessage sm  = prepareSimpleMailMessageFromOrdem(obj); ;
		if(obj.getState()==EstadoOrdemServico.ANALIZE_PENDENTE) {
			sm = prepareSimpleMailMessageFromOrdem(obj);
		}else if(obj.getState()==EstadoOrdemServico.CONFIRMACAO_PENDENTE) {
			sm = pedidoDeConfirmacao(obj);
		}else if(obj.getState()==EstadoOrdemServico.RECUSADO) {
			sm = mensagemCancelado(obj);
		}else if(obj.getState()==EstadoOrdemServico.CONCLUIDO) {
			sm = mensagemConcluido(obj);
		}else if(obj.getState()==EstadoOrdemServico.CANCELADO) {
			sm = mensagemCancelado(obj);
		}
		
		sendEmail(sm);
	}
	
	protected SimpleMailMessage pedidoDeConfirmacao(OrdemServico obj) {
		SimpleMailMessage sm=new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido foi aprovado pelo tecnico: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		StringBuilder builder = new StringBuilder();
		builder.append(obj.toString());
		builder.append("\n");
		builder.append("Para confirmar o pedido acesse o link abaixo: ");
		builder.append("\n");
		builder.append("http://localhost:8080/ordens/confirmar/"+"id:"+obj.getId()+":"+obj.getSerialKey());
		sm.setText(builder.toString());
		return sm;
	}
	protected SimpleMailMessage mensagemCancelado(OrdemServico obj) {
		SimpleMailMessage sm=new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido não foi concluido: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.getEstado());
		return sm;
	}
	protected SimpleMailMessage mensagemConcluido(OrdemServico obj) {
		SimpleMailMessage sm=new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido foi concluido e aguarda retirada: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.getEstado());
		return sm;
	}
	protected SimpleMailMessage prepareSimpleMailMessageFromOrdem(OrdemServico obj) {
		SimpleMailMessage sm=new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido registrado e sera encaminhado a analize: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	
}