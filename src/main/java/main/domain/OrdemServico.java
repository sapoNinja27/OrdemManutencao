package main.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import main.domain.enums.EstadoOrdemServico;
@Entity
public class OrdemServico implements Serializable {
	private static final long serialVersionUID = 1L;
	private final long encode=32;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "equipamento_id")
	private Equipamento equipamento;
	
	private Date dataEntrada;
	private String problema;
	private String problemasExtras;
	@JsonIgnore
	private String serialKey;
	@ElementCollection
	@CollectionTable(name = "FOTOS")
	private Set<String> fotos = new HashSet<String>();
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Enumerated
	private EstadoOrdemServico state;
	private Integer valor;
	public OrdemServico() {
		
	}
	public OrdemServico(Cliente cliente,Equipamento equipamento, Date dataEntrada, String problema) {
		super();
		this.cliente=cliente;
		this.equipamento = equipamento;
		this.dataEntrada = dataEntrada;
		this.problema=problema;
		this.serialKey=generateKey(cliente.getNome(),equipamento.getNome(),dataEntrada.toString());
		this.state=EstadoOrdemServico.ANALIZE_PENDENTE;
	}
	private String generateKey(String val1,String val2,String val3) {
		String key="";
		key=val1+val2+val3;
		key.replace(" ", "");
		char keyArray[]=key.toCharArray();
		int cod;
		key="";
		for(int i=0;i<keyArray.length;i++) {
			cod=keyArray[i];
			cod+=encode;
			key+=cod;
		}
		return key;
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
		if(state==EstadoOrdemServico.RECUSADO) {
			return "Pedido foi recusado antes de terminar a analize";
		}
		if(problemasExtras==null||problemasExtras=="") {
			return "Analize pendente";
		}
		return problemasExtras;
	}
	public void setProblemasExtras(String problemasExtras) {
		this.problemasExtras = problemasExtras;
	}
	
	public EstadoOrdemServico getState() {
		return state;
	}
	public String getEstado() {
		return EstadoOrdemServico.toString(state);
	}
	public void setState(EstadoOrdemServico state) {
		this.state = state;
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
	
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public String getSerialKey() {
		return serialKey;
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
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("Manutenção numero: ");
		builder.append(getId());
		builder.append("\n");
		builder.append("Data da Transição: ");
		builder.append(sdf.format(getDataEntrada()));
		builder.append("\n");
		builder.append("Cliente: ");
		builder.append(getCliente().getNome());
		builder.append("\n");
		builder.append("Equipamento : ");
		builder.append(getEquipamento().getNome());
		builder.append(" ");
		builder.append(getEquipamento().getMarca());
		builder.append("\n");
		builder.append("Imagens: ");
		builder.append("exemplo imagens");
		builder.append("\n");
		builder.append("Analize do tecnico: ");
		builder.append(getProblemasExtras());
		builder.append("\n");
		builder.append("Valor da manutenção: ");
		if(getValor()==0) {
			builder.append("Não definido");
		}else {
			builder.append(getValor());
		}
		builder.append("\n");
		return builder.toString();
	}
	
}
