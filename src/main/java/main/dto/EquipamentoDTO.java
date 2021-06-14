package main.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class EquipamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;
	@NotEmpty(message = "Preenchimento obrigatório")
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
