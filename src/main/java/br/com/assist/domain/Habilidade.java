package br.com.assist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	@JoinColumn(name = "habilidade_id")
	private List<HabilidadePersonagem> habilidadePersonagem;

	@SuppressWarnings("unused")
	private Habilidade() {
	}

	public Habilidade(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
		this.habilidadePersonagem = new ArrayList<>();
		validarDominio();
	}
	
	public void adicionarUsos(HabilidadePersonagem habilidadePersonagem) {
		this.habilidadePersonagem.add(habilidadePersonagem);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public List<HabilidadePersonagem> getUsos() {
		return Collections.unmodifiableList(habilidadePersonagem);
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
			return true;
		}
		Habilidade alvoComparacao = (Habilidade) obj;
		return new EqualsBuilder().append(id, alvoComparacao.getId()).build();
	}

}
