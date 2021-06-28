package main.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Cliente;
import main.domain.Endereco;
import main.dto.cliente.ClienteNovoDTO;
import main.dto.cliente.ClienteUpdateDTO;
import main.repositories.ClienteRepository;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

/**
 * Serviços de cliente
 */
@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repo;
	@Autowired
	private EnderecoService endService;

	/**
	 * Busca um cliente baseado no id e retorna
	 */
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Ordem de serviço não encontrado! Id: " + id));
	}

	/**
	 * Retorna todos os clientes
	 */
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	/**
	 * Adiciona um novo cliente
	 */
	@Transactional
	public Cliente insert(ClienteNovoDTO objDto) {
		Cliente obj = new Cliente(objDto.getNome(), objDto.getTelefone(), objDto.getEmail(), objDto.getCpf(),
				objDto.getRg());
		Endereco end = new Endereco(obj, objDto.getBairro(), objDto.getCidade());
		obj.setEndereco(end);
		obj.setId(null);
		save(obj);
		return obj;
	}

	/**
	 * Recebe um id e um cliente atualizado e insere ele no banco baseado no id,
	 * tambem atualiza o endereço ligado a este cliente
	 */
	public void update(Integer id, ClienteUpdateDTO objDto) {
		Cliente obj = find(id);
		Endereco end = endService.find(obj.getEndereco().getId());
		end.setBairro(objDto.getBairro());
		end.setCidade(objDto.getCidade());
		endService.update(end);
		obj.setEndereco(end);
		obj.setNome(objDto.getNome());
		obj.setEmail(objDto.getEmail());
		obj.setTelefone(objDto.getTelefone());
		save(obj);
	}

	/**
	 * Salva um cliente no banco
	 */
	private void save(Cliente obj) {
		repo.save(obj);
	}

	/**
	 * Exclui um cliente baseado no id
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
