package main.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Marca;
import main.repositories.MarcaRepository;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class MarcaService {
	
	
	@Autowired
	private MarcaRepository repo;
	
	public Marca find(Integer id) {
		Optional<Marca> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Marca não encontrado! Id: " + id ));
	}
	public List<Marca> findAll() {
		return repo.findAll();
	}
	@Transactional
	public Marca insert(Marca obj) {
		obj.setId(null);
		obj.setEquipamentos(null);
		obj = repo.save(obj);
		return obj;
	}

}
