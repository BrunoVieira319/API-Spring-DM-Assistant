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
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Classe;
import br.com.assist.domain.Recuperacao;
import br.com.assist.dto.HabilidadeDto;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.domain.Habilidade;
import br.com.assist.domain.Personagem;
import br.com.assist.domain.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonagemServiceTest {

	@Autowired
	private PersonagemService service;
	
	private PersonagemDto personagem;

	@Before
	public void criarUmPersonagem() {
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
		service.deletarTudo();
	}

	@Test
	public void salvarPersonagem() {
		service.salvar(personagem);

		List<Personagem> personagens = service.listarTodos();
		
		assertEquals(1, personagens.size());
		
		Personagem personagemNaBase = personagens.get(0);

		assertEquals("Angor Rot", personagemNaBase.getNome());
		assertEquals(1, personagemNaBase.getNivel());
		assertEquals(10, personagemNaBase.getVidaMax());
		assertEquals(10, personagemNaBase.getVidaAtual());
		assertEquals(Raca.MEIO_ORC, personagemNaBase.getRaca());
		assertEquals(Classe.BRUXO, personagemNaBase.getClasse());
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

		List<Personagem> personagens = service.listarTodos();

		assertEquals(2, personagens.size());
	}

	@Test
	public void adicionarHabilidadeParaPersonagem() {
		service.salvar(personagem);
		
		assertEquals(1, service.listarTodos().size());
		
		Integer idPersonagemNaBase = service.listarTodos().get(0).getId();
		
		HabilidadeDto hDto = new HabilidadeDto();
		hDto.setNome("Invocações Místicas");
		hDto.setDescricao("Descrição");
		hDto.setQtdUsosMaximo(0);
		hDto.setRecuperacao(Recuperacao.SEM_LIMITE);

		service.salvarHabilidadeParaPersonagem(idPersonagemNaBase, hDto);
		
		PersonagemDetalhesDto personagemNaBase = service.buscarPorId(idPersonagemNaBase);
		
		assertEquals(0, personagemNaBase.getHabilidades().get(0).getQtdUsosMaximo());
	}
	
	@Test
	public void buscarPersonagemPorId() {
		service.salvar(personagem);

		List<Personagem> personagens = service.listarTodos();
		
		assertEquals(1, personagens.size());
		
		Personagem personagemNaBase = personagens.get(0);
		PersonagemDetalhesDto personagemEncontrado = service.buscarPorId(personagemNaBase.getId());

		assertEquals("Angor Rot", personagemEncontrado.getNome());
	}
	
	// @Test(expected = ServiceException.class)
	// public void deletarPersonagemPorId() {
	// Personagem personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC,
	// Classe.BRUXO, "Imagem");
	// service.salvar(personagem);
	//
	// Personagem personagemEncontrado = service.buscarPorNome("Angor Rot");
	//
	// service.deletarPorId(personagemEncontrado.getId());
	//
	// service.buscarPorId(personagemEncontrado.getId());
	// }
	//
	// @Test
	// public void deletarTodosPersonagens() {
	// Personagem personagem1 = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC,
	// Classe.BRUXO, "Imagem");
	// Personagem personagem2 = new Personagem("Jorge Glaerd", 1, 10,
	// Raca.MEIO_ELFO, Classe.BARDO, "Imagem");
	//
	// service.salvar(personagem1);
	// service.salvar(personagem2);
	//
	// service.deletarTudo();
	//
	// List<Personagem> personagens = service.listarTodos();
	//
	// assertEquals(0, personagens.size());
	// }
	//
	// @Test(expected = ServiceException.class)
	// public void buscarPersonagemNaoExistentePorId() {
	// service.buscarPorId(1);
	// }
	//
	// @Test(expected = ServiceException.class)
	// public void buscarPersonagemNaoExistentePorNome() {
	// service.buscarPorNome("Angor Rot");
	// }
}
