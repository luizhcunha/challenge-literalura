package br.com.aluraliteraluraluiz.challenge.repository;

import br.com.aluraliteraluraluiz.challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAll();

    Optional<Autor> findById(Long aLong);

    Optional<Autor> findByNome(String nome);

    @Query("SELECT a FROM Autor a WHERE a.anoNascimento <= :ano AND (a.anoFalecimento IS NULL OR a.anoFalecimento >= :ano)")
    List<Autor> findAutoresVivos(@Param("ano") Integer ano);
}
