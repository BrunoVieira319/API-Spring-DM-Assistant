package br.com.assist.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EspacosDeMagiaTest {

	EspacoDeMagia espacoDeMagia;

	@Before
	public void instanciarEspacoDeMagia() {
		espacoDeMagia = new EspacoDeMagia(1, 2);
	}

	@Test
	public void criarEspacoDeMagia() {
		EspacoDeMagia espacoDeMagia = new EspacoDeMagia(1, 3);

		assertEquals(1, espacoDeMagia.getNivel());
		assertEquals(3, espacoDeMagia.getQuantidadeMaxima());
		assertEquals(3, espacoDeMagia.getQuantidade());
	}

	@Test
	public void gastarEspacoDeMagia() {
		espacoDeMagia.usarEspaco();

		assertEquals(1, espacoDeMagia.getQuantidade());
	}

	@Test
	public void restaurarEspacoDeMagia() {
		espacoDeMagia.usarEspaco();

		espacoDeMagia.restauraUmEspaco();

		assertEquals(2, espacoDeMagia.getQuantidade());
	}

	@Test
	public void restaurarTodosEspacosDeMagia() {
		espacoDeMagia.usarEspaco();
		espacoDeMagia.usarEspaco();

		espacoDeMagia.restaurarTodosEspacos();

		assertEquals(2, espacoDeMagia.getQuantidade());
	}

	@Test
	public void garantirQueQuantidadeNaoSejaNegativa() {
		espacoDeMagia.usarEspaco();
		espacoDeMagia.usarEspaco();
		espacoDeMagia.usarEspaco();

		assertEquals(0, espacoDeMagia.getQuantidade());
	}

	@Test
	public void garantirQueQuantidadeNaoUltrapasseQuantidadeMaxima() {
		espacoDeMagia.restauraUmEspaco();

		assertEquals(2, espacoDeMagia.getQuantidade());
	}

	@Test
	public void mudarQuantidadeMaximaDeEspacos() {
		espacoDeMagia.setQuantidadeMaxima(4);

		assertEquals(4, espacoDeMagia.getQuantidadeMaxima());
	}

	@Test(expected = DominioInvalidoException.class)
	public void criarEspacoComNivelInvalido() {
		new EspacoDeMagia(10, 3);
	}
	
	@Test(expected = DominioInvalidoException.class)
	public void criarEspacoComQuantidadeInvalida() {
		new EspacoDeMagia(6, 5);
	}
	
	@Test(expected = DominioInvalidoException.class)
	public void mudarQuantidadeDeEspacosParaValorInvalido() {
		new EspacoDeMagia(6, 3).setQuantidadeMaxima(5);
	}
	
}
