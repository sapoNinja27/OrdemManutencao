package main.dto;

import java.io.Serializable;

public class EquipamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nome;
	private String marca;
	public EquipamentoDTO() {
		
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	
}
