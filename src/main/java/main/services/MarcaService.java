package main.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Equipamento;
import main.domain.Marca;
import main.dto.MarcaDTO;
import main.repositories.MarcaRepository;
import main.services.exceptions.ObjectNotFoundException;
/**
*Serviço de marca
*/
@Service
public class MarcaService {
	@Autowired
	private MarcaRepository repo;
	@Autowired
	/**
	*Busca por id
	*/
	private EquipamentoService equipamentoService;
	public Marca find(Integer id) {
		Optional<Marca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Marca não encontrado! Id: " + id ));
	}
	/**
	*Busca por nome
	*/
	public Marca find(String nome) {
		Marca obj = repo.findByNome(formatar(nome));
		if(obj==null) {
			return null;
		}
		return obj;
	}
	/**
	*Retorna todas as marcas
	*/
	public List<Marca> findAll() {
		return repo.findAll();
	}
	/**
	*Salva uma marca no banco
	*/
	public void update(Marca obj) {
		repo.save(obj);
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
	*Insere uma nova marca no banco
	*/
	@Transactional
	public Marca insert(MarcaDTO objDto) {
		String equipamento=objDto.getEquipamento();
		Marca obj=find(objDto.getNome());
		if(obj==null) {
			obj=new Marca(formatar(objDto.getNome()));
			obj.setId(null);
		}
		update(obj);
		Equipamento equip= new Equipamento(equipamento,obj);
		equip.setId(null);
		equipamentoService.insert(equip);
		return obj;
	}
}