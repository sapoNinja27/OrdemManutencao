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

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResources {

	@Autowired
	private EquipamentoService equipamentoService;
	@Autowired
	private MarcaService marcaService;
	
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/tipos",method = RequestMethod.POST)
	public ResponseEntity<Void> insertT(@Valid @RequestBody EquipamentoDTO objDto) {
		Marca mark=marcaService.find(objDto.getMarca());
		Equipamento novoEquip=equipamentoService.fromDTO(objDto);
		
		Equipamento equip=equipamentoService.find(novoEquip.getNome(),mark);
		boolean novo=true;
		if(equip!=null) {
			novo=false;
		}
		if(novo) {
			mark.addEquipamento(novoEquip);
			equipamentoService.insert(novoEquip);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoEquip.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}
		return null;
	}
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/marcas",method = RequestMethod.POST)
	public ResponseEntity<Void> insertM(@Valid @RequestBody MarcaDTO objDto) {
		Marca marca=marcaService.find(objDto.getNome());
		if(marca==null) {
			String equipamento=objDto.getEquipamento();
			Marca newMarca=new Marca(objDto.getNome());
			Equipamento newEquip= new Equipamento(equipamento,newMarca);
			newEquip.setMarca(newMarca);
			newMarca.addEquipamento(newEquip);
			marcaService.insert(newMarca);
			equipamentoService.insert(newEquip);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newMarca.getId()).toUri();
			return ResponseEntity.created(uri).build();
		}else {
			String equipamento=objDto.getEquipamento();
			Equipamento equip=equipamentoService.find(equipamento,marca);
			boolean novo=true;
			if(equip!=null) {
				novo=false;
			}
			if(novo) {
				Equipamento newEquip= new Equipamento(equipamento,marca);
				newEquip.setMarca(marca);
				marca.addEquipamento(newEquip);
				equipamentoService.insert(newEquip);
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newEquip.getId()).toUri();
				return ResponseEntity.created(uri).build();
			}
			return null;
		}
	}

	@RequestMapping(value = "/marcas", method = RequestMethod.GET)
	public ResponseEntity<List<Marca>> findTAll() {
		return ResponseEntity.ok().body(marcaService.findAll());
	}
	
	
	@RequestMapping(value="/tipos" ,method = RequestMethod.GET)
	public ResponseEntity<List<Equipamento>> findAll() {
		List<Equipamento> list = equipamentoService.findAll();
		return ResponseEntity.ok().body(list);
	}
}
