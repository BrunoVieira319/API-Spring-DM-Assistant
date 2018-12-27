package br.com.assist.domain;


public enum Raca {

	ANAO("Anão"),
	DRACONATO("Draconato"),
	ELFO("Elfo"),
	GNOMO("Gnomo"),
	HALFLING("Halfling"),
	HUMANO("Humano"),
	MEIO_ELFO("Meio-Elfo"),
	MEIO_ORC("Meio-Orc"),
	TIEFLING("Tiefling");
	
	private String nome;
	
	private Raca(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
