package br.edu.infnet.aluno_app.controller;

import br.edu.infnet.aluno_app.application.AlunoService;
import br.edu.infnet.aluno_app.domain.Aluno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService service;

    @PostMapping("{nome}")
    public ResponseEntity<Aluno> create(@PathVariable("nome") String nome) {
        Aluno aluno = this.service.create(nome);
        return new ResponseEntity<>(aluno, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable("id") UUID id) {
        return this.service.getAluno(id).map(x -> {
            return new ResponseEntity<Aluno>(x, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/add/{turmaId}")
    public ResponseEntity<Aluno> addTurma(@PathVariable("id") UUID id, @PathVariable("turmaId") UUID turmaId) {
        Aluno aluno = this.service.addTurma(id, turmaId);
        return new ResponseEntity<>(aluno, HttpStatus.CREATED);
    }
    
}
