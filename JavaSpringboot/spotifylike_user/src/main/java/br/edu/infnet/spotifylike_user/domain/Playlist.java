package br.edu.infnet.spotifylike_user.domain;

import java.util.ArrayList;
import java.util.List;
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
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private boolean isPublic;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs;

    public Playlist(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
        this.songs = new ArrayList<Song>();
    }
    
    public Playlist() {
        this.name = "";
        this.isPublic = false;
        this.songs = new ArrayList<Song>();
    }
}
