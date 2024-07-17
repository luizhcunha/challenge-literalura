package br.com.aluraliteraluraluiz.challenge.service;

import br.com.aluraliteraluraluiz.challenge.model.Autor;
import br.com.aluraliteraluraluiz.challenge.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> getAutoresVivosNoAno(Integer ano) {
        return autorRepository.findAutoresVivos(ano);
    }
}