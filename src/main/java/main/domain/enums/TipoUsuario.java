package main.domain.enums;


public enum TipoUsuario {
	RECEPCIONISTA(1, "ROLE_RECEPCIONISTA"),
	ANALISTA(2, "ROLE_ANALISTA"),
	TECNICO(3, "ROLE_TECNICO"),
	ADMIN(4, "ROLE_ADMIN");

	private int cod;
	private String descricao;
	 
	public static Integer totalTipos() {
		return TipoUsuario.values().length;
	}
	private TipoUsuario(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	public static String toString(TipoUsuario usr) {
		if (usr == null) {
			return null;
		}
		return usr.getDescricao();
	}
	public static TipoUsuario toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoUsuario x : TipoUsuario.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}
