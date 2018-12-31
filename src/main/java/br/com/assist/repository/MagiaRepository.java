package br.com.assist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.assist.domain.Magia;

@Repository
public interface MagiaRepository extends JpaRepository<Magia, Integer> {

	Optional<Magia> findByNome(String nome);
}
