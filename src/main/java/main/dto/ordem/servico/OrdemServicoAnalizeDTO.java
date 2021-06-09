package main.dto.ordem.servico;

import java.io.Serializable;

import main.domain.OrdemServico;

public class OrdemServicoAnalizeDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	
	private String problemasExtras;

	public OrdemServicoAnalizeDTO() {

	}

	public OrdemServicoAnalizeDTO(OrdemServico obj) {
		problemasExtras=obj.getProblemasExtras();
	}
	public String getProblemasExtras() {
		return problemasExtras;
	}

	public void setProblemasExtras(String problemasExtras) {
		this.problemasExtras = problemasExtras;
	}

	



}
