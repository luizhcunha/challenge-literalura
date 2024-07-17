package br.com.aluraliteraluraluiz.challenge;

import br.com.aluraliteraluraluiz.challenge.principal.Principal;
import br.com.aluraliteraluraluiz.challenge.repository.AutorRepository;
import br.com.aluraliteraluraluiz.challenge.repository.LivroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {
	@Autowired
	private LivroRepository lRepositorio;
	@Autowired
	private AutorRepository aRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner leitura = new Scanner(System.in);
		Principal principal = new Principal(lRepositorio, aRepositorio);
		principal.exibeMenu();

	}
}
