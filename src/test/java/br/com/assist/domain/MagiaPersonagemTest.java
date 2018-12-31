package br.com.assist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class MagiaPersonagemTest {

	private Magia magia;

	@Before
	public void instanciarMagia() {
		magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M", "Instantânea",
				"descrição");
	}

	@Test
	public void criarMagiaPersonagem() {
		MagiaPersonagem magiaPersonagem = new MagiaPersonagem(magia);
		
		assertEquals(magia, magiaPersonagem.getMagia());
		assertFalse(magiaPersonagem.isPreparada());
	}

}
