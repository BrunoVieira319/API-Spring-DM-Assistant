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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.domain.Habilidade;
import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.service.PersonagemService;

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
	public ResponseEntity<?> salvar(@RequestBody @Valid PersonagemDto personagemDto) {
		service.salvar(personagemDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{id}/habilidade")
	public ResponseEntity<?> salvarHabilidade(@RequestBody HabilidadeDto habilidadeDto,
			@PathVariable("id") Integer id) {
		service.salvarHabilidadeParaPersonagem(id, habilidadeDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/{id}/espaco")
	public ResponseEntity<?> salvarEspacoDeMagia(@RequestBody EspacoDeMagiaDto espacoDto,
			@PathVariable("id") Integer id) {
		service.salvarEspacoDeMagiaParaPersonagem(id, espacoDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/habilidade/{idHabilidade}")
	public ResponseEntity<?> usarHabilidade(@PathVariable("idHabilidade") Integer idHabilidade,
			@PathVariable("idPersonagem") Integer idPersonagem) {
		service.usarHabilidade(idPersonagem, idHabilidade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping("/{idPersonagem}/restaurar-habilidade/{idHabilidade}")
	public ResponseEntity<?> restaurarUsosHabilidade(@PathVariable("idHabilidade") Integer idHabilidade,
			@PathVariable("idPersonagem") Integer idPersonagem) {
		service.restaurarUsosDeHabilidade(idPersonagem, idHabilidade);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> removerPersonagem(@PathVariable("id") Integer id) {
		service.deletarPorId(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
