package br.com.assist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.assist.domain.Personagem;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {

	Personagem findByNome(String nome);
}
