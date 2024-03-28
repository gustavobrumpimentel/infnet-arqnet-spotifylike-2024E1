package br.edu.infnet.spotifylike_user.domain;

import java.time.LocalDateTime;
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
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Playlist> playlists;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    public Usuario(String name) {
        Plan plan = new Plan("familia", "descricao", 19.5F);
        Card card = new Card(true, 10000F, "123456");
        this.name = name;
        this.cards = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.subscriptions = new ArrayList<>();
        this.addCard(card);
        this.subscribe(plan, card);
        this.createFavoritesPlaylist();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void subscribe(Plan plan, Card card) {
        card.createTransaction(plan.getName(), plan.getValue(), plan.getDescritption());

        if (!this.subscriptions.isEmpty() && this.subscriptions.stream().anyMatch(Subscription::isActive)) {
            Subscription activeSubscription = this.subscriptions.stream().filter(subscription -> subscription.isActive()).findFirst().orElse(null);
            activeSubscription.setActive(false);
        }

        this.subscriptions.add(new Subscription(true, LocalDateTime.now(), plan));
    }

    public void createPlaylist(String name, boolean isPublic) {
        this.playlists.add(new Playlist(name, isPublic));
    }

    public void createFavoritesPlaylist() {
        this.playlists.add(new Playlist("Favoritas", false));
    }

    public void favoriteSong(Song song) {
        Playlist playlist = this.playlists.stream().filter(x -> x.getName() == "Favorites").findFirst().orElse(null);

        playlist.getSongs().add(song);
    }

    public void unfavoriteSong(Song song) {
        Playlist playlist = this.playlists.stream().filter(x -> x.getName() == "Favorites").findFirst().orElse(null);

        Song favSong = playlist.getSongs().stream().filter(x -> x.getId() == song.getId()).findFirst().orElse(null);
        
        playlist.getSongs().remove(favSong);
    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        playlist.getSongs().add(song);
    }

    public void removeSongFromPlaylist(Song song, Playlist playlist) {
        Song favSong = playlist.getSongs().stream().filter(x -> x.getId() == song.getId()).findFirst().orElse(null);
        
        playlist.getSongs().remove(favSong);
    }
}

