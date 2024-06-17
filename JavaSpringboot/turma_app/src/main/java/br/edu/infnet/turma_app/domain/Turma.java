package br.edu.infnet.turma_app.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Turma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    public Turma(String nome) {
        this.nome = nome;
    }

    public Turma() {
        this.nome = "";
    }
}

