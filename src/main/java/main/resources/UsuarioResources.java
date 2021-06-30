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

import main.domain.Usuario;
import main.dto.usuario.UsuarioNovoDTO;
import main.dto.usuario.UsuarioPerfilDTO;
import main.dto.usuario.UsuarioUpdateDTO;
import main.services.UsuarioService;

/**
 * Endpoint para usuarios
 */
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {
	@Autowired
	private UsuarioService service;

	/**
	 * Buscar usuario por id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Integer id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Buscar usuario por nome
	 */
	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findByNome(@PathVariable String nome) {
		Usuario obj = service.buscarPeloNome(nome);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * Buscar todos os usuarios
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Inserir novo usuario
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNovoDTO objDto) {
		Usuario obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	/**
	 * Modificar usuario(Nome, Nome de usuario e Senha)
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioUpdateDTO objDto, @PathVariable Integer id) {
		service.update(id, objDto);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Adicionar cargos para o usuario
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/cargos/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> AddProfile(@Valid @RequestBody UsuarioPerfilDTO objDto, @PathVariable Integer id) {
		service.adicionarCargos(id, objDto);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Excluir um usuario
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Atualizar a imagem de usuario
	 */
	@RequestMapping(value = "/imagem/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> uploadPictures(@PathVariable Integer id,
			@RequestParam(name = "file") MultipartFile file) {
		URI uri = service.uploadPicture(file, id);
		return ResponseEntity.created(uri).build();
	}
}
