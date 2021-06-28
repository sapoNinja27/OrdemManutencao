package main.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Equipamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;

	@JsonIgnore
	@OneToMany(mappedBy = "equipamento")
	private List<OrdemServico> ordens = new ArrayList<OrdemServico>();

	public Equipamento() {

	}

	public Equipamento(String nome, Marca marca) {
		super();
		this.nome = nome;
		this.marca = marca;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMarca() {
		return marca.getNome();
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<OrdemServico> getOrdens() {
		return ordens;
	}

	public void addOrdem(OrdemServico ordem) {
		this.ordens.add(ordem);
	}

	public void setOrdens(List<OrdemServico> ordens) {
		this.ordens = ordens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
