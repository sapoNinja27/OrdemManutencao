package main.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.domain.Cliente;
import main.dto.cliente.ClienteNovoDTO;
import main.dto.cliente.ClienteUpdateDTO;
import main.services.ClienteService;
/**
*Endpoint para clientes
*/
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {
	@Autowired
	private ClienteService service;
	/**
	*Retorna um cliente baseado no id
	*/
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	/**
	*Retorna todos os clientes
	*/
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Cliente>> findAll() {
		List<Cliente> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	/**
	*Adiciona um novo cliente
	*/
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNovoDTO objDto) {
		Cliente obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	/**
	*Edita um cliente baseado no id
	*/
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteUpdateDTO objDto, @PathVariable Integer id) {
		service.updateCliente(id,objDto);
		return ResponseEntity.noContent().build();
	}
	/**
	*Exclui um cliente baseado no id
	*/
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}