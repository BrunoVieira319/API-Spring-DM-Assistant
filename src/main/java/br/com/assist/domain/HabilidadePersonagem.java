package br.com.assist.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class HabilidadePersonagem extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "habilidade_id")
	private Habilidade habilidade;

	@NotNull
	@PositiveOrZero
	private int qtdUsosMaximo;

	@NotNull
	@PositiveOrZero
	private int qtdUsosRestantes;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Recuperacao recuperacao;

	@SuppressWarnings("unused")
	private HabilidadePersonagem() {
	}

	HabilidadePersonagem(Habilidade habilidade, int qtdUsosMaximos, Recuperacao recuperacao) {
		this.habilidade = habilidade;
		this.qtdUsosMaximo = qtdUsosMaximos;
		this.qtdUsosRestantes = qtdUsosMaximos;
		this.recuperacao = recuperacao;
		validarDominio();
	}

	void usarHabilidade() {
		if (qtdUsosRestantes > 0) {
			qtdUsosRestantes -= 1;
		}
	}

	void restaurarUsos() {
		qtdUsosRestantes = qtdUsosMaximo;
	}

	Integer getId() {
		return id;
	}

	public int getQtdUsosMaximo() {
		return qtdUsosMaximo;
	}

	public int getQtdUsosRestantes() {
		return qtdUsosRestantes;
	}

	public Recuperacao getRecuperacao() {
		return recuperacao;
	}

	public Habilidade getHabilidade() {
		return habilidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((habilidade == null) ? 0 : habilidade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return true;
		}
		HabilidadePersonagem alvoComparacao = (HabilidadePersonagem) obj;
		return new EqualsBuilder().append(id, alvoComparacao.getId()).append(habilidade, alvoComparacao.getHabilidade())
				.build();
	}

}
