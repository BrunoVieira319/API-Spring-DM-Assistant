package br.com.assist.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HabilidadeTest {

	@Test
	public void criaHabilidade() {
		Habilidade habilidade = new Habilidade("Canalizar Divindade", "Descrição");

		assertEquals(habilidade.getNome(), "Canalizar Divindade");
		assertEquals(habilidade.getDescricao(), "Descrição");
	}

	@Test(expected = DominioInvalidoException.class)
	public void criaHabilidadeComNomeVazio() {
		new Habilidade("", "Descrição");
	}
	
	@Test(expected = DominioInvalidoException.class)
	public void criaHabilidadeComNomeNull() {
		new Habilidade(null, "Descrição");
	}
	
	@Test(expected = DominioInvalidoException.class)
	public void criaHabilidadeComDescricaoNull() {
		new Habilidade("Nome", null);
	}
}
