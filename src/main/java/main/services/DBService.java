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
		Cliente cliente=new Cliente("paulo", "48 9 8836 9755", "cpf", "email");
		Endereco end=new Endereco(cliente,"jardim amelia","sombrio");
		cliente.setEndereco(end);
		
		
		OrdemServico ordem=new OrdemServico(cliente,equi1, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem2=new OrdemServico(cliente,equi2, sdf.parse("30/09/2017 10:32"), "problema na roda");
		OrdemServico ordem3=new OrdemServico(cliente,equi3, sdf.parse("30/09/2017 10:32"), "problema na roda");
		equi1.addOrdem(ordem);
		equi2.addOrdem(ordem2);
		equi3.addOrdem(ordem3);
		Usuario user=new Usuario("joao_Pedro",pe.encode("1234"));
		user.addPerfil(TipoUsuario.ADMIN);
		usuarioRepository.save(user);
		marcaRepository.saveAll(Arrays.asList(m1,m2));
		clienteRepository.saveAll(Arrays.asList(cliente));
		enderecoRepository.saveAll(Arrays.asList(end));
		equipamentoRepository.saveAll(Arrays.asList(equi1,equi2,equi3));
		ordemServicoRepository.saveAll(Arrays.asList(ordem,ordem2,ordem3));

	}
}
