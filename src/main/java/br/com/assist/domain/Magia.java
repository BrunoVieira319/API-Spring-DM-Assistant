package br.com.assist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class Magia extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "magia_id")
	private Integer id;

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	@Min(value = 1)
	@Max(value = 9)
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
	@Lob
	private String descricao;

	public Magia(String nome, int nivel, EscolaDeMagia escola, boolean ritual, String tempoDeConjuracao, String alcance,
			String componentes, String duracao, String descricao) {
		this.nome = nome;
		this.nivel = nivel;
		this.escola = escola;
		this.ritual = ritual;
		this.tempoDeConjuracao = tempoDeConjuracao;
		this.alcance = alcance;
		this.componentes = componentes;
		this.duracao = duracao;
		this.descricao = descricao;
		validarDominio();
	}

	@SuppressWarnings("unused")
	private Magia() {
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getNivel() {
		return nivel;
	}

	public EscolaDeMagia getEscola() {
		return escola;
	}

	public boolean isRitual() {
		return ritual;
	}

	public String getTempoDeConjuracao() {
		return tempoDeConjuracao;
	}

	public String getAlcance() {
		return alcance;
	}

	public String getComponentes() {
		return componentes;
	}

	public String getDuracao() {
		return duracao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Magia magiaComparada = (Magia) obj;
		return new EqualsBuilder().append(id, magiaComparada.getId()).append(nome, magiaComparada.getNome()).build();
	}
}
