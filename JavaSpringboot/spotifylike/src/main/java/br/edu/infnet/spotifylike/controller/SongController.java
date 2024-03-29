package br.edu.infnet.spotifylike.controller;

import br.edu.infnet.spotifylike.application.SongService;
import br.edu.infnet.spotifylike.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService service;

    @GetMapping("{id}")
    public ResponseEntity<Song> get(@PathVariable("id")UUID id) {
        return this.service.getSong(id).map(x -> {
            return new ResponseEntity<Song>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
