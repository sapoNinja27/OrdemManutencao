package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
	/**
	 * Retorna todos os equipamentos com o mesmo nome
	 */
	@Transactional(readOnly = true)
	List<Equipamento> findAllByNome(String nome);
}
