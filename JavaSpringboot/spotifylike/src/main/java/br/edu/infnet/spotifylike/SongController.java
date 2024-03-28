package br.edu.infnet.spotifylike;

import br.edu.infnet.spotifylike.application.SongService;
import br.edu.infnet.spotifylike.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService service;


    @GetMapping("{id}")
    public ResponseEntity<Song> get(@PathVariable("id")UUID id) {
        Random rand = new Random();
        int n = rand.nextInt(100);
        if (n % 2 == 0) {
            return this.service.getSong(id).map(x -> {
                return new ResponseEntity<Song>(x, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
