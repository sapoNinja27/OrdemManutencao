package main.dto.usuario;

import java.io.Serializable;

import main.domain.enums.TipoUsuario;

public class UsuarioPerfilDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer[] perfis = new Integer[TipoUsuario.totalTipos()];

	public UsuarioPerfilDTO() {
	}

	public Integer[] getPerfis() {
		return perfis;
	}

	public void setPerfis(Integer[] perfis) {
		this.perfis = perfis;
	}

}
