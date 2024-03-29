package br.edu.infnet.spotifylike.controller;

import br.edu.infnet.spotifylike.application.BandService;
import br.edu.infnet.spotifylike.domain.Band;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/band")
public class BandController {

    @Autowired
    private BandService bandService;

    @GetMapping
    public ResponseEntity<List<Band>> getAll() {
        List<Band> band = this.bandService.getAll();
        return new ResponseEntity<>(band, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Band> get(@PathVariable("id") UUID id) {
        return this.bandService.getBand(id).map(x -> {
            return new ResponseEntity<Band>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Band> create(@RequestBody Band band) {
        this.bandService.create(band);
        return new ResponseEntity<>(band, HttpStatus.CREATED);
    }

}
