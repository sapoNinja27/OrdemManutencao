package main.dto;

import java.io.Serializable;

public class MarcaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String equipamento;
	
	public MarcaDTO() {
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	
	
}
