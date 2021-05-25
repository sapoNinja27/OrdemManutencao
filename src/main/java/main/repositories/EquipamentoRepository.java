package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Equipamento;
import main.domain.Marca;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
	@Transactional(readOnly=true)
	List<Equipamento> findByMarca(Marca marca);
}
