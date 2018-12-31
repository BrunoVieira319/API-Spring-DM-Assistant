package br.com.assist.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.assist.domain.Classe;
import br.com.assist.domain.EspacoDeMagia;
import br.com.assist.domain.Recuperacao;
import br.com.assist.dto.EspacoDeMagiaDto;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.dto.PersonagemHomePageDto;
import br.com.assist.domain.HabilidadePersonagem;
import br.com.assist.domain.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonagemServiceTest {

	@Autowired
	private PersonagemService service;

	private PersonagemDto personagem;
	
	private HabilidadeDto habilidadeDto;
	
	private EspacoDeMagiaDto espacoDto;

	@Before
	public void criarUmPersonagem() {
		personagem = new PersonagemDto();
		personagem.setNome("Angor Rot");
		personagem.setNivel(1);
		personagem.setVidaMax(10);
		personagem.setRaca(Raca.MEIO_ORC);
		personagem.setClasse(Classe.BRUXO);
		personagem.setImg("Imagem");
		
		habilidadeDto = new HabilidadeDto();
		habilidadeDto.setNome("Invocações Místicas");
		habilidadeDto.setDescricao("Descrição");
		habilidadeDto.setQtdUsosMaximo(2);
		habilidadeDto.setRecuperacao(Recuperacao.DESCANSO_CURTO);
		
		espacoDto = new EspacoDeMagiaDto();
		espacoDto.setNivel(1);
		espacoDto.setQuantidadeMaxima(2);
	}

	@After
	public void deletarBaseDeDados() {
		service.deletarTudo();
	}

	@Test
	public void salvarPersonagem() {
		service.salvar(personagem);

		List<PersonagemHomePageDto> personagens = service.buscarTodosPersonagens();

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

		service.salvar(personagem);
		service.salvar(personagem2);

		List<PersonagemHomePageDto> personagens = service.buscarTodosPersonagens();

		assertEquals(2, personagens.size());
	}

	@Test
	public void buscarPersonagemPorId() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();

		PersonagemDetalhesDto personagemEncontrado = service.buscarPorId(idPersonagemNaBase);

		assertEquals("Angor Rot", personagemEncontrado.getNome());
	}
	
	@Test(expected = ServiceException.class)
	public void buscarPersonagemNaoExistentePorId() {
		service.buscarPorId(1);
	}

	@Test
	public void adicionarHabilidadeParaPersonagem() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();

		service.salvarHabilidadeParaPersonagem(idPersonagemNaBase, habilidadeDto);

		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);

		assertEquals(2, habilidadePersonagem.getQtdUsosMaximo());
		assertEquals(2, habilidadePersonagem.getQtdUsosRestantes());
		assertEquals("Invocações Místicas", habilidadePersonagem.getHabilidade().getNome());
		assertEquals("Descrição", habilidadePersonagem.getHabilidade().getDescricao());
		assertEquals(Recuperacao.DESCANSO_CURTO, habilidadePersonagem.getRecuperacao());
	}

	@Test
	public void usarHabilidade() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		service.salvarHabilidadeParaPersonagem(idPersonagemNaBase, habilidadeDto);

		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);
				
		service.usarHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());
		
		personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(1, personagemNaBase.getHabilidades().get(0).getQtdUsosRestantes());
	}
	
	@Test
	public void restaurarUsosdeHabilidade() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		service.salvarHabilidadeParaPersonagem(idPersonagemNaBase, habilidadeDto);

		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		HabilidadePersonagem habilidadePersonagem = personagemNaBase.getHabilidades().get(0);

		service.usarHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());

		service.restaurarUsosDeHabilidade(personagemNaBase.getId(), habilidadePersonagem.getHabilidade().getId());

		personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(2, personagemNaBase.getHabilidades().get(0).getQtdUsosRestantes());
	}
	
	@Test
	public void removerHabilidade() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		service.salvarHabilidadeParaPersonagem(idPersonagemNaBase, habilidadeDto);

		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		Integer idHabilidade = personagemNaBase.getHabilidades().get(0).getHabilidade().getId();
		
		service.removerHabilidade(idPersonagemNaBase, idHabilidade);

		personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(0, personagemNaBase.getHabilidades().size());
	}
	
	
	@Test
	public void adicionarMagiaParaPersoangem() {
	}
	
	@Test
	public void adicionarEspacoDeMagiaParaPersonagem() {
		service.salvar(personagem);		
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		
		service.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, espacoDto);
		
		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);

		EspacoDeMagia espaco = personagemNaBase.getEspacosDeMagia().get(0);
		
		assertEquals(1, personagemNaBase.getEspacosDeMagia().size());
		assertEquals(1, espaco.getNivel());
		assertEquals(2, espaco.getQuantidadeMaxima());
		assertEquals(2, espaco.getQuantidade());
	}
	
	@Test
	public void conjurarMagia() {
		service.salvar(personagem);		
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		service.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, espacoDto);
		
		service.conjurarMagia(idPersonagemNaBase, espacoDto.getNivel());
		
		PersonagemDetalhesDto personagem = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(1, personagem.getEspacosDeMagia().get(0).getQuantidade());
	}
	
	@Test
	public void restaurarEspacoDeMagia() {
		service.salvar(personagem);		
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		service.salvarEspacoDeMagiaParaPersonagem(idPersonagemNaBase, espacoDto);
		service.conjurarMagia(idPersonagemNaBase, espacoDto.getNivel());
		
		service.restaurarEspacoDeMagia(idPersonagemNaBase, espacoDto.getNivel());
		
		PersonagemDetalhesDto personagem = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(2, personagem.getEspacosDeMagia().get(0).getQuantidade());
	}
	
	@Test
	public void usarDadoDeVida() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();
		PersonagemDetalhesDto personagem = service.buscarPorId(idPersonagemNaBase);
		
		service.usarDadoDeVida(personagem.getId());
		
		personagem = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(0, personagem.getDadosDeVida());
	}
	
	@Test
	public void removerPersonagemPorId() {
		service.salvar(personagem);
		Integer idPersonagemNaBase = service.buscarTodosPersonagens().get(0).getId();

		service.deletarPorId(idPersonagemNaBase);

		assertEquals(0, service.buscarTodosPersonagens().size());
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

		service.salvar(personagem);
		service.salvar(personagem2);

		service.deletarTudo();

		assertEquals(0, service.buscarTodosPersonagens().size());
	}

}
