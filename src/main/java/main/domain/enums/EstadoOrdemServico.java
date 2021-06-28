package main.domain.enums;

public enum EstadoOrdemServico {
	ANALIZE_PENDENTE(1, "Analize pendente"), RECUSADO(4, "Ordem de reparos recusada pela empresa"),
	CONFIRMACAO_PENDENTE(3, "Necessário confirmação do cliente"),
	CANCELADO(5, "Ordem de reparos cancelada pelo cliente"), MANUTENCAO_PENDENTE(2, "Manutenção pendente"),
	CONCLUIDO(6, "Manutenção finalizada");

	private int cod;
	private String descricao;

	private EstadoOrdemServico(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String toString(EstadoOrdemServico stt) {
		if (stt == null) {
			return null;
		}
		return stt.getDescricao();
	}

	public static EstadoOrdemServico toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (EstadoOrdemServico x : EstadoOrdemServico.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id invalido: " + cod);
	}
}
