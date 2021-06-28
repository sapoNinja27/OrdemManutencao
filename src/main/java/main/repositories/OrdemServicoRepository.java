package main.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.domain.OrdemServico;

@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Integer> {
	/**
	 * Retorna todos os serviços de um cliente pelo id dele
	 */
	@Transactional(readOnly = true)
	List<OrdemServico> findAllByCliente_id(Integer Id);
}
