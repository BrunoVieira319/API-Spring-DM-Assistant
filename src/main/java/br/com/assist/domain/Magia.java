package br.com.assist.domain;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Magia {

	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private boolean preparada;
}
