package br.com.assist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class MagiaTest {

	@Test
	public void criarMagia() {
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M", "Instantânea",
				"descrição");
		
		assertEquals("Bola De Fogo", magia.getNome());
		assertEquals(3, magia.getNivel());
		assertEquals(EscolaDeMagia.EVOCACAO, magia.getEscola());
		assertFalse(magia.isRitual());
		assertEquals("1 ação", magia.getTempoDeConjuracao());
		assertEquals("45m", magia.getAlcance());
		assertEquals("V S M", magia.getComponentes());
		assertEquals("Instantânea", magia.getDuracao());
		assertEquals("descrição", magia.getDescricao());
	}

}
