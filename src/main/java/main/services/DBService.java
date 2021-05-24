package main.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Cliente;
import main.domain.Endereco;
import main.domain.Equipamento;
import main.domain.Marca;
import main.domain.OrdemServico;
import main.repositories.ClienteRepository;
import main.repositories.EnderecoRepository;
import main.repositories.EquipamentoRepository;
import main.repositories.MarcaRepository;
import main.repositories.OrdemServicoRepository;

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
		equi1.setOrdemServico(ordem);

		marcaRepository.saveAll(Arrays.asList(m1,m2));
		clienteRepository.saveAll(Arrays.asList(cliente));
		enderecoRepository.saveAll(Arrays.asList(end));
		ordemServicoRepository.saveAll(Arrays.asList(ordem));
		equipamentoRepository.saveAll(Arrays.asList(equi1,equi2,equi3));

	}
}
