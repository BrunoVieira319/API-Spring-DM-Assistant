package br.com.assist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Habilidade;
import br.com.assist.domain.Personagem;
import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.repository.PersonagemRepository;

@Service
@Transactional
public class PersonagemService {

	private PersonagemRepository repository;

	@Autowired
	public PersonagemService(PersonagemRepository repository) {
		this.repository = repository;
	}

	public void salvar(PersonagemDto pDto) {
		Personagem personagem = new Personagem(pDto.getNome(), pDto.getNivel(), pDto.getVidaMax(), pDto.getRaca(),
				pDto.getClasse(), pDto.getImg());

		repository.saveAndFlush(personagem);
	}

	public PersonagemDetalhesDto buscarPorId(Integer id) {
		Optional<Personagem> personagemOptional = repository.findByIdComHabilidades(id);

		if (personagemOptional.isPresent()) {
			Personagem personagem = personagemOptional.get();
			PersonagemDetalhesDto personagemDetalhesDto = new PersonagemDetalhesDto();

			personagemDetalhesDto.setId(personagem.getId());
			personagemDetalhesDto.setNome(personagem.getNome());
			personagemDetalhesDto.setDadosDeVida(personagem.getDadosDeVida());
			personagemDetalhesDto.setHabilidades(personagem.getHabilidades());
			personagemDetalhesDto.setEspacosDeMagia(personagem.getEspacosDeMagia());
			return personagemDetalhesDto;
		}
		throw new ServiceException("Personagem não encontrado");
	}

	public List<PersonagemHomePageDto> buscarTodosPersonagens() {
		List<Personagem> personagens = repository.findAll();
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

	public void salvarHabilidadeParaPersonagem(Integer id, HabilidadeDto habilidadeDto) {
		Optional<Personagem> personagemOptional = repository.findById(id);

		if (personagemOptional.isPresent()) {
			Habilidade habilidade = new Habilidade(habilidadeDto.getNome(), habilidadeDto.getDescricao());

			Personagem personagem = personagemOptional.get();
			personagem.adicionarHabilidade(habilidade, habilidadeDto.getQtdUsosMaximo(),
					habilidadeDto.getRecuperacao());

			repository.saveAndFlush(personagem);
		} else {
			throw new ServiceException("Personagem não encontrado");
		}
	}

	public void salvarEspacoDeMagiaParaPersonagem(Integer id, EspacoDeMagiaDto espacoDto) {
		Optional<Personagem> personagemOptional = repository.findById(id);

		if (personagemOptional.isPresent()) {
			Personagem personagem = personagemOptional.get();

			personagem.adicionarEspacoDeMagia(espacoDto.getNivel(), espacoDto.getQuantidadeMaxima());

			repository.saveAndFlush(personagem);
		} else {
			throw new ServiceException("Personagem não encontrado");
		}
	}

	public void usarHabilidade(Integer idPersonagem, Integer idHabilidade) {
		Optional<Personagem> personagem = repository.findByIdComHabilidades(idPersonagem);
		if (personagem.isPresent()) {
			personagem.get().usarHabilidade(idHabilidade);
		}
		throw new ServiceException("Personagem não encontrado");
	}

	public void restaurarUsosDeHabilidade(Integer idPersonagem, Integer idHabilidade) {
		Optional<Personagem> personagem = repository.findById(idPersonagem);
		if (personagem.isPresent()) {
			personagem.get().restaurarUsosHabilidade(idHabilidade);
		}
		throw new ServiceException("Personagem não encontrado");
	}

	public void deletarPorId(int id) {
		repository.deleteById(id);
	}

	public void deletarTudo() {
		repository.deleteAll();
	}
	
	public Optional<Personagem> findById (Integer id) {
		return repository.findById(id);
	}

}
