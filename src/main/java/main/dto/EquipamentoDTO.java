package main.dto;

import java.io.Serializable;

public class EquipamentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String tipo;
	private Integer marca;
	public EquipamentoDTO() {
		
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getMarca() {
		return marca;
	}
	public void setMarca(Integer marca) {
		this.marca = marca;
	}
	
}
