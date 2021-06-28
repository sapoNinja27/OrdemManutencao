package main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import main.domain.Usuario;
import main.repositories.UsuarioRepository;
import main.security.UserSS;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UsuarioRepository repo;

	@Override
	public UserDetails loadUserByUsername(String nome) throws UsernameNotFoundException {
		Usuario user = repo.findByNome(nome);
		if (user == null) {
			throw new UsernameNotFoundException(nome);
		}
		return new UserSS(user.getId(), user.getNome(), user.getSenha(), user.getPerfil());
	}

}
