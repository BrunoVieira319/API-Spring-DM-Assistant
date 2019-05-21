package br.com.assist.service;

import br.com.assist.domain.Classe;
import br.com.assist.domain.Personagem;
import br.com.assist.domain.Raca;
import br.com.assist.dto.PersonagemDetalhesDto;
import br.com.assist.dto.PersonagemDto;
import br.com.assist.repository.PersonagemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonagemServiceTestComMock {

    @Mock
    PersonagemRepository personagemRepository;

    @InjectMocks
    PersonagemService personagemService;

    @Spy
    PersonagemDto personagemDto;

    @Mock
    Personagem personagem;

    @Before
    public void criaUmPersonagem() {
        personagemDto = new PersonagemDto();
        personagemDto.setNome("Angor Rot");
        personagemDto.setNivel(1);
        personagemDto.setVidaMax(10);
        personagemDto.setRaca(Raca.MEIO_ORC);
        personagemDto.setClasse(Classe.BRUXO);
        personagemDto.setImg("Imagem");
    }

    @Test
    public void salvaPersonagem() {
        when(personagemRepository.saveAndFlush(any(Personagem.class))).thenReturn(personagem);
        Personagem personagemSalvo = personagemService.salvar(personagemDto);

        assertEquals(personagemSalvo, personagem);
        verify(personagemRepository, times(1)).saveAndFlush(any(Personagem.class));
        verifyNoMoreInteractions(personagemRepository);
    }

    @Test
    public void buscaPersonagemPorId() {
        when(personagemRepository.findByIdComHabilidades(anyInt())).thenReturn(Optional.of(personagem));
        when(personagemRepository.buscaPersonagemComMagias(anyInt())).thenReturn(personagem);
        PersonagemDetalhesDto personagemRetornado = personagemService.buscarPorId(1);

        assertNotNull(personagemRetornado);
        verify(personagemRepository, times(1)).findByIdComHabilidades(anyInt());
        verify(personagemRepository, times(1)).buscaPersonagemComMagias(anyInt());
    }
}
