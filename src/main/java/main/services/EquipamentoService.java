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
	
	@Transactional
	public Equipamento insert(Equipamento obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public List<Equipamento> findAll(Integer marca) {
		Marca obj=marcaService.find(marca);
		return repo.findByMarca(obj);
	}
	public Equipamento fromDTO(EquipamentoDTO objDto) {
		Marca marca=marcaService.find(objDto.getMarca());
		return new Equipamento(objDto.getTipo(),marca);
	}

}
