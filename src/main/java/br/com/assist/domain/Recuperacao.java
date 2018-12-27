package br.com.assist.domain;


public enum Recuperacao {

	DESCANSO_CURTO("Descanso Curto"),
	DESCANSO_LONGO("Descanso Longo"),
	SEM_LIMITE("Sem Limite");
	
	private String descricao;
	
	private Recuperacao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
