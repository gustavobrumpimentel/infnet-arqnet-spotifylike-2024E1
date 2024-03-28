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
import br.edu.infnet.spotifylike_user.repository.PlanRepository;
import br.edu.infnet.spotifylike_user.repository.PlaylistRepository;
import br.edu.infnet.spotifylike_user.repository.SongRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public Optional<Usuario> getUsuario(UUID id) {
        return this.usuarioRepository.findById(id);
    }

    public Usuario createAccount(String name) {
        Usuario usuario = new Usuario(name);

        this.usuarioRepository.save(usuario);

        return usuario;
    }

    public void favoriteSong(UUID id, UUID songId) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        Song song = verifySong(songId);

        usuario.favoriteSong(song);

        this.usuarioRepository.save(usuario);
    }

    public void unfavoriteSong(UUID id, UUID songId) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        Song song = verifySong(songId);

        usuario.unfavoriteSong(song);

        this.usuarioRepository.save(usuario);
    }

    private Song verifySong(UUID songId) {

        Song song = this.songRepository.findById(songId).get();

        return song;
    }

    private Playlist verifyPlaylist(UUID playlistId) {

        Playlist playlist = this.playlistRepository.findById(playlistId).get();

        return playlist;
    }

    public void createPlaylist(UUID id, String name, boolean isPublic) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        usuario.createPlaylist(name, isPublic);
    }

    public void addSongToPlaylist(UUID id, UUID songId, UUID playlistId) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        usuario.addSongToPlaylist(song, playlist);

        this.usuarioRepository.save(usuario);
    }

    public void removeSongFromPlaylist(UUID id, UUID songId, UUID playlistId) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        Song song = verifySong(songId);

        Playlist playlist = verifyPlaylist(playlistId);

        usuario.removeSongFromPlaylist(song, playlist);

        this.usuarioRepository.save(usuario);
    }

    public void subscribe(UUID id, UUID planId, Card card) {
        Usuario usuario = this.usuarioRepository.findById(id).get();

        Plan plan = this.planRepository.findById(id).get();

        usuario.subscribe(plan, card);

        this.usuarioRepository.save(usuario);
    }
}
