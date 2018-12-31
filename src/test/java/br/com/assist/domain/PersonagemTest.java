package br.com.assist.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class PersonagemTest {

	private Personagem personagem;

	@Before
	public void instanciarPersonagem() {
		personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
	}

	@Test
	public void criarPersonagem() {
		Personagem personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");

		assertEquals("Angor Rot", personagem.getNome());
		assertEquals(1, personagem.getNivel());
		assertEquals(10, personagem.getVidaMax());
		assertEquals(Raca.MEIO_ORC, personagem.getRaca());
		assertEquals(Classe.BRUXO, personagem.getClasse());
		assertEquals("Imagem", personagem.getImg());
	}

	@Test
	public void subirNivelDoPersonagem() {
		personagem.subirDeNivel();
		assertEquals(2, personagem.getNivel());
	}

	@Test
	public void nivelDePersonagemNaoDeveUltrapassarVinte() {
		personagem = new Personagem("Angor Rot", 20, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");

		personagem.subirDeNivel();

		assertEquals(20, personagem.getNivel());
	}

	@Test
	public void mudarVidaAtual() {
		personagem.setVidaAtual(3);

		assertEquals(3, personagem.getVidaAtual());
	}

	@Test(expected = DominioInvalidoException.class)
	public void mudarVidaAtualParaUmValorNegativo() {
		personagem.setVidaAtual(-4);
	}

	@Test(expected = DominioInvalidoException.class)
	public void mudarVidaAtualParaUmValorMaiorQueVidaMaxima() {
		personagem.setVidaAtual(11);
	}

	@Test
	public void usarDadoDeVida() {
		personagem.usarDadoDeVida();
		assertEquals(0, personagem.getDadosDeVida());
	}

	@Test
	public void adicionarHabilidadeParaPersonagem() {
		personagem.adicionarHabilidade(new Habilidade("Pacto da Lâmina", "descricao"), 0, Recuperacao.SEM_LIMITE);

		assertEquals(1, personagem.getHabilidades().size());
		assertEquals("Pacto da Lâmina", personagem.getHabilidades().get(0).getHabilidade().getNome());
	}

	@Test
	public void adicionarMagiaParaPersonagem() {
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M",
				"Instantânea", "descrição");
		personagem.adicionarMagia(magia);

		assertEquals(1, personagem.getMagias().size());
		assertEquals(magia, personagem.getMagias().get(0).getMagia());
	}

	@Test(expected = DominioInvalidoException.class)
	public void adicionarMagiaRepetida() {
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M",
				"Instantânea", "descrição");
		personagem.adicionarMagia(magia);
		personagem.adicionarMagia(magia);
	}

	@Test
	public void prepararMagia() {
		personagem = new Personagem("Simpson", 1, 7, Raca.HUMANO, Classe.MAGO, "Imagem");
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M",
				"Instantânea", "descrição");
		personagem.adicionarMagia(magia);

		personagem.prepararMagia(null);

		assertTrue(personagem.getMagias().get(0).isPreparada());
	}

	@Test
	public void desprepararMagia() {
		personagem = new Personagem("Simpson", 1, 7, Raca.HUMANO, Classe.MAGO, "Imagem");
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M",
				"Instantânea", "descrição");
		personagem.adicionarMagia(magia);

		personagem.prepararMagia(null);
		personagem.desprepararMagia(null);

		assertFalse(personagem.getMagias().get(0).isPreparada());
	}

	@Test(expected = DominioInvalidoException.class)
	public void garantirQueSomenteClerigoDruidaMagoPaladinoPreparemMagias() {
		Magia magia = new Magia("Bola De Fogo", 3, EscolaDeMagia.EVOCACAO, false, "1 ação", "45m", "V S M",
				"Instantânea", "descrição");
		personagem.adicionarMagia(magia);

		personagem.prepararMagia(null);
	}

	@Test
	public void adicionarEspacosDeMagiaParaPersonagem() {
		personagem.adicionarEspacoDeMagia(1, 3);
		assertEquals(1, personagem.getEspacosDeMagia().size());
	}

	@Test
	public void classeBruxoSoPodeTerUmEspacoDeMagia() {
		personagem.adicionarEspacoDeMagia(1, 2);
		personagem.adicionarEspacoDeMagia(2, 2);
		personagem.adicionarEspacoDeMagia(3, 2);
		assertEquals(1, personagem.getEspacosDeMagia().size());
	}

	@Test
	public void sobrescreverEspacoDeMagiaSeNivelForIgual() {
		personagem = new Personagem("Simpson", 1, 7, Raca.HUMANO, Classe.MAGO, "Imagem");

		personagem.adicionarEspacoDeMagia(1, 2);
		personagem.adicionarEspacoDeMagia(1, 3);
		assertEquals(1, personagem.getEspacosDeMagia().size());
		assertEquals(3, personagem.getEspacoDeMagia(1).getQuantidadeMaxima());
	}

	@Test
	public void usarHabilidade() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);

		personagem.usarHabilidade(null);

		assertEquals(0, personagem.getHabilidades().get(0).getQtdUsosRestantes());
	}

	@Test
	public void restaurarUsosDeHabilidade() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
		personagem.usarHabilidade(null);

		personagem.restaurarUsosHabilidade(null);

		assertEquals(1, personagem.getHabilidades().get(0).getQtdUsosRestantes());
	}

	@Test
	public void removerHabilidadeDePersonagem() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);

		personagem.removerHabilidade(null);

		assertEquals(0, personagem.getHabilidades().size());
	}

	@Test
	public void usarEspacoDeMagia() {
		personagem.adicionarEspacoDeMagia(1, 3);

		personagem.conjurarMagia(1);

		EspacoDeMagia espacoDeMagiaNv1 = personagem.getEspacoDeMagia(1);
		assertEquals(2, espacoDeMagiaNv1.getQuantidade());
	}

	@Test
	public void restaurarUmEspacoDeMagia() {
		personagem.adicionarEspacoDeMagia(1, 3);
		personagem.conjurarMagia(1);

		personagem.restaurarEspacoDeMagia(1);

		EspacoDeMagia espacoDeMagiaNv1 = personagem.getEspacoDeMagia(1);
		assertEquals(3, espacoDeMagiaNv1.getQuantidade());
	}

	@Test
	public void descansar() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
		personagem.adicionarEspacoDeMagia(1, 3);

		personagem.setVidaAtual(2);
		personagem.usarDadoDeVida();
		personagem.usarHabilidade(null);
		personagem.conjurarMagia(1);

		personagem.descansar();

		assertEquals(10, personagem.getVidaAtual());
		assertEquals(1, personagem.getDadosDeVida());
		assertEquals(1, personagem.getHabilidades().get(0).getQtdUsosRestantes());
		assertEquals(3, personagem.getEspacoDeMagia(1).getQuantidade());
	}

}
