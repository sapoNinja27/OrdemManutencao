package main.dto.usuario;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class UsuarioUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
