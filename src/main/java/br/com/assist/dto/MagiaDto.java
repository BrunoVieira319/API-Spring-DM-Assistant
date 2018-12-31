package br.com.assist.dto;

import br.com.assist.domain.EscolaDeMagia;

public class MagiaDto {

	private Integer id;

	private String nome;

	private int nivel;

	private EscolaDeMagia escola;

	private boolean ritual;

	private String tempoDeConjuracao;

	private String alcance;

	private String componentes;

	private String duracao;

	private String descricao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public EscolaDeMagia getEscola() {
		return escola;
	}

	public void setEscola(EscolaDeMagia escola) {
		this.escola = escola;
	}

	public boolean isRitual() {
		return ritual;
	}

	public void setRitual(boolean ritual) {
		this.ritual = ritual;
	}

	public String getTempoDeConjuracao() {
		return tempoDeConjuracao;
	}

	public void setTempoDeConjuracao(String tempoDeConjuracao) {
		this.tempoDeConjuracao = tempoDeConjuracao;
	}

	public String getAlcance() {
		return alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}

	public String getComponentes() {
		return componentes;
	}

	public void setComponentes(String componentes) {
		this.componentes = componentes;
	}

	public String getDuracao() {
		return duracao;
	}

	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
