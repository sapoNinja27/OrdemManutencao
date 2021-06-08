package main.dto.ordem.servico;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;

import main.domain.OrdemServico;

public class OrdemServicoAnalizeDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	
	private String problemasExtras;
	@ElementCollection
	@CollectionTable(name = "FOTOS")
	private Set<String> fotos = new HashSet<String>();

	public OrdemServicoAnalizeDTO() {

	}

	public OrdemServicoAnalizeDTO(OrdemServico obj) {
		problemasExtras=obj.getProblemasExtras();
		fotos=obj.getFotos();
	}
	public String getProblemasExtras() {
		return problemasExtras;
	}

	public void setProblemasExtras(String problemasExtras) {
		this.problemasExtras = problemasExtras;
	}

	public Set<String> getFotos() {
		Set<String> f2=fotos;
		return f2;
	}
	public void setFotos(Set<String> fotos) {
		this.fotos=fotos;
	}



}
