package br.com.assist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.domain.Magia;
import br.com.assist.dto.MagiaDto;
import br.com.assist.dto.MagiaPesquisaDto;
import br.com.assist.service.MagiaService;

@RestController
@CrossOrigin
@RequestMapping(value = "/magia")
public class MagiaController {

	@Autowired
	private MagiaService service;

	@GetMapping
	public ResponseEntity<List<Magia>> buscarMagias() {
		List<Magia> magias = service.buscarTodasMagias();
		return new ResponseEntity<List<Magia>>(magias, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> salvarMagia(@RequestBody MagiaDto magiaDto) {
		service.salvar(magiaDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/nome={nome}")
	public ResponseEntity<List<MagiaPesquisaDto>> pesquisarMagiasPorNome(@PathVariable("nome") String nome) {
		List<MagiaPesquisaDto> magias = service.buscarPorNome(nome);
		return new ResponseEntity<List<MagiaPesquisaDto>>(magias, HttpStatus.OK);
	}
}
