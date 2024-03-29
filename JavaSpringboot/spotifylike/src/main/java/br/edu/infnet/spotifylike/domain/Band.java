package br.edu.infnet.spotifylike.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Album> albums;
}
