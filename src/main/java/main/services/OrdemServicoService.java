package main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.OrdemServico;
import main.repositories.OrdemServicoRepository;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {
	
	
	@Autowired
	private OrdemServicoRepository repo;
	
	
	public OrdemServico find(Integer id) {
		Optional<OrdemServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrado! Id: " + id ));
	}
	
	public OrdemServico insert(OrdemServico obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
