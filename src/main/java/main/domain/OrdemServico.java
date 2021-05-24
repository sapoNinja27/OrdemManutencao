package main.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Entity
public class OrdemServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "ordemServico")
	private Equipamento equipamento;
	
	private Date dataEntrada;
	private String problema;
	private String problemasExtras;
	@ElementCollection
	@CollectionTable(name = "FOTOS")
	private Set<String> fotos = new HashSet<String>();
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	public OrdemServico() {
		
	}
	public OrdemServico(Cliente cliente,Equipamento equipamento, Date dataEntrada, String problema) {
		super();
		this.cliente=cliente;
		this.equipamento = equipamento;
		this.dataEntrada = dataEntrada;
		this.problema=problema;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Equipamento getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}
	public Date getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	public String getProblema() {
		return this.problema;
	}
	public void setProblema(String problema) {
		this.problema=problema;
	}
	
	public String getProblemasExtras() {
		return problemasExtras;
	}
	public void setProblemasExtras(String problemasExtras) {
		this.problemasExtras = problemasExtras;
	}
	
	public Set<String> getFotos() {
		Set<String> f2=fotos;
		return f2;
	}
	public void setFotos(String fotos) {
		this.fotos.add(fotos);
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
