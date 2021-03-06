package main.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
	/**
	 * Retorna a marca com o determinado nome
	 */
	@Transactional(readOnly = true)
	Marca findByNome(String nome);
}
