package br.com.assist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.service.PersonagemService;

@CrossOrigin
@RestController
@RequestMapping(value = "/personagem")
public class PersonagemController {

	@Autowired
	private PersonagemService service;

	@GetMapping
	public ResponseEntity<List<PersonagemHomePageDto>> buscarTodosPersonagens() {
		List<PersonagemHomePageDto> personagens = service.buscarTodosPersonagens();
		return new ResponseEntity<List<PersonagemHomePageDto>>(personagens, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/detalhes")
	public ResponseEntity<PersonagemDetalhesDto> buscarPersonagem(@PathVariable("id") Integer id) {
		PersonagemDetalhesDto personagem = service.buscarPorId(id);
		return new ResponseEntity<PersonagemDetalhesDto>(personagem, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> salvarPersonagem(@RequestBody PersonagemDto personagemDto) {
		service.salvar(personagemDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping(value = "/{id}/vida/{vidaAtual}")
	public ResponseEntity<?> atualizarVidaDoPersonagem(@PathVariable("id") Integer id,
			@PathVariable("vidaAtual") int vidaAtual) {
		service.atualizarVidaDoPersonagem(id, vidaAtual);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/dadoDeVida")
	public ResponseEntity<?> usarDadoDeVida(@PathVariable("idPersonagem") Integer id) {
		service.usarDadoDeVida(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/{id}/habilidade")
	public ResponseEntity<?> salvarHabilidade(@RequestBody HabilidadeDto habilidadeDto,
			@PathVariable("id") Integer id) {
		service.salvarHabilidadeParaPersonagem(id, habilidadeDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{id}/espacoDeMagia")
	public ResponseEntity<?> salvarEspacoDeMagia(@RequestBody EspacoDeMagiaDto espacoDto,
			@PathVariable("id") Integer id) {
		service.salvarEspacoDeMagiaParaPersonagem(id, espacoDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/{idPersonagem}/magia/{idMagia}")
	public ResponseEntity<?> salvarMagia(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("idMagia") Integer idMagia) {
		service.salvarMagiaParaPersonagem(idPersonagem, idMagia);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/habilidade/{idHabilidade}/usar")
	public ResponseEntity<?> usarHabilidade(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("idHabilidade") Integer idHabilidade) {
		service.usarHabilidade(idPersonagem, idHabilidade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/habilidade/{idHabilidade}/restaurar")
	public ResponseEntity<?> restaurarUsosHabilidade(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("idHabilidade") Integer idHabilidade) {
		service.restaurarUsosDeHabilidade(idPersonagem, idHabilidade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{idPersonagem}/habilidade/{idHabilidade}")
	public ResponseEntity<?> removerHabilidade(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("idHabilidade") Integer idHabilidade) {
		service.removerHabilidade(idPersonagem, idHabilidade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/espacoDeMagia/{nivel}/usar")
	public ResponseEntity<?> conjurarMagia(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("nivel") int nivel) {
		service.conjurarMagia(idPersonagem, nivel);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{idPersonagem}/espacoDeMagia/{nivel}/restaurar")
	public ResponseEntity<?> restaurarEspacoDeMagia(@PathVariable("idPersonagem") Integer idPersonagem,
			@PathVariable("nivel") int nivel) {
		service.restaurarEspacoDeMagia(idPersonagem, nivel);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> removerPersonagem(@PathVariable("id") Integer id) {
		service.deletarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
