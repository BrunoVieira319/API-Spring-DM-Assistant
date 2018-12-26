package br.com.assist.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.domain.Personagem;
import br.com.assist.service.PersonagemService;

@RestController

public class PersonagemController {

	@Autowired
	private PersonagemService service;

	@GetMapping(value = "/personagem")
	public ResponseEntity<List<Personagem>> buscarTodosPersonagens() {
		return new ResponseEntity<List<Personagem>>(service.listarTodos(), HttpStatus.OK);
	}
	
	@PostMapping(value = "/personagem")
	public ResponseEntity<?> salvar(@RequestBody @Valid Personagem personagem) {
		service.salvar(personagem);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
