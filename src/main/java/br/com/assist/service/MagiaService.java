package br.com.assist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Magia;
import br.com.assist.dto.MagiaDto;
import br.com.assist.repository.MagiaRepository;

@Service
@Transactional
public class MagiaService {

	@Autowired
	private MagiaRepository repository;

	public void salvarMagia(MagiaDto mDto) {
		Optional<Magia> magiaOpt = repository.findByNome(mDto.getNome().trim());
		if (magiaOpt.isPresent()) {
			throw new ServiceException("Magia já cadastrada");
		}

		Magia magia = new Magia(mDto.getNome().trim(), mDto.getNivel(), mDto.getEscola(), mDto.isRitual(),
				mDto.getTempoDeConjuracao(), mDto.getAlcance(), mDto.getComponentes(), mDto.getDuracao(),
				mDto.getDescricao());

		repository.saveAndFlush(magia);
	}
	
	public List<Magia> buscarTodasMagias() {
		return repository.findAll();
	}

}
