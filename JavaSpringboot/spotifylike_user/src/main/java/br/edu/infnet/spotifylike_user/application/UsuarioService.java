package br.edu.infnet.spotifylike_user.application;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.infnet.spotifylike_user.domain.Card;
import br.edu.infnet.spotifylike_user.domain.Plan;
import br.edu.infnet.spotifylike_user.domain.Playlist;
import br.edu.infnet.spotifylike_user.domain.Song;
import br.edu.infnet.spotifylike_user.domain.Usuario;
import br.edu.infnet.spotifylike_user.repository.UsuarioRepository;
import br.edu.infnet.spotifylike_user.repository.BandRepository;
import br.edu.infnet.spotifylike_user.repository.PlanRepository;
import br.edu.infnet.spotifylike_user.repository.PlaylistRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public Optional<Usuario> getUser(UUID id) {
        return this.userRepository.findById(id);
    }

    public Usuario createAccount(String name) {
        Usuario user = new Usuario(name);

        this.userRepository.save(user);

        return user;
    }

    public void favoriteSong(UUID id, UUID songId) {
        Usuario user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        user.favoriteSong(song);

        this.userRepository.save(user);
    }

    public void unfavoriteSong(UUID id, UUID songId) {
        Usuario user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        user.unfavoriteSong(song);

        this.userRepository.save(user);
    }

    private Song verifySong(UUID songId) {

        Song song = new Song();
        try {
            song = this.bandRepository.getSong(songId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return song;
    }

    private Playlist verifyPlaylist(UUID playlistId) {

        Playlist playlist = this.playlistRepository.findById(playlistId).get();

        return playlist;
    }

    public void createPlaylist(UUID id, String name, boolean isPublic) {
        Usuario user = this.userRepository.findById(id).get();

        user.createPlaylist(name, isPublic);

        this.userRepository.save(user);
    }

    public void addSongToPlaylist(UUID id, UUID songId, UUID playlistId) {
        Usuario user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        user.addSongToPlaylist(song, playlist);

        this.userRepository.save(user);
    }

    public void removeSongFromPlaylist(UUID id, UUID songId, UUID playlistId) {
        Usuario user = this.userRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        user.removeSongFromPlaylist(song, playlist);

        this.userRepository.save(user);
    }

    public void subscribe(UUID id, UUID planId, Card card) {
        Usuario user = this.userRepository.findById(id).get();

        Plan plan = this.planRepository.findById(id).get();

        user.subscribe(plan, card);

        this.userRepository.save(user);
    }
}
