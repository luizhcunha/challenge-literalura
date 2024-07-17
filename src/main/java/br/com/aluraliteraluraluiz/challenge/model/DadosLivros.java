package br.com.aluraliteraluraluiz.challenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(@JsonAlias("id") Long idLivro,
                          @JsonAlias("title") String titulo,
                          @JsonAlias("authors") List<DadosAutor> autor,
                          @JsonAlias("languages") List<String> idiomas,
                          @JsonAlias("download_count") Long numeroDownloads) {
}
