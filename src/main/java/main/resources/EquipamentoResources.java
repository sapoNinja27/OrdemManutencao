package main.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.domain.Equipamento;
import main.domain.Marca;
import main.dto.EquipamentoDTO;
import main.services.EquipamentoService;
import main.services.MarcaService;

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResources {

	@Autowired
	private EquipamentoService equipamentoService;
	@Autowired
	private MarcaService marcaService;
	
		
	@RequestMapping(value = "/tipos",method = RequestMethod.POST)
	public ResponseEntity<Void> insertT(@Valid @RequestBody EquipamentoDTO objDto) {
		Equipamento obj=equipamentoService.fromDTO(objDto);
		equipamentoService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	@RequestMapping(value = "/marcas",method = RequestMethod.POST)
	public ResponseEntity<Void> insertM(@Valid @RequestBody Marca obj) {
		marcaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/marcas", method = RequestMethod.GET)
	public ResponseEntity<List<Marca>> findTAll() {
		return ResponseEntity.ok().body(marcaService.findAll());
	}
	@RequestMapping(value="{marca}/tipos" ,method = RequestMethod.GET)
	public ResponseEntity<List<Equipamento>> findAllByMarca(@PathVariable Integer marca) {
		List<Equipamento> list = equipamentoService.findAll(marca);
		return ResponseEntity.ok().body(list);
	}
	@RequestMapping(value="/tipos" ,method = RequestMethod.GET)
	public ResponseEntity<List<Equipamento>> findAll() {
		List<Equipamento> list = equipamentoService.findAll();
		return ResponseEntity.ok().body(list);
	}
}
