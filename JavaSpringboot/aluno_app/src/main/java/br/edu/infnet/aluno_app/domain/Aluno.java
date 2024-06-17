package br.edu.infnet.aluno_app.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Aluno {

    public Aluno(String nome) {
        this.nome = nome;
        this.turmas = new ArrayList<Turma>();
    }

    public Aluno() {
        this.nome = "";
        this.turmas = new ArrayList<Turma>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column
    public String nome;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Turma> turmas;

}