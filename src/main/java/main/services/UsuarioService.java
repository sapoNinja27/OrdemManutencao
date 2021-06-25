package main.services;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import main.domain.Cliente;
import main.domain.Usuario;
import main.domain.enums.TipoUsuario;
import main.dto.usuario.UsuarioNovoDTO;
import main.dto.usuario.UsuarioPerfilDTO;
import main.dto.usuario.UsuarioUpdateDTO;
import main.repositories.UsuarioRepository;
import main.security.UserSS;
import main.services.exceptions.AuthorizationException;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;
/**
*Serviços de Usuario
*/
@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private S3Service s3Service;
	@Autowired
	private BCryptPasswordEncoder pe;
	/**
	*Retorna usuario por id
	*/
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuario inesistente! Id: " + id ));
	}
	/**
	*Retorna todos os usuarios
	*/
	public List<Usuario> findAll() {
		return repo.findAll();
	}
	/**
	*Retorna usuario por nome
	*/
	public Usuario buscarPeloNome(String nome) {
		Usuario obj = repo.findByNome(nome);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	/**
	*Recebe um objDto com perfis e adiciona ao usuario
	*/
	public void adicionarCargos(Integer id,UsuarioPerfilDTO objDto ) {
		Usuario obj=find(id);
		if(objDto.getPerfis().length>0) {
			Set<TipoUsuario> tipos=new HashSet<TipoUsuario>();
			obj.setPerfis(null);
			for (Integer index : objDto.getPerfis()) {
				if(index>0 && index<= TipoUsuario.totalTipos()) {
					tipos.add(TipoUsuario.toEnum(index));
				}
			}
			obj.setPerfis(tipos);
		}
		update(obj);
	}
	/**
	*Recebe um objDto que pode conter modificado(Nome, Nome de usuario e Senha) e atualiza os dados do usuario baseado no id 
	*/
	public void updateUser(Integer id, UsuarioUpdateDTO objDto) {
		Usuario obj=find(id);
		if(objDto.getSenha()!=null) {
			obj.setSenha(pe.encode(objDto.getSenha()));
		}
		if(objDto.getNome()!=null) {
			obj.setNome((objDto.getNome()));
		}
		if(objDto.getNomeNormal()!=null) {
			obj.setNomeNormal((objDto.getNomeNormal()));
		}
		update(obj);
	}
	/**
	*Insere um novo usuario
	*/
	public Usuario insert(UsuarioNovoDTO objDto) {
		Usuario obj=new Usuario();
		obj.setNome(objDto.getNome());
		obj.setSenha(pe.encode("1"));
		obj.setNomeNormal(objDto.getNome());
		obj.setId(null);
		update(obj);
		return obj ;
	}
	/**
	*Atualiza os dados de um usuario
	*/
	private void update(Usuario obj) {
		repo.save(obj);
	}
	/**
	*Excluir um usuario
	*/
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}
	/**
	*Recebe um file e um id, salva a imagem e adiciona o link dela no campo (imagem) do usuario
	*/
	public URI uploadPicture(MultipartFile multipartFile,Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		URI uri = s3Service.uploadFile(multipartFile);
		Usuario ord = find(id);
		ord.setImagem(uri.toString());
		update(ord);
		return uri;
	}
}
