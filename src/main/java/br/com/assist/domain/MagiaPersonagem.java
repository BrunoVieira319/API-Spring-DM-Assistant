package br.com.assist.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class MagiaPersonagem extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "magia_id")
	private Magia magia;

	@NotNull
	private boolean preparada;

	MagiaPersonagem(Magia magia) {
		this.magia = magia;
		this.preparada = false;
		validarDominio();
	}
	
	@SuppressWarnings("unused")
	private MagiaPersonagem() {	
	}

	void prepararMagia() {
		preparada = true;
	}
	
	void desprepararMagia() {
		preparada = false;
	}
	
	public Integer getId() {
		return id;
	}

	public Magia getMagia() {
		return magia;
	}

	public boolean isPreparada() {
		return preparada;
	}
}
