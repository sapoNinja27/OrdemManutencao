package main.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import main.domain.Cliente;
import main.domain.Endereco;
import main.domain.Equipamento;
import main.domain.Marca;
import main.domain.OrdemServico;
import main.domain.Usuario;
import main.domain.enums.EstadoOrdemServico;
import main.domain.enums.TipoUsuario;
import main.repositories.ClienteRepository;
import main.repositories.EnderecoRepository;
import main.repositories.EquipamentoRepository;
import main.repositories.MarcaRepository;
import main.repositories.OrdemServicoRepository;
import main.repositories.UsuarioRepository;

@Service
public class DBService {
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private MarcaRepository marcaRepository;
	@Autowired
	private BCryptPasswordEncoder pe;
	@Autowired
	private UsuarioRepository usuarioRepository;

	public void instantiateTestDataBase() throws Exception {

		Marca m1 = new Marca("Rusquarna");
		Marca m2 = new Marca("Bosh");

		Equipamento equi1 = new Equipamento("Aparador de grama", m1);
		Equipamento equi2 = new Equipamento("Furadeira", m1);
		Equipamento equi3 = new Equipamento("Furadeira", m2);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Cliente cliente = new Cliente("Paulo", "48 9 8836 9755", "jp_ronzanisantos@hotmail.com", "12534314311",
				"34123466743");
		Cliente cliente2 = new Cliente("Clenio", "48 9 8836 9755", "jp_ronzanisantos@hotmail.com", "12534314311",
				"34123557443");
		Cliente cliente3 = new Cliente("Não Tenho Criatividade Pra Nome", "48 9 8836 9755",
				"jp_ronzanisantos@hotmail.com", "12534314311", "34331723443");
		Cliente cliente4 = new Cliente("Teste", "48 9 8836 9755", "jp_ronzanisantos@hotmail.com", "12534314131",
				"34123334743");
		Cliente cliente5 = new Cliente("Zulmito", "48 9 8836 9755", "jp_ronzanisantos@hotmail.com", "12534131431",
				"34122273443");
		Cliente cliente6 = new Cliente("Joedio", "48 9 8836 9755", "jp_ronzanisantos@hotmail.com", "12534311431",
				"34111273443");
		Endereco end = new Endereco(cliente, "jardim amelia", "sombrio");
		cliente.setEndereco(end);

		OrdemServico ordem = new OrdemServico(cliente, equi1, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem2 = new OrdemServico(cliente2, equi2, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem3 = new OrdemServico(cliente3, equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem4 = new OrdemServico(cliente4, equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem5 = new OrdemServico(cliente5, equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem6 = new OrdemServico(cliente6, equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		ordem2.setState(EstadoOrdemServico.CANCELADO);
		ordem3.setState(EstadoOrdemServico.RECUSADO);
		ordem4.setState(EstadoOrdemServico.CONFIRMACAO_PENDENTE);
		ordem5.setState(EstadoOrdemServico.MANUTENCAO_PENDENTE);
		ordem6.setState(EstadoOrdemServico.CONCLUIDO);
		equi1.addOrdem(ordem);
		equi2.addOrdem(ordem2);
		equi3.addOrdem(ordem3);
		Usuario user = new Usuario("adm", pe.encode("1"));
		user.addPerfil(TipoUsuario.ADMIN);
		Usuario user2 = new Usuario("tec", pe.encode("1"));
		user2.addPerfil(TipoUsuario.TECNICO);
		Usuario user3 = new Usuario("rec", pe.encode("1"));
		user3.addPerfil(TipoUsuario.RECEPCIONISTA);
		Usuario user4 = new Usuario("an", pe.encode("1"));
		user4.addPerfil(TipoUsuario.ANALISTA);
		Usuario user5 = new Usuario("clenio", pe.encode("1"));
		user5.addPerfil(TipoUsuario.ANALISTA);
		user5.addPerfil(TipoUsuario.RECEPCIONISTA);

		usuarioRepository.saveAll(Arrays.asList(user, user2, user3, user4, user5));
		marcaRepository.saveAll(Arrays.asList(m1, m2));
		clienteRepository.saveAll(Arrays.asList(cliente, cliente2, cliente3, cliente4, cliente5, cliente6));
		enderecoRepository.saveAll(Arrays.asList(end));
		equipamentoRepository.saveAll(Arrays.asList(equi1, equi2, equi3));
		ordemServicoRepository.saveAll(Arrays.asList(ordem, ordem2, ordem3, ordem4, ordem5, ordem6));

	}
}
