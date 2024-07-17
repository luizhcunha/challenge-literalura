package br.com.aluraliteraluraluiz.challenge.principal;

import br.com.aluraliteraluraluiz.challenge.model.DadosLivros;
import br.com.aluraliteraluraluiz.challenge.model.Autor;
import br.com.aluraliteraluraluiz.challenge.model.Livro;
import br.com.aluraliteraluraluiz.challenge.model.LivroResultadosApi;
import br.com.aluraliteraluraluiz.challenge.repository.AutorRepository;
import br.com.aluraliteraluraluiz.challenge.repository.LivroRepository;
import br.com.aluraliteraluraluiz.challenge.service.ConsumoApi;
import br.com.aluraliteraluraluiz.challenge.service.ConverteDados;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class Principal {


    private Scanner leitura = new Scanner(System.in);

    private ConsumoApi consumo = new ConsumoApi();

    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books/?search=";

    private List<Livro> dadosLivros = new ArrayList<>();

    private LivroRepository lRepositorio;
    private AutorRepository aRepositorio;

    public Principal(LivroRepository lRepositorio, AutorRepository aRepositorio) {
        this.lRepositorio = lRepositorio;
        this.aRepositorio = aRepositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ***Opções para escolha***
                    1 - Buscar livros pelo título.
                    2 - Lista de Livros registrados.
                    3 - lista de autores registrados.
                    4 - Lista de autores registrados vivos em determinado ano.
                    5 - Lista de livros por idioma.

                    0 - Sair.
                    """;

            try{
                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();


                switch (opcao) {
                    case 1:
                        buscarLivrosPorTitulo();
                        break;
                    case 2:
                        listaDeLivrosRegistrados();
                        break;
                    case 3:
                        listaDeAutoresRegistrados();
                        break;
                    case 4:
                        listaDeAutoresVivosEmDeterminadoAno();
                        break;
                    case 5:
                        listaLivrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Dado invalido tente novamente");
                leitura.next();
            }
        }
    }

    private Livro getDadosLivros() {
        System.out.println("Digite o nome do livro que deseja buscar: ");
        var nomeLivro = leitura.nextLine().toLowerCase();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        LivroResultadosApi dados = conversor.obterDados(json, LivroResultadosApi.class);
        System.out.println(dados);

        if (dados != null && dados.getResultadoLivro() != null && !dados.getResultadoLivro().isEmpty()) {
            DadosLivros livroUm = dados.getResultadoLivro().get(0);
            return new Livro(livroUm);
        } else {
            System.out.println("Nenhum resultado encontrado");
            return null;
        }
    }

    @Transactional
    protected void buscarLivrosPorTitulo(){
        Livro livro = getDadosLivros();
        if (livro == null) {
            System.out.println("Não foi possível encontrar o livro");
            return;
        }

        try{ boolean livroExists = Boolean.parseBoolean(lRepositorio.existsByTitulo(livro.getTitulo()));
            if (livroExists){
                System.out.println(livro);
            } else{
                lRepositorio.save(livro);
                System.out.println(livro);
            }
        }catch (InvalidDataAccessApiUsageException | DataIntegrityViolationException e) {
            System.out.println("Livro invalido");
        }


    }

    @Transactional(readOnly = true)
    protected void listaDeLivrosRegistrados() {
        dadosLivros = lRepositorio.findAll();
        dadosLivros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }


    private void listaDeAutoresRegistrados() {
        List<Autor> autors = aRepositorio.findAll();
        autors.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void listaDeAutoresVivosEmDeterminadoAno() {
        System.out.println("Escolha o ano para ver a lista de autores vivos nesse ano: ");
        try{ var anoVivo = leitura.nextInt();
            leitura.nextLine();
            List<Autor> autoresVivos = aRepositorio.findAutoresVivos(anoVivo);
            autoresVivos.forEach(System.out::println);
        }catch (InputMismatchException e) {
            System.out.println("Dado invalido tente novamente");
            leitura.next();}
    }

    private void listaLivrosPorIdioma() {
        var opcaoIdioma = -1;
        while (opcaoIdioma != 0) {
            System.out.println("***Opções de idiomas para escolha***");
            System.out.println("1 - Espanhol (es).");
            System.out.println("2 - Português (pt).");
            System.out.println("3 - Inglês (en).");
            System.out.println("4 - Francês (fr).");
            System.out.println("0 - sair");
            System.out.println("escolha uma opção");
            opcaoIdioma = leitura.nextInt();
            leitura.nextLine();
            if (opcaoIdioma == 1) {
                listarLivrosPorIdioma("es");
            } else if (opcaoIdioma == 2) {
                listarLivrosPorIdioma("pt");
            } else if (opcaoIdioma == 3) {
                listarLivrosPorIdioma("en");
            } else if (opcaoIdioma == 4) {
                listarLivrosPorIdioma("fr");
            } else if (opcaoIdioma == 0) {
                System.out.println("Você saiu do Menu, programa encerrado");
            } else {
                System.out.println("opção invalida tente novamente");
            }


        }

    }
    private void listarLivrosPorIdioma(String idioma){


        List<Livro> livrosPorIdioma = lRepositorio.livrosPorIdioma(idioma);
        System.out.println("*** Livros em " + idioma + " ***");
        livrosPorIdioma.forEach(l -> System.out.println(l.getTitulo()));


    }
}
