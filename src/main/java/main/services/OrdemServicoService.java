package main.services;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import main.domain.Cliente;
import main.domain.Equipamento;
import main.domain.Marca;
import main.domain.OrdemServico;
import main.domain.enums.EstadoOrdemServico;
import main.dto.ordem.servico.OrdemServicoAnalizeDTO;
import main.dto.ordem.servico.OrdemServicoNovoDTO;
import main.dto.ordem.servico.OrdemServicoUpdateDTO;
import main.repositories.OrdemServicoRepository;
import main.security.UserSS;
import main.services.exceptions.AuthorizationException;
import main.services.exceptions.ObjectNotFoundException;
/**
*Serviços de Ordens(Pedidos)
*/
@Service
public class OrdemServicoService {
	@Autowired
	private OrdemServicoRepository repo;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EquipamentoService equipamentoService;
	@Autowired
	private MarcaService marcaService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private S3Service s3Service;
	/**
	*Retorna uma ordem pelo id
	*/
	public OrdemServico find(Integer id) {
		Optional<OrdemServico> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Ordem de serviço não encontrado! Id: " + id));
	}
	/**
	*Retorna todas as ordens
	*/
	public List <OrdemServico> findAll() {
		return repo.findAll();
	}
	/**
	*Retorna ordens baseado em um id de cliente
	*/
	public List<OrdemServico> findAllByCliente(Integer id){
		return repo.findAllByCliente_id(id);
	}
	/**
	*Insere uma nova ordem atualizando as tabelas filiais e retorna o objeto novo
	*/
	@Transactional
	public OrdemServico insert(OrdemServicoNovoDTO objDto) {
		Cliente cli = clienteService.find(objDto.getCliente());
		Marca marca= marcaService.find(objDto.getMarca());
		Equipamento equip = equipamentoService.find(objDto.getEquipamento(),marca);
		System.out.println(equip.getNome());
		OrdemServico obj  = new OrdemServico(cli, equip, new Date(System.currentTimeMillis()), objDto.getProblema());
		equip.addOrdem(obj);
		equipamentoService.insert(equip);
		obj.setId(null);
		update(obj);
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
	/**
	*Edita informações do pedido
	*/
	@Transactional
	public void updateOrdem(Integer id,OrdemServicoUpdateDTO objDto) {
		OrdemServico obj =find(id);
		obj.setProblema(objDto.getProblema());
		if(objDto.getValor()!=0) {
			obj.setValor(objDto.getValor());
		}
		Equipamento equi = equipamentoService.find(objDto.getEquipamento());
		obj.setEquipamento(equi);
		equi.addOrdem(obj);
		update(obj);
	}
	/**
	*Muda o estado do pedido para MANUTENCAO_PENDENTE
	*/
	public void confirmar(Integer id, String key) {
		OrdemServico obj = find(id);
		if(obj.getSerialKey().equals(key) && obj.getState()==EstadoOrdemServico.CONFIRMACAO_PENDENTE) {
			obj.setState(EstadoOrdemServico.MANUTENCAO_PENDENTE);
		}
		update(obj);
	}
	/**
	*Muda o estado do pedido para CANCELADO
	*/
	public void cancelar(Integer id) {
		OrdemServico obj =find(id);
		obj.setState(EstadoOrdemServico.CANCELADO);
		update(obj);
	}
	/**
	*Muda o estado do pedido para RECUSADO
	*/
	public void recusar(Integer id) {
		OrdemServico obj =find(id);
		obj.setState(EstadoOrdemServico.RECUSADO);
		update(obj);
	}
	/**
	*Muda o estado do pedido para CONCLUIDO, manda um email avisando o cliente
	*/
	public void concluir(Integer id) {
		OrdemServico obj = find(id);
		obj.setState(EstadoOrdemServico.CONCLUIDO);
		emailService.sendOrderConfirmationEmail(obj);
		update(obj);
	}
	/**
	*Muda o estado do pedido para CONFIRMACAO_PENDENTE, adiciona um valor e problemas extras para o pedido, 
	*envia um email para que o cliente possa confirmar
	*/
	public void analizar(Integer id,OrdemServicoAnalizeDTO objDto) {
		OrdemServico obj =find(id);
		obj.setProblemasExtras(objDto.getProblemasExtras());
		obj.setValor(objDto.getValor());
		obj.setState(EstadoOrdemServico.CONFIRMACAO_PENDENTE);
		emailService.sendOrderConfirmationEmail(obj);
		update(obj);
	}
	/**
	*Salva a ordem recebendo uma ordem modificada
	*/
	private void update(OrdemServico obj) {
		repo.save(obj);
	}
	/**
	*Faz o upload da imagem no s3 e adiciona o link dela a lista de fotos desse pedido
	*/
	public URI uploadProblemPicture(MultipartFile multipartFile,Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		URI uri = s3Service.uploadFile(multipartFile);
		OrdemServico ord = find(id);
		ord.addFoto(uri.toString());
		update(ord);
		return uri;
	}
}
