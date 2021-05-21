package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.services.DBService;

@SpringBootApplication
public class SistemaDeManutencaoApplication implements CommandLineRunner {

	@Autowired
	DBService service;
	
	public SistemaDeManutencaoApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeManutencaoApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		service.instantiateTestDataBase();

	}

}