package br.com.assist.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.assist.domain.EscolaDeMagia;
import br.com.assist.domain.Magia;
import br.com.assist.dto.MagiaDto;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MagiaServiceTest {

	@Autowired
	private MagiaService service;
	
	private MagiaDto magiaDto;
	
	@Before
	public void criarMagia() {
		magiaDto = new MagiaDto();
		magiaDto.setNome("Bola de Fogo");
		magiaDto.setNivel(3);
		magiaDto.setEscola(EscolaDeMagia.EVOCACAO);
		magiaDto.setRitual(false);
		magiaDto.setTempoDeConjuracao("1 ação");
		magiaDto.setAlcance("45m");
		magiaDto.setComponentes("V S M");
		magiaDto.setDuracao("Instantânea");
		magiaDto.setDescricao("descricão");
	}
	
	@Test
	public void salvarMagia() {
		service.salvarMagia(magiaDto);
		
		Magia magia = service.buscarTodasMagias().get(0);
		
		assertEquals(magia.getNome(), magiaDto.getNome());
		assertEquals(magia.getNivel(), magiaDto.getNivel());
		assertEquals(magia.getEscola(), magiaDto.getEscola());
		assertEquals(magia.isRitual(), magiaDto.isRitual());
		assertEquals(magia.getTempoDeConjuracao(), magiaDto.getTempoDeConjuracao());
		assertEquals(magia.getAlcance(), magiaDto.getAlcance());
		assertEquals(magia.getComponentes(), magiaDto.getComponentes());
		assertEquals(magia.getDuracao(), magiaDto.getDuracao());
		assertEquals(magia.getDescricao(), magiaDto.getDescricao());
	}
}
