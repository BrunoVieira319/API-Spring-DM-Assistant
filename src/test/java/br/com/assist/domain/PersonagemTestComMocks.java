package br.com.assist.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonagemTestComMocks {

    private Personagem personagemBruxo;
    private Personagem personagemMago;

    @Mock
    private Habilidade habilidade;

    @Mock
    private Magia magia;


    @Before
    public void instanciarPersonagem() {
        personagemBruxo = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
        personagemMago = new Personagem("Simpson", 1, 7, Raca.HUMANO, Classe.MAGO, "Imagem");
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
    public void mudarVidaAtual() {
        personagemBruxo.setVidaAtual(3);

        assertEquals(3, personagemBruxo.getVidaAtual());
    }

    @Test(expected = DominioInvalidoException.class)
    public void mudarVidaAtualParaUmValorNegativo() {
        personagemBruxo.setVidaAtual(-4);
    }

    @Test(expected = DominioInvalidoException.class)
    public void mudarVidaAtualParaUmValorMaiorQueVidaMaxima() {
        personagemBruxo.setVidaAtual(11);
    }

    @Test
    public void usarDadoDeVida() {
        personagemBruxo.usarDadoDeVida();
        assertEquals(0, personagemBruxo.getDadosDeVida());
    }

    @Test
    public void adicionarHabilidadeParaPersonagem() {
        personagemBruxo.adicionarHabilidade(habilidade, 0, Recuperacao.SEM_LIMITE);
        assertEquals(1, personagemBruxo.getHabilidades().size());
    }

    @Test
    public void adicionarMagiaParaPersonagem() {
        personagemBruxo.adicionarMagia(magia);

        assertEquals(1, personagemBruxo.getMagias().size());
        assertEquals(magia, personagemBruxo.getMagias().get(0).getMagia());
    }

    @Test(expected = DominioInvalidoException.class)
    public void adicionarMagiaRepetida() {
        personagemBruxo.adicionarMagia(magia);
        personagemBruxo.adicionarMagia(magia);
    }

    @Test
    public void removerMagiaDoPersonagem() {
        personagemBruxo.adicionarMagia(magia);
        personagemBruxo.removerMagia(0);

        assertEquals(0, personagemBruxo.getMagias().size());
    }

    @Test
    public void removerHabilidadeDePersonagem() {
        personagemBruxo.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
        personagemBruxo.removerHabilidade(0);

        assertEquals(0, personagemBruxo.getHabilidades().size());
    }

    @Test
    public void prepararMagia() {
        personagemMago.adicionarMagia(magia);
        personagemMago.prepararMagia(0);

        assertTrue(personagemMago.getMagias().get(0).isPreparada());
    }

    @Test
    public void desprepararMagia() {
        personagemMago.adicionarMagia(magia);
        personagemMago.prepararMagia(0);
        personagemMago.desprepararMagia(0);

        assertFalse(personagemMago.getMagias().get(0).isPreparada());
    }

    @Test(expected = DominioInvalidoException.class)
    public void garantirQueSomenteClerigoDruidaMagoPaladinoPreparemMagias() {
        personagemBruxo.adicionarMagia(magia);
        personagemBruxo.prepararMagia(0);
    }

    @Test
    public void adicionarEspacosDeMagiaParaPersonagem() {
        personagemBruxo.adicionarEspacoDeMagia(1, 3);
        assertEquals(1, personagemBruxo.getEspacosDeMagia().size());
    }

    @Test
    public void classeBruxoSoPodeTerUmEspacoDeMagia() {
        personagemBruxo.adicionarEspacoDeMagia(1, 2);
        personagemBruxo.adicionarEspacoDeMagia(2, 2);
        personagemBruxo.adicionarEspacoDeMagia(3, 2);
        assertEquals(1, personagemBruxo.getEspacosDeMagia().size());
    }

    @Test
    public void sobrescreverEspacoDeMagiaSeNivelForIgual() {
        personagemMago.adicionarEspacoDeMagia(1, 2);
        personagemMago.adicionarEspacoDeMagia(1, 3);
        assertEquals(1, personagemMago.getEspacosDeMagia().size());
        assertEquals(3, personagemMago.getEspacoDeMagia(1).getQuantidadeMaxima());
    }

    @Test
    public void usarEspacoDeMagia() {
        personagemBruxo.adicionarEspacoDeMagia(1, 3);
        personagemBruxo.conjurarMagia(1);

        EspacoDeMagia espacoDeMagiaNv1 = personagemBruxo.getEspacoDeMagia(1);
        assertEquals(2, espacoDeMagiaNv1.getQuantidade());
    }

    @Test
    public void usarHabilidade() {
        personagemBruxo.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
        personagemBruxo.usarHabilidade(0);

        assertEquals(0, personagemBruxo.getHabilidades().get(0).getQtdUsosRestantes());
    }

    @Test
    public void restaurarUsosDeHabilidade() {
        personagemBruxo.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
        personagemBruxo.usarHabilidade(0);
        personagemBruxo.restaurarUsosHabilidade(0);

        assertEquals(1, personagemBruxo.getHabilidades().get(0).getQtdUsosRestantes());
    }

    @Test
    public void restaurarUmEspacoDeMagia() {
        personagemBruxo.adicionarEspacoDeMagia(1, 3);
        personagemBruxo.conjurarMagia(1);
        personagemBruxo.restaurarEspacoDeMagia(1);

        EspacoDeMagia espacoDeMagiaNv1 = personagemBruxo.getEspacoDeMagia(1);
        assertEquals(3, espacoDeMagiaNv1.getQuantidade());
    }

    @Test
    public void descansar() {
        personagemBruxo.adicionarHabilidade(habilidade, 1, Recuperacao.DESCANSO_CURTO);
        personagemBruxo.adicionarEspacoDeMagia(1, 3);

        personagemBruxo.setVidaAtual(2);
        personagemBruxo.usarDadoDeVida();
        personagemBruxo.usarHabilidade(0);
        personagemBruxo.conjurarMagia(1);

        personagemBruxo.descansar();

        assertEquals(10, personagemBruxo.getVidaAtual());
        assertEquals(1, personagemBruxo.getDadosDeVida());
        assertEquals(1, personagemBruxo.getHabilidades().get(0).getQtdUsosRestantes());
        assertEquals(3, personagemBruxo.getEspacoDeMagia(1).getQuantidade());
    }

    @Test
    public void mudarNivel() {
        personagemBruxo.setNivel(2);
        assertEquals(2, personagemBruxo.getNivel());
    }

    @Test
    public void mudarVidaMaxima() {
        personagemBruxo.setVidaMax(20);
        assertEquals(20, personagemBruxo.getVidaMax());
    }

    @Test
    public void mudarImg() {
        personagemBruxo.setImg("outra imagem");
        assertEquals("outra imagem", personagemBruxo.getImg());
    }

}
