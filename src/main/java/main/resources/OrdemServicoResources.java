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

import main.domain.OrdemServico;
import main.domain.enums.EstadoOrdemServico;
import main.dto.OrdemServicoDTO;
import main.dto.OrdemServicoNewDTO;
import main.services.OrdemServicoService;

@RestController
@RequestMapping(value = "/ordens")
public class OrdemServicoResources {

	@Autowired
	private OrdemServicoService service;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrdemServico> find(@PathVariable Integer id) {
		OrdemServico obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<List<OrdemServico>> findAll() {
		List<OrdemServico> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrdemServicoNewDTO objDto) {
		OrdemServico obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrdemServicoNewDTO objDto, @PathVariable Integer id) {
		//TODO 
//		private Integer equipamento; pode ser modificado
//		
//		private Date dataEntrada; nao pode ser modificada
//		private String problema; pode ser modificado
//		
//		private Integer cliente; nao pode ser modificada
		OrdemServico obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/recusar", method = RequestMethod.PUT)
	public ResponseEntity<Void> recusar(@PathVariable Integer id) {
		OrdemServico obj = service.find(id);
		obj.setState(EstadoOrdemServico.RECUSADO);
		obj = service.recusar(obj);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/finalizacoes", method = RequestMethod.PUT)
	public ResponseEntity<Void> finish(@Valid @RequestBody OrdemServicoDTO objDto, @PathVariable Integer id) {
		OrdemServico obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}
