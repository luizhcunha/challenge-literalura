package br.com.aluraliteraluraluiz.challenge.model;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idLivro;

    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Long numeroDownloads;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {
    }


    public Livro(DadosLivros dadosLivros) {
        this.idLivro = dadosLivros.idLivro();
        this.titulo = dadosLivros.titulo();
        if (dadosLivros.autor() != null && !dadosLivros.autor().isEmpty()) {
            Autor autor = new Autor(dadosLivros.autor().get(0));
            this.autor = autor;
        } else {
            System.out.println("Autor não encontrado");
            this.autor = null;
        }
        this.idiomas = Collections.singletonList(idiomaN(dadosLivros.idiomas()));
        this.numeroDownloads = dadosLivros.numeroDownloads();

    }

    public Livro(Livro livro) {
    }

    private String idiomaN(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "Idioma não encontrado";
        } else {
            return idiomas.get(0);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Long getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Long numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "\n****************************\n" +
                "\n Id: " + id +
                "\n Id do livro: " + idLivro+
                "\n Titulo: '" + titulo + '\'' +
                "\n Autor: " + (autor != null ? autor.getNome(): "N/A") +
                "\n Ano Nascimento do autor: " + (autor != null ? autor.getAnoNascimento(): "N/A") +
                "\n Ano Falecimento do autor: " + (autor != null ? autor.getAnoFalecimento(): "N/A") +
                "\n Disponível nos idiomas: " + idiomas +
                "\n Numero de Downloads do livro: " + numeroDownloads +
                "\n ****************************\n" ;
    }
}