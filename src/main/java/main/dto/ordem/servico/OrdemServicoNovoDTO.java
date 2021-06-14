package main.dto.ordem.servico;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

public class OrdemServicoNovoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String equipamento;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String marca;
	@NotEmpty(message = "Preenchimento obrigatório")
	private Date dataEntrada;
	@NotEmpty(message = "Preenchimento obrigatório")
	private String problema;
	@NotEmpty(message = "Preenchimento obrigatório")
	private Integer cliente;
	
	public OrdemServicoNovoDTO() {
		
	}

	

	public String getEquipamento() {
		return equipamento;
	}



	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
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
