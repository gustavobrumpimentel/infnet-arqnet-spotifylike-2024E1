package br.edu.infnet.turma_app.application;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.turma_app.domain.Turma;
import br.edu.infnet.turma_app.repository.TurmaRepository;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    public Optional<Turma> getTurma(UUID id) {
        return this.repository.findById(id);
    }

    public Turma create(String nome) {
        Turma turma = new Turma(nome);

        this.repository.save(turma);

        return turma;
    }
}
