package main.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.domain.Equipamento;
import main.domain.OrdemServico;
import main.domain.Problema;
import main.repositories.EquipamentoRepository;
import main.repositories.OrdemServicoRepository;
import main.repositories.ProblemaRepository;

@Service
public class DBService {
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	@Autowired
	private ProblemaRepository problemaRepository;

	public void instantiateTestDataBase() throws Exception {
		
		Equipamento equi1=new Equipamento("Aparador de grama", "Excel");
		
		Problema prob1=new Problema("Falha na roda", "Roda pode travar as vezes");
		Problema prob2=new Problema("Falha no motor", "Consome muita gasolina");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		OrdemServico ordem=new OrdemServico(equi1, sdf.parse("30/09/2017 10:32"));
		ordem.setProblemas(prob1);
		ordem.setProblemas(prob2);
		equi1.setOrdemServico(ordem);
		prob1.setOrdemServico(ordem);
		prob2.setOrdemServico(ordem);

		ordemServicoRepository.saveAll(Arrays.asList(ordem));
		equipamentoRepository.saveAll(Arrays.asList(equi1));
		problemaRepository.saveAll(Arrays.asList(prob1,prob2));

	}
}
