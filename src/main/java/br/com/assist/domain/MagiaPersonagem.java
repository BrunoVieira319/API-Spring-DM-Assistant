package br.com.assist.domain;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class MagiaPersonagem extends BaseDominio {

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Magia magia;

	@NotNull
	private boolean preparada;

	public MagiaPersonagem(Magia magia) {
		this.magia = magia;
		this.preparada = false;
		validarDominio();
	}
}
