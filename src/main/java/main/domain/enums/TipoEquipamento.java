package main.domain.enums;

public enum TipoEquipamento {
	FERRAMENTA(1, "Ferramenta De Trabalho"),
	ELETRONICO(2, "Aparelho Eletronico"),
	OUTRO(3, "Outro tipo de equipamento");

	private int cod;
	private String descricao;

	private TipoEquipamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoEquipamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoEquipamento x : TipoEquipamento.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}
