package br.com.assist.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EspacoDeMagia {

	@NotNull
	@Size(min = 1, max = 9)
	private int nivel;
	
	@NotNull
	@Size(min = 1, max = 4)
	private int qtdMaxima;
	
	@NotNull
	@Size(min = 1, max = 4)
	private int qtdRestantes;
	
}
