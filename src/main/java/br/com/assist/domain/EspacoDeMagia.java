package br.com.assist.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class EspacoDeMagia extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@Min(value = 1)
	@Max(value = 9)
	private int nivel;

	@NotNull
	@Min(value = 1)
	@Max(value = 4)
	private int quantidadeMaxima;

	@NotNull
	@Min(value = 0)
	@Max(value = 4)
	private int quantidade;

	public EspacoDeMagia(int nivel, int quantidadeMaxima) {
		this.nivel = nivel;
		this.quantidadeMaxima = quantidadeMaxima;
		quantidade = quantidadeMaxima;
		validarDominio();
	}

	@SuppressWarnings("unused")
	private EspacoDeMagia() {
	}

	public void usarEspaco() {
		if (quantidade > 0) {
			quantidade -= 1;
		}
	}

	public void restauraUmEspaco() {
		int quantidadeAtualizada = quantidade += 1;
		quantidade = Math.min(quantidadeAtualizada, quantidadeMaxima);
	}

	public void restaurarTodosEspacos() {
		quantidade = quantidadeMaxima;
	}

	public void setQuantidadeMaxima(int espacosMaximo) {
		this.quantidadeMaxima = espacosMaximo;
		validarDominio();
	}

	public Integer getId() {
		return id;
	}

	public int getNivel() {
		return nivel;
	}

	public int getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public int getQuantidade() {
		return quantidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + nivel;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		EspacoDeMagia alvoComparacao = (EspacoDeMagia) obj;
		return new EqualsBuilder().append(id, alvoComparacao.getId()).append(nivel, alvoComparacao.getNivel()).build();
	}

}
