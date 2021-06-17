package main.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import main.domain.Cliente;
import main.domain.Usuario;
import main.repositories.UsuarioRepository;
import main.security.UserSS;
import main.services.exceptions.AuthorizationException;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	
	@Autowired
	private UsuarioRepository repo;
	@Autowired
	private S3Service s3Service;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuario inesistente! Id: " + id ));
	}
	
	public Usuario buscarPeloNome(String nome) {
		
		Usuario obj = repo.findByNome(nome);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	@Transactional
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		return obj = repo.save(obj);
	}
	
	public Usuario update(Usuario obj) {
		UserSS user = UserService.authenticated();
		if (user == null ||  !obj.getNome().equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		Usuario newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	public Usuario atualizar(Usuario obj) {
		return repo.save(obj);
	}
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir");
		}
	}

	public List<Usuario> findAll() {
		return repo.findAll();
	}

	private void updateData(Usuario newObj, Usuario obj) {
		newObj.setNome(obj.getNome());
		newObj.setSenha(obj.getSenha());
	}
	public URI uploadPicture(MultipartFile multipartFile,Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartFile);

		Usuario ord = find(id);
		ord.setImagem(uri.toString());
		repo.save(ord);

		return uri;
	}
}
