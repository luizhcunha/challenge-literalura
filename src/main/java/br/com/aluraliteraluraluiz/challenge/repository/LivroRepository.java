package br.com.aluraliteraluraluiz.challenge.repository;

import br.com.aluraliteraluraluiz.challenge.model.Autor;
import br.com.aluraliteraluraluiz.challenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    String existsByTitulo(String titulo);
    Livro findByTituloContainsIgnoreCase(String titulo);
    String existsByAutor(Autor nomeauto);

    @Query("SELECT l FROM Livro l WHERE :idioma MEMBER OF l.idiomas")
    List<Livro> livrosPorIdioma(String idioma);


}
