package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	/**
	 * Retorna um usuario por nome
	 */
	@Transactional(readOnly = true)
	Usuario findByNome(String nome);
}
