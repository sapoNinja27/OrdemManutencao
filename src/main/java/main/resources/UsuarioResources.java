package main.resources;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.domain.Usuario;
import main.domain.enums.TipoUsuario;
import main.dto.usuario.UsuarioNovoDTO;
import main.dto.usuario.UsuarioPerfilDTO;
import main.dto.usuario.UsuarioUpdateDTO;
import main.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResources {

	@Autowired
	private UsuarioService service;
	@Autowired
	private BCryptPasswordEncoder pe;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> find(@PathVariable Integer id) {
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}


	@RequestMapping(value = "/nome/{nome}", method = RequestMethod.GET)
	public ResponseEntity<Usuario> findByNome(@PathVariable String nome) {
		Usuario obj = service.buscarPeloNome(nome);
		return ResponseEntity.ok().body(obj);
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNovoDTO objDto) {
		Usuario obj=new Usuario();
		obj.setNome(objDto.getNome());
		obj.setSenha(pe.encode("1"));
		obj.setNomeNormal(objDto.getNome());
		service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	//alterar nome e senha
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioUpdateDTO objDto, @PathVariable Integer id) {
		Usuario obj=service.find(id);
		if(objDto.getSenha()!=null) {
			obj.setSenha(pe.encode(objDto.getSenha()));
		}
		if(objDto.getNome()!=null) {
			obj.setNome((objDto.getNome()));
		}
		if(objDto.getNomeNormal()!=null) {
			obj.setNomeNormal((objDto.getNomeNormal()));
		}
		service.atualizar(obj);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}/cargos", method = RequestMethod.PUT)
	public ResponseEntity<Void> AddProfile(@Valid @RequestBody UsuarioPerfilDTO objDto, @PathVariable Integer id) {
		Usuario obj=service.find(id);
		if(objDto.getPerfis().length>0) {
			Set<TipoUsuario> tipos=new HashSet<TipoUsuario>();
			obj.setPerfis(null);
			for (Integer index : objDto.getPerfis()) {
				if(index>0 && index<= TipoUsuario.totalTipos()) {
					tipos.add(TipoUsuario.toEnum(index));
				}
			}
			obj.setPerfis(tipos);
			obj = service.atualizar(obj);
		}
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	//atualizar imagem de usuario
	@PreAuthorize("hasAnyRole('ADMIN')")
		@RequestMapping(value="/{id}/imagens", method=RequestMethod.POST)
		public ResponseEntity<Void> uploadPictures(@PathVariable Integer id,@RequestParam(name="file") MultipartFile file) {
			URI uri = service.uploadPicture(file,id);
			return ResponseEntity.created(uri).build();
		}
}
