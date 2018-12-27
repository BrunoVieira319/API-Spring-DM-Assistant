package br.com.assist.dto;

import java.util.List;

import br.com.assist.domain.EspacoDeMagia;
import br.com.assist.domain.HabilidadePersonagem;

public class PersonagemDetalhesDto {

	private Integer id;
	
	private String nome;
	
	private int dadosDeVida;
	
	private List<HabilidadePersonagem> habilidades;
	
	private List<EspacoDeMagia> espacosDeMagia;

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

	public int getDadosDeVida() {
		return dadosDeVida;
	}

	public void setDadosDeVida(int dadosDeVida) {
		this.dadosDeVida = dadosDeVida;
	}

	public List<HabilidadePersonagem> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<HabilidadePersonagem> habilidades) {
		this.habilidades = habilidades;
	}

	public List<EspacoDeMagia> getEspacosDeMagia() {
		return espacosDeMagia;
	}

	public void setEspacosDeMagia(List<EspacoDeMagia> espacosDeMagia) {
		this.espacosDeMagia = espacosDeMagia;
	}
	
	
}
