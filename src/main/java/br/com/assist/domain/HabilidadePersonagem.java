package br.com.assist.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class HabilidadePersonagem extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "habilidade_personagem_id")
	private Integer id;

	@NotNull
	@Size(min = 0)
	private int qtdUsosMaximo;

	@NotNull
	@Size(min = 0)
	private int qtdUsosRestantes;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Descanso descanso;
	
	@SuppressWarnings("unused")
	private HabilidadePersonagem() {
	}

	public HabilidadePersonagem(int qtdUsosMaximos, Descanso descanso) {
		this.qtdUsosMaximo = qtdUsosMaximos;
		this.qtdUsosRestantes = qtdUsosMaximos;
		this.descanso = descanso;
		validarDominio();
	}

	public int getQtdUsosMaximo() {
		return qtdUsosMaximo;
	}

	public void setQtdUsosMaximo(int qtdUsosMaximo) {
		this.qtdUsosMaximo = qtdUsosMaximo;
	}

	public int getQtdUsosRestantes() {
		return qtdUsosRestantes;
	}
	
	public Descanso getDescanso() {
		return descanso;
	}

	public void setDescanso(Descanso descanso) {
		this.descanso = descanso;
	}

	public void usarHabilidade() {
		if (qtdUsosRestantes > 0) {
			this.qtdUsosRestantes -= 1;
		}
	}

	public void restaurarUsos() {
		this.qtdUsosRestantes = this.qtdUsosMaximo;
	}

}
