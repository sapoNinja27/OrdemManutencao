package main.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Equipamento;
import main.domain.Marca;
import main.dto.EquipamentoDTO;
import main.repositories.EquipamentoRepository;
import main.services.exceptions.ObjectNotFoundException;
/**
*Serviços de equipamentos
*/
@Service
public class EquipamentoService {
	@Autowired
	private EquipamentoRepository repo;
	@Autowired
	private MarcaService marcaService;
	/**
	*Busca por id
	*/
	public Equipamento find(Integer id) {
		Optional<Equipamento> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Equipamento não encontrado! Id: " + id ));
	}
	/**
	*Retorna lista com todos os equipamentos
	*/
	public List<Equipamento> findAll() {
		return repo.findAll();
	}
	/**
	*Retorna lista de equipamentos pelo nome
	*/
	private List<Equipamento> findAll(String nome) {
		return repo.findAllByNome(nome);
	}
	/**
	*Salva equipamento 
	*/
	private Equipamento update(Equipamento obj) {
		return repo.save(obj);
	}
	/**
	*Formata para ter a 1 letra maiuscula
	*/
	private String formatar(String palavra) {
		palavra=palavra.toLowerCase();
		String primeiraLetra=palavra.substring(0,1).toUpperCase();
		palavra=primeiraLetra+palavra.substring(1);
		return palavra;
	}
	/**
	*Retorna o equipamento com determinado nome e marca
	*/
	public Equipamento find(String nome, Marca marca) {
		List<Equipamento>list=findAll(formatar(nome));
		if(list.size()!=0) {
			for (Equipamento e : list) {
				if(e.getMarca()==marca.getNome()) {
					return e;
				}
			}
		}
		return null;
	}
	/**
	*insere equipamento baseado no DTO
	*/
	@Transactional
	public Equipamento insert(EquipamentoDTO objDto) {
		Marca marca=marcaService.find(objDto.getMarca());
		Equipamento obj=find(objDto.getNome(),marca);
		if(obj==null) {
			obj = new Equipamento(formatar(objDto.getNome()),marca);
			obj.setId(null);
			marca.addEquipamento(obj);
			marcaService.update(marca);
			update(obj);
		}
		return obj;
	}
	/**
	*insere equipamento
	*/
	public void insert(Equipamento obj) {
		Marca marca=marcaService.find(obj.getMarca());
		Equipamento objNew=find(obj.getNome(),marca);
		if(objNew==null) {
			objNew = new Equipamento(formatar(obj.getNome()),marca);
			objNew.setId(null);
			marca.addEquipamento(objNew);
			marcaService.update(marca);
			update(objNew);
		}else {
			update(obj);
		}
	}
}