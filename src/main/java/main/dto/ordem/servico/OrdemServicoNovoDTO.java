package main.dto.ordem.servico;

import java.io.Serializable;
import java.util.Date;

public class OrdemServicoNovoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer equipamento;
	
	private Date dataEntrada;
	private String problema;
	
	private Integer cliente;
	
	public OrdemServicoNovoDTO() {
		
	}

	public Integer getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Integer equipamento) {
		this.equipamento = equipamento;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}
	

	
	
}
