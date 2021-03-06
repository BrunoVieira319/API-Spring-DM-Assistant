package br.com.assist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.assist.domain.Personagem;

@Repository
public interface PersonagemRepository extends JpaRepository<Personagem, Integer> {

	@Query("SELECT p FROM Personagem p LEFT JOIN FETCH p.habilidades WHERE p.id = :id")
	Optional<Personagem> findByIdComHabilidades(@Param("id") Integer id);
	
	@Query("SELECT p FROM Personagem p LEFT JOIN FETCH p.magias WHERE p.id = :id")
	Personagem buscaPersonagemComMagias(@Param("id") Integer id);
}