package br.com.assist.domain;


public enum Classe {

	BARBARO("Bárbaro"),
	BARDO("Bardo"),
	BRUXO("Bruxo"),
	CLERIGO("Clérigo"),
	DRUIDA("Druida"),
	FEITICEIRO("Feiticeiro"),
	GUERREIRO("Guerreiro"),
	LADINO("Ladino"),
	MAGO("Mago"),
	MONGE("Monge"),
	PALADINO("Paladino"),
	PATRULHEIRO("Patrulheiro");
	
	private String nome;
	
	Classe(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
