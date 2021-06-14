package main.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Cliente;
import main.domain.Usuario;
import main.domain.enums.TipoUsuario;
import main.repositories.UsuarioRepository;
import main.security.UserSS;
import main.services.exceptions.AuthorizationException;
import main.services.exceptions.DataIntegrityException;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	
	@Autowired
	private UsuarioRepository repo;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Usuario inesistente! Id: " + id ));
	}
	
	public Usuario buscarPeloNome(String nome) {
		
		UserSS user = UserService.authenticated();
		if (user == null ||  !nome.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
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

}
