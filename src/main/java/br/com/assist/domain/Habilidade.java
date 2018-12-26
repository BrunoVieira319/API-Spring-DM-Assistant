package br.com.assist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class Habilidade extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "habilidade_id")
	private Integer id;

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	private String descricao;

	@SuppressWarnings("unused")
	private Habilidade() {
	}

	public Habilidade(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
		validarDominio();
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return true;
		}
		Habilidade alvoComparacao = (Habilidade) obj;
		return new EqualsBuilder().append(id, alvoComparacao.getId()).append(nome, alvoComparacao.getNome())
				.append(descricao, alvoComparacao.getDescricao()).build();
	}

}
