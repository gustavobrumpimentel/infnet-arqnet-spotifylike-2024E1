package br.edu.infnet.spotifylike.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column
    public String nome;

    //Todo: CORRIGIR!!!
    @Column
    public int duracao;

}