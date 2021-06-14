package main.dto.ordem.servico;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class OrdemServicoUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer equipamento;
	@NotEmpty(message = "Preenchimento obrigatório")
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
