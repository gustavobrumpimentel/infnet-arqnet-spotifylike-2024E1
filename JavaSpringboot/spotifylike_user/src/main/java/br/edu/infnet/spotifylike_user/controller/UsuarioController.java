package br.edu.infnet.spotifylike_user.controller;

import br.edu.infnet.spotifylike_user.application.UsuarioService;
import br.edu.infnet.spotifylike_user.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // criar conta
    @PostMapping("{name}")
    public ResponseEntity<Usuario> create(@PathVariable("name") String name) {

        Usuario usuario = this.usuarioService.createAccount(name);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Usuario> get(@PathVariable("id") UUID id) {
        return this.usuarioService.getUsuario(id).map(x -> {
            return new ResponseEntity<Usuario>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // favoritar musica
    @PostMapping("{id}/favorite/{songId}")
    public ResponseEntity<Usuario> favoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        usuarioService.favoriteSong(id, songId);

        Usuario usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // desfavoritar musica
    @PostMapping("/{id}/unfavorite/{songId}")
    public ResponseEntity<Usuario> unfavoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        usuarioService.unfavoriteSong(id, songId);

        Usuario usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // criar playlist
    @PostMapping("{id}/playlist/{playlistName}/{isPublic}")
    public ResponseEntity<Usuario> createPlaylist(@PathVariable("id") UUID id, @PathVariable("playlistName") String playlistName, @PathVariable("isPublic") boolean isPublic) {
        usuarioService.createPlaylist(id, playlistName, true);

        Usuario usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // adicionar musica
    @PostMapping("/{id}/add/{songId}/{playlistId}")
    public ResponseEntity<Usuario> addSongToPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        usuarioService.addSongToPlaylist(id, songId, playlistId);

        Usuario usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }    

    // remover musica
    @PostMapping("/{id}/remove/{songId}/{playlistId}")
    public ResponseEntity<Usuario> removeSongFromPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        usuarioService.removeSongFromPlaylist(id, songId, playlistId);

        Usuario usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // escolher um plano
    @PostMapping("/{id}/subscribe/{planId}")
    public ResponseEntity<Usuario> subscribe(@PathVariable("id") UUID id, @PathVariable("planId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        Usuario usuario = usuarioService.getUsuario(id).get();

        usuarioService.removeSongFromPlaylist(id, songId, playlistId);

        usuario = usuarioService.getUsuario(id).get();

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

}