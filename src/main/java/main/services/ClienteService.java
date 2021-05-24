package main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Cliente;
import main.domain.Endereco;
import main.dto.ClienteDTO;
import main.dto.ClienteNewDTO;
import main.repositories.ClienteRepository;
import main.repositories.EnderecoRepository;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoRepository endRepo;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Ordem de serviço não encontrado! Id: " + id ));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		endRepo.save(obj.getEndereco());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
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

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getNome(),objDto.getTelefone(), objDto.getEmail(), null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente( objDto.getNome(),objDto.getTelefone(), objDto.getEmail(), objDto.getCpf());
		Endereco end = new Endereco(cli,objDto.getBairro(), objDto.getCidade());
		cli.setEndereco(end);
		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		newObj.setTelefone(obj.getTelefone());
	}

}
