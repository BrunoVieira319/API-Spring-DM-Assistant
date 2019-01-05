package br.com.assist.dto;

import java.util.List;

import br.com.assist.domain.EspacoDeMagia;
import br.com.assist.domain.HabilidadePersonagem;
import br.com.assist.domain.MagiaPersonagem;

public class PersonagemDetalhesDto {

	private Integer id;
	
	private String nome;
	
	private int nivel;
	
	private int dadosDeVida;
	
	private String img;
	
	private List<HabilidadePersonagem> habilidades;
	
	private List<EspacoDeMagia> espacosDeMagia;
	
	private List<MagiaPersonagem> magias;

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
	
	public int getDadosDeVida() {
		return dadosDeVida;
	}

	public void setDadosDeVida(int dadosDeVida) {
		this.dadosDeVida = dadosDeVida;
	}
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
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
	
	public List<MagiaPersonagem> getMagias() {
		return magias;
	}
	
	public void setMagias(List<MagiaPersonagem> magias) {
		this.magias = magias;
	}
	
}
