package br.com.assist.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.domain.Personagem;
import br.com.assist.service.PersonagemService;

@RestController
@RequestMapping(value = "/personagem")
public class PersonagemController {

	@Autowired
	private PersonagemService service;

	@GetMapping(value = "/")
	public ResponseEntity<List<Personagem>> buscarTodosPersonagens() {
		return new ResponseEntity<List<Personagem>>(service.listarTodos(), HttpStatus.OK);
	}

	@PostMapping(value = "/")
	public ResponseEntity<?> salvar(@RequestBody @Valid Personagem personagem) {
		service.salvar(personagem);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> removerPersonagem(@PathVariable("id") Integer id) {
		service.deletarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
