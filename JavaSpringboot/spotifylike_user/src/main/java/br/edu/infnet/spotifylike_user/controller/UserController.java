package br.edu.infnet.spotifylike_user.controller;

import br.edu.infnet.spotifylike_user.application.UserService;
import br.edu.infnet.spotifylike_user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    // criar conta
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        this.userService.createAccount(user.getName(), user.getSubscriptions().get(0).getPlan().getId(), user.getCards().get(0));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> get(@PathVariable("id") UUID id) {
        return this.userService.getUser(id).map(x -> {
            return new ResponseEntity<User>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // favoritar musica
    @PostMapping("{id}/favorite/{songId}")
    public ResponseEntity<User> favoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        userService.favoriteSong(id, songId);

        User user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // desfavoritar musica
    @PostMapping("/{id}/unfavorite/{songId}")
    public ResponseEntity<User> unfavoriteSong(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId) {
        userService.unfavoriteSong(id, songId);

        User user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // criar playlist
    @PostMapping("{id}/playlist/{playlistName}/{isPublic}")
    public ResponseEntity<User> createPlaylist(@PathVariable("id") UUID id, @PathVariable("playlistName") String playlistName, @PathVariable("isPublic") boolean isPublic) {
        userService.createPlaylist(id, playlistName, true);

        User user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // adicionar musica
    @PostMapping("/{id}/add/{songId}/{playlistId}")
    public ResponseEntity<User> addSongToPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        userService.addSongToPlaylist(id, songId, playlistId);

        User user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }    

    // remover musica
    @PostMapping("/{id}/remove/{songId}/{playlistId}")
    public ResponseEntity<User> removeSongFromPlaylist(@PathVariable("id") UUID id, @PathVariable("songId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        userService.removeSongFromPlaylist(id, songId, playlistId);

        User user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // escolher um plano
    @PostMapping("/{id}/subscribe/{planId}")
    public ResponseEntity<User> subscribe(@PathVariable("id") UUID id, @PathVariable("planId") UUID songId, @PathVariable("playlistId") UUID playlistId) {
        User user = userService.getUser(id).get();

        userService.removeSongFromPlaylist(id, songId, playlistId);

        user = userService.getUser(id).get();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}