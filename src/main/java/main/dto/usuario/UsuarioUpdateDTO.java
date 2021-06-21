package main.dto.usuario;

import java.io.Serializable;

import main.services.validation.UsuarioUpdate;

@UsuarioUpdate
public class UsuarioUpdateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeNormal;
	private String nome;
	private String senha;

	
	public String getNomeNormal() {
		return nomeNormal;
	}

	public void setNomeNormal(String nomeNormal) {
		this.nomeNormal = nomeNormal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
