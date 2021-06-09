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

@Service
public class EquipamentoService {
	
	
	@Autowired
	private EquipamentoRepository repo;
	@Autowired
	private MarcaService marcaService;
	
	public Equipamento find(Integer id) {
		Optional<Equipamento> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Equipamento não encontrado! Id: " + id ));
	}
	private List<Equipamento> findAll(String nome) {
		return repo.findAllByNome(nome);
	}
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
	public Equipamento update(Equipamento obj) {
		return repo.save(obj);
	}
	@Transactional
	public Equipamento insert(Equipamento obj) {
		obj.setId(null);
		obj.setNome(formatar(obj.getNome()));
		obj = repo.save(obj);
		return obj;
	}
	private String formatar(String palavra) {
		palavra=palavra.toLowerCase();
		String primeiraLetra=palavra.substring(0,1).toUpperCase();
		palavra=primeiraLetra+palavra.substring(1);
		return palavra;
	}
	public List<Equipamento> findAll() {
		return repo.findAll();
	}
	public Equipamento fromDTO(EquipamentoDTO objDto) {
		Marca marca=marcaService.find(formatar(objDto.getMarca()));
		return new Equipamento(formatar(objDto.getNome()),marca);
	}

}
