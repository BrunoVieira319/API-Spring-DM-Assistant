package br.com.assist.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HabilidadePersonagemTest {

	private Habilidade habilidade;

	@Before
	public void instanciarHabilidade() {
		habilidade = new Habilidade("Canalizar Divindade", "Descrição");
	}

	@Test
	public void criarHabilidadePersonagem() {
		HabilidadePersonagem hp = new HabilidadePersonagem(habilidade, 2, Recuperacao.DESCANSO_CURTO);

		assertEquals(2, hp.getQtdUsosMaximo());
		assertEquals(2, hp.getQtdUsosRestantes());
		assertEquals(Recuperacao.DESCANSO_CURTO, hp.getRecuperacao());
		assertEquals(habilidade, hp.getHabilidade());
	}

	@Test
	public void usarHabilidade() {
		HabilidadePersonagem hp = new HabilidadePersonagem(habilidade, 2, Recuperacao.DESCANSO_CURTO);
		hp.usarHabilidade();

		assertEquals(1, hp.getQtdUsosRestantes());
	}

	@Test
	public void garantirQueQuantidadeDeUsosNaoSejaNegativo() {
		HabilidadePersonagem hp = new HabilidadePersonagem(habilidade, 2, Recuperacao.DESCANSO_CURTO);
		hp.usarHabilidade();
		hp.usarHabilidade();
		hp.usarHabilidade();
		hp.usarHabilidade();

		assertEquals(0, hp.getQtdUsosRestantes());
	}

	@Test
	public void restaurarUsos() {
		HabilidadePersonagem hp = new HabilidadePersonagem(habilidade, 2, Recuperacao.DESCANSO_CURTO);
		hp.usarHabilidade();
		hp.usarHabilidade();

		hp.restaurarUsos();

		assertEquals(2, hp.getQtdUsosRestantes());
	}

	@Test(expected = DominioInvalidoException.class)
	public void criarHabilidadePersonagemComHabilidadeNull() {
		new HabilidadePersonagem(null, 2, Recuperacao.DESCANSO_CURTO);
	}

	@Test(expected = DominioInvalidoException.class)
	public void criarHabilidadePersonagemComUsosNegativo() {
		new HabilidadePersonagem(habilidade, -1, Recuperacao.DESCANSO_CURTO);
	}

	@Test(expected = DominioInvalidoException.class)
	public void criarHabilidadePersonagemComDescansoNull() {
		new HabilidadePersonagem(habilidade, 2, null);
	}

}
