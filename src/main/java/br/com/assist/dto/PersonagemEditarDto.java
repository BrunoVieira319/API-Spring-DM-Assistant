package br.com.assist.dto;

public class PersonagemEditarDto {

	private Integer id;
	
	private int nivel;
	
	private int vidaMax; 
	
	private String img;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public void setVidaMax(int vidaMax) {
		this.vidaMax = vidaMax;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	
	
}
