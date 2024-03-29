package br.edu.infnet.spotifylike_user.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID realId;

    @Column
    public UUID id;

    @Column
    public String name;

    @Column
    public int duration;

}