package main.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Cliente;
import main.domain.Equipamento;
import main.domain.OrdemServico;
import main.dto.OrdemServicoDTO;
import main.dto.OrdemServicoNewDTO;
import main.repositories.OrdemServicoRepository;
import main.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository repo;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EquipamentoService equipamentoService;

	public OrdemServico find(Integer id) {
		Optional<OrdemServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Ordem de serviço não encontrado! Id: " + id));
	}
	public List <OrdemServico> findAll() {
		return repo.findAll();
	}
	public OrdemServico insert(OrdemServico obj) {
		obj.setId(null);
		obj = repo.save(obj);
		return obj;
	}

	public OrdemServico update(OrdemServico obj) {
		OrdemServico newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	public OrdemServico recusar(OrdemServico obj) {
		
		return repo.save(obj);
	}

	private void updateData(OrdemServico newObj, OrdemServico obj) {
		newObj.setProblemasExtras(obj.getProblemasExtras());
		Set<String> fotos = obj.getFotos();
		for (String foto : fotos) {
			newObj.setFotos(foto);
		}
	}

	public OrdemServico fromDTO(OrdemServicoDTO objDto) {
		OrdemServico ord = new OrdemServico();
		ord.setProblemasExtras(objDto.getProblemasExtras());
		Set<String> fotos = objDto.getFotos();
		for (String foto : fotos) {
			ord.setFotos(foto);
		}
		return ord;
	}

	public OrdemServico fromDTO(OrdemServicoNewDTO objDto) {
		Cliente cli = clienteService.find(objDto.getCliente());
		Equipamento equip = equipamentoService.find(objDto.getEquipamento());
		OrdemServico ord = new OrdemServico(cli, equip, new Date(System.currentTimeMillis()), objDto.getProblema());
		equip.addOrdem(ord);
		equipamentoService.update(equip);
		return ord;
	}

}
