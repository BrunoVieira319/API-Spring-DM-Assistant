package br.com.assist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.assist.domain.Habilidade;
import br.com.assist.repository.HabilidadeRepository;

@Service
public class HabilidadeService {

	private HabilidadeRepository repository;
	
	@Autowired
	public HabilidadeService (HabilidadeRepository repository) {
		this.repository = repository;
	}
	
	public void salvar(Habilidade habilidade) {
		repository.saveAndFlush(habilidade);
	}
	
	public Habilidade encontrarPorNome(String nome) {
		return repository.findByNome(nome);
	}
	
	public void deletar(Habilidade habilidade) {
		repository.delete(habilidade);
	}

	public List<Habilidade> buscarTodasHabilidades () {
		return repository.findAll();
	}

	public void deletarTudo() {
		repository.deleteAll();
	} 


}
