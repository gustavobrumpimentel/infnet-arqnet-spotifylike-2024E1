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
    private UsuarioService userService;

    // criar conta
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario user) {
        this.userService.createAccount(user.getName(), user.getSubscriptions().get(0).getPlan().getId(), user.getCards().get(0));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // criar conta
    @PostMapping("{name}")
    public ResponseEntity<Usuario> create(@PathVariable("name") String name) {
        Usuario user = this.userService.createAccount(name);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }    

    @GetMapping("{id}")
    public ResponseEntity<Usuario> get(@PathVariable("id") UUID id) {
        return this.userService.getUser(id).map(x -> {
            return new ResponseEntity<Usuario>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // favoritar musica
    @PostMapping("{id}/favorite/{songId}")
    public ResponseEntity<Usuario> favoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        userService.favoriteSong(id, songId);

        Usuario user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // desfavoritar musica
    @PostMapping("/{id}/unfavorite/{songId}")
    public ResponseEntity<Usuario> unfavoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        userService.unfavoriteSong(id, songId);

        Usuario user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // criar playlist
    @PostMapping("{id}/playlist/{playlistName}/{isPublic}")
    public ResponseEntity<Usuario> createPlaylist(@PathVariable("id") UUID id, @PathVariable("playlistName") String playlistName, @PathVariable("isPublic") boolean isPublic) {
        userService.createPlaylist(id, playlistName, isPublic);

        Usuario user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // adicionar musica
    @PostMapping("/{id}/add/{songId}/{playlistId}")
    public ResponseEntity<Usuario> addSongToPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        userService.addSongToPlaylist(id, songId, playlistId);

        Usuario user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }    

    // remover musica
    @PostMapping("/{id}/remove/{songId}/{playlistId}")
    public ResponseEntity<Usuario> removeSongFromPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        userService.removeSongFromPlaylist(id, songId, playlistId);

        Usuario user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // escolher um plano
    @PostMapping("/{id}/subscribe/{planId}")
    public ResponseEntity<Usuario> subscribe(@PathVariable("id") UUID id, @PathVariable("planId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        Usuario user = userService.getUser(id).get();

        userService.removeSongFromPlaylist(id, songId, playlistId);

        user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}