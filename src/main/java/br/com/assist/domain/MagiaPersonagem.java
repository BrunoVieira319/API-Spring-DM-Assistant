package br.com.assist.domain;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class MagiaPersonagem extends BaseDominio {

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Magia magia;

	@NotNull
	private boolean preaparada;

	public MagiaPersonagem(Magia magia, boolean preparada) {
		this.magia = magia;
		preaparada = preparada;
		validarDominio();
	}
}
