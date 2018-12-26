package br.com.assist.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Magia extends BaseDominio {

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	private int nivel;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EscolaDeMagia escola;

	@NotNull
	private boolean ritual;

	@NotNull
	@NotEmpty
	private String tempoDeConjuracao;

	@NotNull
	@NotEmpty
	private String alcance;

	@NotNull
	@NotEmpty
	private String componentes;

	@NotNull
	@NotEmpty
	private String duracao;

	@NotNull
	@NotEmpty
	private String descricao;

}
