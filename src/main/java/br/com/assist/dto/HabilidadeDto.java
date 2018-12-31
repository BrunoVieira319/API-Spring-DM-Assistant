package br.com.assist.dto;

import br.com.assist.domain.Recuperacao;

public class HabilidadeDto {

	private String nome;
	
	private String descricao;
	
	private int qtdUsosMaximo;
	
	private Recuperacao recuperacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQtdUsosMaximo() {
		return qtdUsosMaximo;
	}

	public void setQtdUsosMaximo(int qtdUsosMaximo) {
		this.qtdUsosMaximo = qtdUsosMaximo;
	}

	public Recuperacao getRecuperacao() {
		return recuperacao;
	}

	public void setRecuperacao(Recuperacao recuperacao) {
		this.recuperacao = recuperacao;
	}
	
	
	
}
