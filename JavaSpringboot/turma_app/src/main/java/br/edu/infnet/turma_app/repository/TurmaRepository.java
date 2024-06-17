package br.edu.infnet.turma_app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.turma_app.domain.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, UUID> {
}
