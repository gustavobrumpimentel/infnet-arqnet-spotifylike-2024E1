package br.edu.infnet.aluno_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID realId;

    @Column
    public UUID id;

    @Column
    public String nome;
}
