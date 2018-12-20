package br.com.assist.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.assist.domain.Descanso;
import br.com.assist.domain.Habilidade;
import br.com.assist.domain.HabilidadePersonagem;

@RunWith(value = SpringRunner.class)
@SpringBootTest
public class HabilidadeServiceTest {

	@Autowired
	private HabilidadeService habilidadeService;

	@After
	public void deletaBase() {
		habilidadeService.deletarTudo();
	}
	
	@Test
	public void salvaHabilidade() {
		HabilidadePersonagem hp = new HabilidadePersonagem(2, Descanso.CURTO);
		
		Habilidade habilidade = new Habilidade("Canalizar Divindade", "");
		habilidade.adicionarUsos(hp);
		habilidadeService.salvar(habilidade);
		
		Habilidade habilidadeEncontrada = habilidadeService.encontrarPorNome(habilidade.getNome());

		assertEquals(habilidade, habilidadeEncontrada);
	}

	@Test
	public void deletaHabilidade() {
		HabilidadePersonagem hp = new HabilidadePersonagem(2, Descanso.CURTO);
		
		Habilidade habilidade = new Habilidade("Canalizar Divindade", "");
		habilidade.adicionarUsos(hp);
		habilidadeService.salvar(habilidade);
		
		habilidadeService.deletar(habilidade);
		
		assertEquals(0, habilidadeService.buscarTodasHabilidades().size());
	}
}
