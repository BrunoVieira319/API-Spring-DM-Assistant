package br.com.assist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.assist.domain.Personagem;
import br.com.assist.repository.PersonagemRepository;

@Service
public class PersonagemService {

	private PersonagemRepository repository;

	@Autowired
	public PersonagemService(PersonagemRepository repository) {
		this.repository = repository;
	}

	public void salvar(Personagem personagem) {
		repository.saveAndFlush(personagem);
	}

	public Personagem buscarPorNome(String nome) {
		Personagem personagem = repository.findByNome(nome);
		if (personagem == null) {
			throw new ServiceException("Personagem não encontrado");
		}
		return personagem;
	}

	public Personagem buscarPorId(Integer id) {
		Optional<Personagem> personagem = repository.findById(id);
		if (personagem.isPresent()) {
			return personagem.get();
		}
		throw new ServiceException("Personagem não encontrado");
	}
	
	public List<Personagem> listarTodos() {
		return repository.findAll();
	}
	
	public void deletarPorId(int id) {
		repository.deleteById(id);
	}

	public void deletarTudo() {
		repository.deleteAll();
	}
}
