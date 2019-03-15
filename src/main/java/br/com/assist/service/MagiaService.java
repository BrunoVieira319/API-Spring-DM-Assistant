package br.com.assist.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Magia;
import br.com.assist.dto.MagiaDto;
import br.com.assist.dto.MagiaPesquisaDto;
import br.com.assist.repository.MagiaRepository;

@Service
@Transactional
public class MagiaService {

	@Autowired
	private MagiaRepository repository;

	public void salvar(MagiaDto mDto) {
		Optional<Magia> magiaOpt = repository.findByNome(mDto.getNome().trim());
		if (magiaOpt.isPresent()) {
			throw new ServiceException("Magia já cadastrada");
		}

		Magia magia = new Magia(mDto.getNome().trim(), mDto.getNivel(), mDto.getEscola(), mDto.isRitual(),
				mDto.getTempoDeConjuracao(), mDto.getAlcance(), mDto.getComponentes(), mDto.getDuracao(),
				mDto.getDescricao());

		repository.saveAndFlush(magia);
	}

	public Magia buscarPorId(Integer id) {
		Optional<Magia> magia = repository.findById(id);
		if (magia.isPresent()) {
			return magia.get();
		}
		throw new ServiceException("Magia não encontrada");
	}

	public List<MagiaPesquisaDto> buscarPorNome(String nome) {
		if (nome.isEmpty()) {
			return new ArrayList<>();
		}
		List<Magia> magias = repository.findByNomeStartingWith(nome);
		List<MagiaPesquisaDto> magiasDto = new ArrayList<>();
		for (Magia magia : magias) {
			MagiaPesquisaDto magiaDto = new MagiaPesquisaDto();
			magiaDto.setId(magia.getId());
			magiaDto.setNome(magia.getNome());
			magiaDto.setNivel(magia.getNivel());
			magiaDto.setEscola(magia.getEscola().getNome());

			magiasDto.add(magiaDto);
		}
		return magiasDto;
	}

	public List<Magia> buscarTodasMagias() {
		List<Magia> magias = repository.findAll();
		Stream<Magia> sortMagias = magias.stream();
		sortMagias = sortMagias.sorted(Comparator.comparing(Magia::getNome));
		sortMagias = sortMagias.sorted(Comparator.comparing(Magia::getNivel));
		return sortMagias.collect(Collectors.toList());
	}
	
	public void deletarTudo() {
		repository.deleteAll();
	}

}
