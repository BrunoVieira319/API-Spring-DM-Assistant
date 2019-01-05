package br.com.assist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Habilidade;
import br.com.assist.domain.Magia;
import br.com.assist.domain.Personagem;
import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemEditarDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.repository.MagiaRepository;
import br.com.assist.repository.PersonagemRepository;

@Service
@Transactional
public class PersonagemService {

	@Autowired
	private PersonagemRepository personagemRepository;
	
	@Autowired
	private MagiaRepository magiaRepository;
	
	private Personagem findById(Integer id) {
		Optional<Personagem> personagem = personagemRepository.findById(id);
		if (personagem.isPresent()) {
			return personagem.get();
		}
		throw new ServiceException("Personagem não encontrado");
	}

	public void salvar(PersonagemDto pDto) {
		Personagem personagem = new Personagem(pDto.getNome(), pDto.getNivel(), pDto.getVidaMax(), pDto.getRaca(),
				pDto.getClasse(), pDto.getImg());

		personagemRepository.saveAndFlush(personagem);
	}

	public PersonagemDetalhesDto buscarPorId(Integer id) {
		Optional<Personagem> personagemOptional = personagemRepository.findByIdComHabilidades(id);

		if (personagemOptional.isPresent()) {
			Personagem personagem = personagemOptional.get();
			PersonagemDetalhesDto personagemDetalhesDto = new PersonagemDetalhesDto();

			personagemDetalhesDto.setId(personagem.getId());
			personagemDetalhesDto.setNome(personagem.getNome());
			personagemDetalhesDto.setNivel(personagem.getNivel());
			personagemDetalhesDto.setDadosDeVida(personagem.getDadosDeVida());
			personagemDetalhesDto.setImg(personagem.getImg());
			personagemDetalhesDto.setHabilidades(personagem.getHabilidades());
			personagemDetalhesDto.setEspacosDeMagia(personagem.getEspacosDeMagia());
			personagemDetalhesDto.setMagias(personagemRepository.buscaPersonagemComMagias(id).getMagias());
			return personagemDetalhesDto;
		}
		throw new ServiceException("Personagem não encontrado");
	}

	public List<PersonagemHomePageDto> buscarTodosPersonagens() {
		List<Personagem> personagens = personagemRepository.findAll();
		List<PersonagemHomePageDto> personagensHomePage = new ArrayList<>();

		for (Personagem p : personagens) {
			PersonagemHomePageDto personagemDto = new PersonagemHomePageDto();
			personagemDto.setNome(p.getNome());
			personagemDto.setId(p.getId());
			personagemDto.setNivel(p.getNivel());
			personagemDto.setClasse(p.getClasse().getNome());
			personagemDto.setRaca(p.getRaca().getNome());
			personagemDto.setVidaAtual(p.getVidaAtual());
			personagemDto.setVidaMax(p.getVidaMax());
			personagemDto.setImg(p.getImg());

			personagensHomePage.add(personagemDto);
		}
		return personagensHomePage;
	}
	
	public void editarPersonagem(PersonagemEditarDto personagemDto) {
		Personagem personagem = findById(personagemDto.getId());
		personagem.setNivel(personagemDto.getNivel());
		personagem.setVidaMax(personagemDto.getVidaMax());
		personagem.setImg(personagemDto.getImg());

	}

	public void salvarHabilidadeParaPersonagem(Integer id, HabilidadeDto habilidadeDto) {
		Personagem personagem = findById(id);
		Habilidade habilidade = new Habilidade(habilidadeDto.getNome(), habilidadeDto.getDescricao());
		personagem.adicionarHabilidade(habilidade, habilidadeDto.getQtdUsosMaximo(), habilidadeDto.getRecuperacao());
	}

	public void salvarEspacoDeMagiaParaPersonagem(Integer id, EspacoDeMagiaDto espacoDto) {
		Personagem personagem = findById(id);
		personagem.adicionarEspacoDeMagia(espacoDto.getNivel(), espacoDto.getQuantidadeMaxima());
	}
	
	public void salvarMagiaParaPersonagem(Integer idPersonagem, Integer idMagia) {
		Personagem personagem = findById(idPersonagem);
		Optional<Magia> magia = magiaRepository.findById(idMagia);
		if (magia.isPresent()) {
			personagem.adicionarMagia(magia.get());
		} else {
			throw new ServiceException("Magia não encontrada");
		}
	}

	public void usarDadoDeVida(Integer id) {
		Personagem personagem = findById(id);
		personagem.usarDadoDeVida();
	}
	
	public void descansarPersonagem(Integer id) {
		Personagem personagem = findById(id);
		personagem.descansar();
	}
	
	public void usarHabilidade(Integer idPersonagem, Integer idHabilidade) {
		Personagem personagem = findById(idPersonagem);
		personagem.usarHabilidade(idHabilidade);
	}

	public void restaurarUsosDeHabilidade(Integer idPersonagem, Integer idHabilidade) {
		Personagem personagem = findById(idPersonagem);
		personagem.restaurarUsosHabilidade(idHabilidade);
	}

	public void removerHabilidade(Integer idPersonagem, Integer idHabilidade) {
		Personagem personagem = findById(idPersonagem);
		personagem.removerHabilidade(idHabilidade);
	}

	public void conjurarMagia(Integer idPersonagem, int nivel) {
		Personagem personagem = findById(idPersonagem);
		personagem.conjurarMagia(nivel);
	}

	public void restaurarEspacoDeMagia(Integer idPersonagem, int nivel) {
		Personagem personagem = findById(idPersonagem);
		personagem.restaurarEspacoDeMagia(nivel);
	}

	public void atualizarVidaDoPersonagem(Integer id, int vidaAtual) {
		Personagem personagem = findById(id);
		personagem.setVidaAtual(vidaAtual);
	}
	
	public void prepararMagia(Integer idPersonagem, Integer idMagia) {
		Personagem personagem = findById(idPersonagem);
		personagem.prepararMagia(idMagia);
	}
	
	public void desprepararMagia(Integer idPersonagem, Integer idMagia) {
		Personagem personagem = findById(idPersonagem);
		personagem.desprepararMagia(idMagia);
	}
	
	public void removerMagiaDoPersonagem(Integer idPersonagem, Integer idMagia) {
		Personagem personagem = findById(idPersonagem);
		personagem.removerMagia(idMagia);
	}

	public void deletarPorId(int id) {
		personagemRepository.deleteById(id);
	}

	public void deletarTudo() {
		personagemRepository.deleteAll();
	}

}
