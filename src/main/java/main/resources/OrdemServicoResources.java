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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.domain.OrdemServico;
import main.domain.enums.EstadoOrdemServico;
import main.dto.ordem.servico.OrdemServicoAnalizeDTO;
import main.dto.ordem.servico.OrdemServicoNovoDTO;
import main.dto.ordem.servico.OrdemServicoUpdateDTO;
import main.services.EmailService;
import main.services.OrdemServicoService;

@RestController
@RequestMapping(value = "/ordens")
public class OrdemServicoResources {

	@Autowired
	private OrdemServicoService service;

	@Autowired
	private EmailService emailService;
	
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
	public ResponseEntity<Void> insert(@Valid @RequestBody OrdemServicoNovoDTO objDto) {
		OrdemServico obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	//PUT PARA EDITAR O PEDIDO
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrdemServicoUpdateDTO objDto, @PathVariable Integer id) {
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
	//PUT PARA Cliente CONFIRMAR
		@RequestMapping(value = "/confirmar/{key}", method = RequestMethod.PUT)
		public ResponseEntity<Void> confirmar(@PathVariable String key) {
			String[] parse=key.split(":");
			
			int id=Integer.valueOf(parse[1]);
			key=parse[2];
			OrdemServico obj = service.find(id);
			if(obj.getSerialKey().equals(key)) {
				obj.setState(EstadoOrdemServico.MANUTENCAO_PENDENTE);
			}
			obj = service.save(obj);
			return ResponseEntity.noContent().build();
		}
	//PUT PARA RECUSAR
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/recusar", method = RequestMethod.PUT)
	public ResponseEntity<Void> recusar(@PathVariable Integer id) {
		OrdemServico obj = service.find(id);
		obj.setState(EstadoOrdemServico.RECUSADO);
		obj = service.save(obj);
		return ResponseEntity.noContent().build();
	}
	//PUT PARA TERMINAR A ANALIZE
		@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
		@RequestMapping(value = "/{id}/analizar", method = RequestMethod.PUT)
		public ResponseEntity<Void> analizar(@Valid @RequestBody OrdemServicoAnalizeDTO objDto, @PathVariable Integer id) {
			OrdemServico obj =service.find(id);
			obj = service.analizar(obj,objDto);
			return ResponseEntity.noContent().build();
		}
	//PUT PARA FINALIZAR
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/finalizar", method = RequestMethod.PUT)
	public ResponseEntity<Void> finalizar(@Valid @PathVariable Integer id) {
		OrdemServico obj = service.find(id);
		obj.setState(EstadoOrdemServico.CONCLUIDO);
		emailService.sendOrderConfirmationEmail(obj);
		obj = service.save(obj);
		return ResponseEntity.noContent().build();
	}
	//post das imagens
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value="/{id}/imagens", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProblemPictures(@PathVariable Integer id,@RequestParam(name="file") MultipartFile file) {
		System.out.println("zasadpkdspogasdogjsda"+id);
		URI uri = service.uploadProblemPicture(file,id);
		return ResponseEntity.created(uri).build();
	}
}
