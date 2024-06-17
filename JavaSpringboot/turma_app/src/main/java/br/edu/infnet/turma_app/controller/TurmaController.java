package br.edu.infnet.turma_app.controller;

import br.edu.infnet.turma_app.application.TurmaService;
import br.edu.infnet.turma_app.domain.Turma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService service;

    @PostMapping("{nome}")
    public ResponseEntity<Turma> create(@PathVariable("nome") String nome) {
        Turma turma = this.service.create(nome);
        return new ResponseEntity<>(turma, HttpStatus.CREATED);
    }    

    @GetMapping("{id}")
    public ResponseEntity<Turma> get(@PathVariable("id") UUID id) {
        return this.service.getTurma(id).map(x -> {
            return new ResponseEntity<Turma>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}