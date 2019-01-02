package br.com.assist.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.assist.domain.Classe;
import br.com.assist.domain.EscolaDeMagia;
import br.com.assist.domain.EspacoDeMagia;
import br.com.assist.domain.Recuperacao;
import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.MagiaDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.domain.HabilidadePersonagem;
import br.com.assist.domain.Personagem;
import br.com.assist.domain.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonagemServiceTest {

	@Autowired
	private PersonagemService personagemService;

	@Autowired
	private MagiaService magiaService;
	
	private PersonagemDto personagem;
			
	@Before
	public void criarDominios() {
		personagem = new PersonagemDto();
		personagem.setNome("Angor Rot");
		personagem.setNivel(1);
		personagem.setVidaMax(10);
		personagem.setRaca(Raca.MEIO_ORC);
		personagem.setClasse(Classe.BRUXO);
		personagem.setImg("Imagem");
	}

	@After
	public void deletarBaseDeDados() {
		personagemService.deletarTudo();
		magiaService.deletarTudo();
	}

	@Test
	public void salvarPersonagem() {
		personagemService.salvar(personagem);

		List<PersonagemHomePageDto> personagens = personagemService.buscarTodosPersonagens();

		assertEquals(1, personagens.size());

		PersonagemHomePageDto personagemNaBase = personagens.get(0);

		assertEquals("Angor Rot", personagemNaBase.getNome());
		assertEquals(1, personagemNaBase.getNivel());
		assertEquals(10, personagemNaBase.getVidaMax());
		assertEquals(10, personagemNaBase.getVidaAtual());
		assertEquals("Meio-Orc", personagemNaBase.getRaca());
		assertEquals("Bruxo", personagemNaBase.getClasse());
		assertEquals("Imagem", personagemNaBase.getImg());
	}
	
	@Test
	public void buscarTodosPersonagensDaBase() {
		PersonagemDto personagem2 = new PersonagemDto();
		personagem2.setNome("Jorge Glaerd");
		personagem2.setNivel(1);
		personagem2.setVidaMax(10);
		personagem2.setRaca(Raca.MEIO_ELFO);
		personagem2.setClasse(Classe.BARDO);
		personagem2.setImg("Imagem");

		personagemService.salvar(personagem);
		personagemService.salvar(personagem2);

		List<PersonagemHomePageDto> personagens = personagemService.buscarTodosPersonagens();

		assertEquals(2, personagens.size());
	}

	@Test
	public void buscarPersonagemPorId() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();

		PersonagemDetalhesDto personagemEncontrado = personagemService.buscarPorId(idPersonagemNaBase);

		assertEquals("Angor Rot", personagemEncontrado.getNome());
	}
	
	@Test(expected = ServiceException.class)
	public void buscarPersonagemNaoExistentePorId() {
		personagemService.buscarPorId(1);
	}

	@Test
	public void adicionarHabilidadeParaPersonagem() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();

		personagemService.salvarHabilidadeParaPersonagem(idPersonagemNaBase, criarHabilidadeDto());

		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);

		assertEquals(2, habilidadePersonagem.getQtdUsosMaximo());
		assertEquals(2, habilidadePersonagem.getQtdUsosRestantes());
		assertEquals("Invocações Místicas", habilidadePersonagem.getHabilidade().getNome());
		assertEquals("Descrição", habilidadePersonagem.getHabilidade().getDescricao());
		assertEquals(Recuperacao.DESCANSO_CURTO, habilidadePersonagem.getRecuperacao());
	}

	@Test
	public void usarHabilidade() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		personagemService.salvarHabilidadeParaPersonagem(idPersonagemNaBase, criarHabilidadeDto());

		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);
				
		personagemService.usarHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());
		
		personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(1, personagemNaBase.getHabilidades().get(0).getQtdUsosRestantes());
	}
	
	@Test
	public void restaurarUsosdeHabilidade() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		personagemService.salvarHabilidadeParaPersonagem(idPersonagemNaBase, criarHabilidadeDto());

		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);

		personagemService.usarHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());

		personagemService.restaurarUsosDeHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());

		personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(2, personagemNaBase.getHabilidades().get(0).getQtdUsosRestantes());
	}
	
	@Test
	public void removerHabilidade() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		personagemService.salvarHabilidadeParaPersonagem(idPersonagemNaBase, criarHabilidadeDto());

		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		Integer idHabilidade = personagemNaBase.getHabilidades().get(0).getHabilidade().getId();
		
		personagemService.removerHabilidade(idPersonagemNaBase, idHabilidade);

		personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(0, personagemNaBase.getHabilidades().size());
	}
	
	
	@Test
	public void adicionarMagiaParaPersoangem() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();

		magiaService.salvar(criarMagiaDto());
		Integer idMagiaNaBase = magiaService.buscarTodasMagias().get(0).getId();
		
		personagemService.salvarMagiaParaPersonagem(idPersonagemNaBase, idMagiaNaBase);
		
		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(1, personagemNaBase.getMagias().size());
		assertEquals("Bola de Fogo", personagemNaBase.getMagias().get(0).getMagia().getNome());
	}
	
	@Test
	public void prepararMagia() {
		personagem.setClasse(Classe.MAGO);
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();

		magiaService.salvar(criarMagiaDto());
		Integer idMagiaNaBase = magiaService.buscarTodasMagias().get(0).getId();
		System.out.println(idMagiaNaBase);
		
		personagemService.prepararMagia(idPersonagemNaBase, idMagiaNaBase);
		
		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);

		assertTrue(personagemNaBase.getMagias().get(0).isPreparada());
	}
	
	@Test
	public void adicionarEspacoDeMagiaParaPersonagem() {
		personagemService.salvar(personagem);		
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		
		personagemService.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, criarEspacoDeMagiaDto());
		
		PersonagemDetalhesDto personagemNaBase = personagemService.buscarPorId(idPersonagemNaBase);

		EspacoDeMagia espaco = personagemNaBase.getEspacosDeMagia().get(0);
		
		assertEquals(1, personagemNaBase.getEspacosDeMagia().size());
		assertEquals(1, espaco.getNivel());
		assertEquals(2, espaco.getQuantidadeMaxima());
		assertEquals(2, espaco.getQuantidade());
	}
	
	@Test
	public void conjurarMagia() {
		personagemService.salvar(personagem);		
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		personagemService.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, criarEspacoDeMagiaDto());
		
		personagemService.conjurarMagia(idPersonagemNaBase, criarEspacoDeMagiaDto().getNivel());
		
		PersonagemDetalhesDto personagem = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(1, personagem.getEspacosDeMagia().get(0).getQuantidade());
	}
	
	@Test
	public void restaurarEspacoDeMagia() {
		personagemService.salvar(personagem);		
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		
		EspacoDeMagiaDto espacoDto = criarEspacoDeMagiaDto();
		
		personagemService.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, espacoDto);
		personagemService.conjurarMagia(idPersonagemNaBase, espacoDto.getNivel());
		
		personagemService.restaurarEspacoDeMagia(idPersonagemNaBase, espacoDto.getNivel());
		
		PersonagemDetalhesDto personagem = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(2, personagem.getEspacosDeMagia().get(0).getQuantidade());
	}
	
	@Test
	public void usarDadoDeVida() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();
		PersonagemDetalhesDto personagem = personagemService.buscarPorId(idPersonagemNaBase);
		
		personagemService.usarDadoDeVida(personagem.getId());
		
		personagem = personagemService.buscarPorId(idPersonagemNaBase);
		
		assertEquals(0, personagem.getDadosDeVida());
	}
	
	@Test
	public void removerPersonagemPorId() {
		personagemService.salvar(personagem);
		Integer idPersonagemNaBase = personagemService.buscarTodosPersonagens().get(0).getId();

		personagemService.deletarPorId(idPersonagemNaBase);

		assertEquals(0, personagemService.buscarTodosPersonagens().size());
	}

	@Test
	public void removerTodosPersonagens() {
		PersonagemDto personagem2 = new PersonagemDto();
		personagem2.setNome("Jorge Glaerd");
		personagem2.setNivel(1);
		personagem2.setVidaMax(10);
		personagem2.setRaca(Raca.MEIO_ELFO);
		personagem2.setClasse(Classe.BARDO);
		personagem2.setImg("Imagem");

		personagemService.salvar(personagem);
		personagemService.salvar(personagem2);

		personagemService.deletarTudo();

		assertEquals(0, personagemService.buscarTodosPersonagens().size());
	}
	
	private HabilidadeDto criarHabilidadeDto() {
		HabilidadeDto habilidadeDto = new HabilidadeDto();
		habilidadeDto.setNome("Invocações Místicas");
		habilidadeDto.setDescricao("Descrição");
		habilidadeDto.setQtdUsosMaximo(2);
		habilidadeDto.setRecuperacao(Recuperacao.DESCANSO_CURTO);
		
		return habilidadeDto;
	}

	private EspacoDeMagiaDto criarEspacoDeMagiaDto() {
		EspacoDeMagiaDto espacoDto = new EspacoDeMagiaDto();
		espacoDto.setNivel(1);
		espacoDto.setQuantidadeMaxima(2);
		return espacoDto;
	}
	
	private MagiaDto criarMagiaDto() {
		MagiaDto magiaDto = new MagiaDto();
		magiaDto.setNome("Bola de Fogo");
		magiaDto.setNivel(3);
		magiaDto.setEscola(EscolaDeMagia.EVOCACAO);
		magiaDto.setRitual(false);
		magiaDto.setTempoDeConjuracao("1 ação");
		magiaDto.setAlcance("45m");
		magiaDto.setComponentes("V S M");
		magiaDto.setDuracao("Instantânea");
		magiaDto.setDescricao("descricão");
		
		return magiaDto;
	}
	
}
