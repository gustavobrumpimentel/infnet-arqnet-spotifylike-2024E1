package br.edu.infnet.spotifylike.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column
    public String name;

    @Column
    public int duration;

}