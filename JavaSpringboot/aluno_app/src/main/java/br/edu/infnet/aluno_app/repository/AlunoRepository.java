package br.edu.infnet.aluno_app.repository;

import br.edu.infnet.aluno_app.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, UUID> {

}
