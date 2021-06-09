package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
	@Transactional(readOnly=true)
	List<Equipamento> findAllByNome(String nome);
	@Transactional(readOnly=true)
	Equipamento findByNome(String nome);
}
