package br.com.assist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.assist.domain.Habilidade;

@Repository
public interface HabilidadeRepository extends JpaRepository<Habilidade, Integer> {

	public Habilidade findByNome(String nome);
}
