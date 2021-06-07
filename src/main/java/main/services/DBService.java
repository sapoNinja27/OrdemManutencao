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
		
		Marca m1=new Marca("Rusquarna");
		Marca m2=new Marca("bosh");
		
		Equipamento equi1=new Equipamento("Aparador de grama", m1);
		Equipamento equi2=new Equipamento("furadeira", m1);
		Equipamento equi3=new Equipamento("furadeira", m2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Cliente cliente=new Cliente("paulo", "48 9 8836 9755", "email", "cpf");
		Cliente cliente2=new Cliente("clenio", "48 9 8836 9755", "email", "cpf");
		Cliente cliente3=new Cliente("nao tenho criatividade pra nome", "48 9 8836 9755", "email", "cpf");
		Cliente cliente4=new Cliente("teste", "48 9 8836 9755", "email", "cpf");
		Cliente cliente5=new Cliente("zulmito", "48 9 8836 9755", "email", "cpf");
		Cliente cliente6=new Cliente("joedio", "48 9 8836 9755", "email", "cpf");
		Endereco end=new Endereco(cliente,"jardim amelia","sombrio");
		cliente.setEndereco(end);
		
		
		OrdemServico ordem=new OrdemServico(cliente,equi1, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem2=new OrdemServico(cliente,equi2, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem3=new OrdemServico(cliente,equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem4=new OrdemServico(cliente,equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem5=new OrdemServico(cliente,equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem6=new OrdemServico(cliente,equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		ordem.setState(EstadoOrdemServico.ANALIZE_PENDENTE);
		ordem2.setState(EstadoOrdemServico.RECUSADO);
		ordem3.setState(EstadoOrdemServico.CONFIRMACAO_PENDENTE);
		ordem4.setState(EstadoOrdemServico.CANCELADO);
		ordem5.setState(EstadoOrdemServico.MANUTENCAO_PENDENTE);
		ordem6.setState(EstadoOrdemServico.CONCLUIDO);
		equi1.addOrdem(ordem);
		equi2.addOrdem(ordem2);
		equi3.addOrdem(ordem3);
		Usuario user=new Usuario("adm",pe.encode("1"));
		user.addPerfil(TipoUsuario.ADMIN);
		user.addPerfil(TipoUsuario.ANALISTA);
		user.addPerfil(TipoUsuario.RECEPCIONISTA);
		user.addPerfil(TipoUsuario.TECNICO);
		
		
		Usuario user2=new Usuario("tec",pe.encode("1"));
		user2.addPerfil(TipoUsuario.TECNICO);
		
		
		Usuario user3=new Usuario("rec",pe.encode("1"));
		user3.addPerfil(TipoUsuario.RECEPCIONISTA);
		
		Usuario user4=new Usuario("an",pe.encode("1"));
		user4.addPerfil(TipoUsuario.ANALISTA);
		
		Usuario user5=new Usuario("clenio",pe.encode("1"));
		user5.addPerfil(TipoUsuario.ANALISTA);
		user5.addPerfil(TipoUsuario.RECEPCIONISTA);
		
		usuarioRepository.saveAll(Arrays.asList(user,user2,user3,user4,user5));
		marcaRepository.saveAll(Arrays.asList(m1,m2));
		clienteRepository.saveAll(Arrays.asList(cliente,cliente2,cliente3,cliente4,cliente5, cliente6));
		enderecoRepository.saveAll(Arrays.asList(end));
		equipamentoRepository.saveAll(Arrays.asList(equi1,equi2,equi3));
		ordemServicoRepository.saveAll(Arrays.asList(ordem,ordem2,ordem3,ordem4,ordem5,ordem6));

	}
}
