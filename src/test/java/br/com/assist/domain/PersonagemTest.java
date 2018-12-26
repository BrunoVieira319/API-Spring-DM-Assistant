package br.com.assist.domain;

import static org.junit.Assert.assertEquals;

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
	public void causarDanoNoPersonagem() {
		personagem.sofrerDano(8);
		assertEquals(2, personagem.getVidaAtual());
	}

	@Test
	public void curarPersonagem() {
		personagem.sofrerDano(8);
		personagem.curar(5);
		assertEquals(7, personagem.getVidaAtual());
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

	}

	@Test
	public void adicionarEspacosDeMagiaParaPersonagem() {
		personagem.adicionarEspacoDeMagia(1, 3);
		assertEquals(1, personagem.getEspacosDeMagia().size());
	}

	@Test
	public void usarHabilidade() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);

		personagem.usarHabilidade(habilidade);

		assertEquals(0, personagem.getQtdUsosRestantesDeHabilidade(habilidade));
	}

	@Test
	public void restaurarUsosDeHabilidade() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
		personagem.usarHabilidade(habilidade);

		personagem.restaurarUsosHabilidade(habilidade);

		assertEquals(1, personagem.getQtdUsosRestantesDeHabilidade(habilidade));
	}
	
	@Test
	public void removerHabilidadeDePersonagem() {
		Habilidade habilidade = new Habilidade("Sorte Do Próprio Obscuro", "descricao");
		personagem.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
		
		personagem.removerHabilidade(habilidade);
		
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
		
		personagem.sofrerDano(8);
		personagem.usarDadoDeVida();
		personagem.usarHabilidade(habilidade);
		personagem.conjurarMagia(1);
		
		personagem.descansar();
		
		assertEquals(10, personagem.getVidaAtual());
		assertEquals(1, personagem.getDadosDeVida());
		assertEquals(1, personagem.getQtdUsosRestantesDeHabilidade(habilidade));
		assertEquals(3, personagem.getEspacoDeMagia(1).getQuantidade());
	}

}
