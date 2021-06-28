package main.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import main.domain.Endereco;
import main.repositories.EnderecoRepository;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

/**
 * Serviços de endereço
 */
@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repo;

	/**
	 * Retorna endereço pelo id
	 */
	public Endereco find(Integer id) {
		Optional<Endereco> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("endereco nao encontrad! Id: " + id));
	}

	/**
	 * Insere novo endereço
	 */
	public Endereco insert(Endereco obj) {
		obj.setId(null);
		save(obj);
		return obj;
	}

	/**
	 * Altera o endereço
	 */
	public void update(Endereco obj) {
		Endereco newObj = find(obj.getId());
		newObj.setBairro(obj.getBairro());
		newObj.setCidade(obj.getCidade());
		save(newObj);
	}

	/**
	 * Salva o endereço
	 */
	private void save(Endereco obj) {
		repo.save(obj);
	}

	/**
	 * Exclui o endereço
	 */
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente com manutenção pendente");
		}
	}

}
