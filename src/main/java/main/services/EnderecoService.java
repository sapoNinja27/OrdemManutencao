package main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Endereco;
import main.repositories.EnderecoRepository;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {
	
	
	@Autowired
	private EnderecoRepository repo;
	
	public Endereco find(Integer id) {
		Optional<Endereco> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"endereco nao encontrad! Id: " + id ));
	}
	
	@Transactional
	public Endereco insert(Endereco obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}
	
	public Endereco update(Endereco obj) {
		Endereco newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente com manutenção pendente");
		}
	}
	private void updateData(Endereco newObj, Endereco obj) {
		newObj.setBairro(obj.getBairro());
		newObj.setCidade(obj.getCidade());
	}

}
