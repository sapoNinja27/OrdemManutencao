package main.dto.ordem.servico;

import java.io.Serializable;

public class OrdemServicoUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer equipamento;
	
	private String problema;
	
	
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


}
