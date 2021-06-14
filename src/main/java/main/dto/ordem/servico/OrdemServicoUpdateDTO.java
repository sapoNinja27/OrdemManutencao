package main.dto.ordem.servico;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class OrdemServicoUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer equipamento;
	private String problema;
	
	private Double valor;
	
	public OrdemServicoUpdateDTO() {
		
	}

	public Integer getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Integer equipamento) {
		this.equipamento = equipamento;
	}


	public String getProblema() {
		return problema;
	}

	public void setProblema(String problema) {
		this.problema = problema;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}


}
