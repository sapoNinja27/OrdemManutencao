package main.dto.usuario;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import main.services.validation.UsuarioInsert;

@UsuarioInsert
public class UsuarioNovoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String nome;
	

	public UsuarioNovoDTO() {
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	

}
