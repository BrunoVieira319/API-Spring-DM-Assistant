package br.com.assist.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Personagem extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "personagem_id")
	private Integer id;

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	@Size(min = 1, max = 20)
	private int nivel;

	@NotNull
	@Size(min = 1)
	private int vidaMax;

	@NotNull
	@Size(min = 1)
	private int vidaAtual;

	@NotNull
	@NotEmpty
	private String raca;

	@NotNull
	@NotEmpty
	private String classe;

	@NotNull
	@Size(min = 1)
	private int qtdDadosVida;

	@NotNull
	private String img;
	
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "personagem_id")
	private List<HabilidadePersonagem> habilidades;

}
