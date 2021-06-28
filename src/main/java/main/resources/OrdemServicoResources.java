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
import main.dto.ordem.servico.OrdemServicoAnalizeDTO;
import main.dto.ordem.servico.OrdemServicoNovoDTO;
import main.dto.ordem.servico.OrdemServicoUpdateDTO;
import main.services.OrdemServicoService;

/**
 * EndPoint para ordens de servço
 */
@RestController
@RequestMapping(value = "/ordens")
public class OrdemServicoResources {
	@Autowired
	private OrdemServicoService service;

	/**
	 * Buscar ordem por id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrdemServico> find(@PathVariable Integer id) {
		OrdemServico obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Listar todas as ordens
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrdemServico>> findAll() {
		List<OrdemServico> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Pegar ordens baseadas em um cliente expecifico
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<OrdemServico>> findAllByCliente(@PathVariable Integer id) {
		List<OrdemServico> list = service.findAllByCliente(id);
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Adicionar ordem de serviço
	 */
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody OrdemServicoNovoDTO objDto) {
		OrdemServico obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	/**
	 * Editar a ordem de serviço
	 */
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody OrdemServicoUpdateDTO objDto, @PathVariable Integer id) {
		service.update(id, objDto);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Responde a um link criado com a encriptação do nome do cliente e a data da
	 * ordem o link virá em um formato(palavra=key=id) usando a função split do java
	 * é coletado e separado no caractere "=" é feita a requisição para liberar o
	 * pedido id=? enviando como variavel auxiliar a key
	 */
	@RequestMapping(value = "/confirmar/{key}", method = RequestMethod.GET)
	public ResponseEntity<String> confirmar(@PathVariable String key) {
		String[] parse = key.split("=");
		int id = Integer.valueOf(parse[2]);
		key = (parse[1]);
		key = key.replace("value", "");
		service.confirmar(id, key);
		return ResponseEntity.ok().body("Confirmado");
	}

	/**
	 * Cancela a ordem de serviço Somente caso o cliente não aceite continuar com o
	 * pedido
	 */
	@PreAuthorize("hasAnyRole('ADMIN','RECEPCIONISTA')")
	@RequestMapping(value = "/{id}/cancelar", method = RequestMethod.PUT)
	public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
		service.cancelar(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Recusa a ordem de serviço Somente caso a empresa não aceite realizar o pedido
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/recusar", method = RequestMethod.PUT)
	public ResponseEntity<Void> recusar(@PathVariable Integer id) {
		service.recusar(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Adiciona a analize do tecnico e define que a empresa aceitou realizar o
	 * pedido
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/analizar", method = RequestMethod.PUT)
	public ResponseEntity<Void> analizar(@Valid @RequestBody OrdemServicoAnalizeDTO objDto, @PathVariable Integer id) {
		service.analizar(id, objDto);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Finaliza a ordem marcando como concluida
	 */
	@PreAuthorize("hasAnyRole('ADMIN','TECNICO')")
	@RequestMapping(value = "/{id}/finalizar", method = RequestMethod.PUT)
	public ResponseEntity<Void> finalizar(@Valid @PathVariable Integer id) {
		service.concluir(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Recebe uma imagem que sera adicionada ao pedido
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ANALISTA')")
	@RequestMapping(value = "/{id}/imagens", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadProblemPictures(@PathVariable Integer id,
			@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadProblemPicture(file, id);
		return ResponseEntity.created(uri).build();
	}
}
