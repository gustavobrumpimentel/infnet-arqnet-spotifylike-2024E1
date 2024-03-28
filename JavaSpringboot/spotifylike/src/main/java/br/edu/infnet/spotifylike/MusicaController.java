package br.edu.infnet.spotifylike;

import br.edu.infnet.spotifylike.application.MusicaService;
import br.edu.infnet.spotifylike.domain.Musica;
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
@RequestMapping("/musica")
public class MusicaController {

    @Autowired
    private MusicaService service;


    @GetMapping("{id}")
    public ResponseEntity<Musica> get(@PathVariable("id")UUID id) {
        Random rand = new Random();
        int n = rand.nextInt(100);
        if (n % 2 == 0) {
            return this.service.getMusica(id).map(x -> {
                return new ResponseEntity<Musica>(x, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
