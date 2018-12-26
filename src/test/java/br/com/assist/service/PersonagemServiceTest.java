package br.com.assist.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.assist.domain.Classe;
import br.com.assist.domain.Recuperacao;
import br.com.assist.domain.Habilidade;
import br.com.assist.domain.Personagem;
import br.com.assist.domain.Raca;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonagemServiceTest {

	@Autowired
	private PersonagemService service;

	@After
	public void deletarBaseDeDados() {
		service.deletarTudo();
	}

	@Test
	@Transactional
	public void salvarPersonagem() {
		Personagem personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
		personagem.adicionarHabilidade(new Habilidade("Pacto da Lâmina", "desc"), 0, Recuperacao.SEM_LIMITE);

		service.salvar(personagem);

		Personagem personagemEncontrado = service.buscarPorNome("Angor Rot");

		assertEquals("Angor Rot", personagemEncontrado.getNome());
		assertEquals(1, personagemEncontrado.getNivel());
		assertEquals(1, personagemEncontrado.getHabilidades().size());
	}

	@Test
	public void buscarTodosPersonagensDaBase() {
		Personagem personagem1 = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
		Personagem personagem2 = new Personagem("Jorge Glaerd", 1, 10, Raca.MEIO_ELFO, Classe.BARDO, "Imagem");

		service.salvar(personagem1);
		service.salvar(personagem2);

		List<Personagem> personagens = service.listarTodos();

		assertEquals(2, personagens.size());
	}

	@Test
	public void buscarPersonagemPorId() {
		Personagem personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
		service.salvar(personagem);

		Personagem personagemEncontradoPorNome = service.buscarPorNome("Angor Rot");
		Personagem personagemEncontradoPorId = service.buscarPorId(personagemEncontradoPorNome.getId());

		assertEquals("Angor Rot", personagemEncontradoPorId.getNome());
		assertEquals(1, personagemEncontradoPorId.getNivel());
	}

	@Test(expected = ServiceException.class)
	public void deletarPersonagemPorId() {
		Personagem personagem = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
		service.salvar(personagem);

		Personagem personagemEncontrado = service.buscarPorNome("Angor Rot");

		service.deletarPorId(personagemEncontrado.getId());

		service.buscarPorId(personagemEncontrado.getId());
	}
	
	@Test
	public void deletarTodosPersonagens() {
		Personagem personagem1 = new Personagem("Angor Rot", 1, 10, Raca.MEIO_ORC, Classe.BRUXO, "Imagem");
		Personagem personagem2 = new Personagem("Jorge Glaerd", 1, 10, Raca.MEIO_ELFO, Classe.BARDO, "Imagem");

		service.salvar(personagem1);
		service.salvar(personagem2);

		service.deletarTudo();
		
		List<Personagem> personagens = service.listarTodos();

		assertEquals(0, personagens.size());
	}
	
	@Test(expected = ServiceException.class)
	public void buscarPersonagemNaoExistentePorId() {
		service.buscarPorId(1);
	}
	
	@Test(expected = ServiceException.class)
	public void buscarPersonagemNaoExistentePorNome() {
		service.buscarPorNome("Angor Rot");
	}
}
