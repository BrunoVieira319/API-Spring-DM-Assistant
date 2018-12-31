package br.com.assist.domain;

public enum EscolaDeMagia {

	ABJURACAO("Abjuração"),
	ADIVINHACAO("Adivinhação"),
	CONJURACAO("Conjuração"),
	ENCANTAMENTO("Encantamento"),
	EVOCACAO("Evocação"),
	ILUSAO("Ilusão"),
	NECROMANCIA("Necromancia"),
	TRANSMUTACAO("Transmutação");
	
	private String nome;
	
	EscolaDeMagia(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
