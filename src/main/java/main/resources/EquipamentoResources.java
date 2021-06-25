package main.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.domain.Equipamento;
import main.domain.Marca;
import main.dto.EquipamentoDTO;
import main.dto.MarcaDTO;
import main.services.EquipamentoService;
import main.services.MarcaService;
/**
*EndPoint para equipamentos
*/
@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResources {
	@Autowired
	private EquipamentoService equipamentoService;
	@Autowired
	private MarcaService marcaService;
	/**
	*Retorna todas as marcas
	*/
	@RequestMapping(value = "/marcas", method = RequestMethod.GET)
	public ResponseEntity<List<Marca>> findTAll() {
		return ResponseEntity.ok().body(marcaService.findAll());
	}
	/**
	*Retorna todos os equipamentos
	*/
	@RequestMapping(value="/tipos" ,method = RequestMethod.GET)
	public ResponseEntity<List<Equipamento>> findAll() {
		return ResponseEntity.ok().body(equipamentoService.findAll());
	}
	/**
	*adiciona uma marca
	*/
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/marcas",method = RequestMethod.POST)
	public ResponseEntity<Void> insertM(@Valid @RequestBody MarcaDTO objDto) {
		Marca obj= marcaService.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	/**
	*adiciona um equipamento
	*/
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/tipos",method = RequestMethod.POST)
	public ResponseEntity<Void> insertT(@Valid @RequestBody EquipamentoDTO objDto) {
		Equipamento obj = equipamentoService.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}