package br.edu.infnet.aluno_app.application;

import br.edu.infnet.aluno_app.domain.Aluno;
import br.edu.infnet.aluno_app.domain.Turma;
import br.edu.infnet.aluno_app.repository.AlunoRepository;
import br.edu.infnet.aluno_app.repository.TurmaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    public Aluno create(String nome) {
        Aluno aluno = new Aluno(nome);

        this.alunoRepository.save(aluno);

        return aluno;
    }

    public Optional<Aluno> getAluno(UUID id) {
        return this.alunoRepository.findById(id);
    }

    public Aluno addTurma(UUID id, UUID turmaId) {
        Aluno aluno = this.alunoRepository.findById(id).get();

        Turma turma = new Turma();
        try {
            turma = turmaRepository.getTurma(turmaId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        aluno.getTurmas().add(turma);

        this.alunoRepository.save(aluno);

        return aluno;
    }
}
