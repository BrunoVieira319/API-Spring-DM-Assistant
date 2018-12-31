package br.com.assist.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
public class Personagem extends BaseDominio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotNull
	@NotEmpty
	private String nome;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Raca raca;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Classe classe;

	@NotNull
	@Min(value = 1)
	@Max(value = 20)
	private int nivel;

	@NotNull
	@Positive
	private int vidaMax;

	@NotNull
	@PositiveOrZero
	private int vidaAtual;

	@NotNull
	@PositiveOrZero
	private int dadosDeVida;

	@NotNull
	private String img;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id")
	private List<HabilidadePersonagem> habilidades;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "personagem_id")
	private List<EspacoDeMagia> espacosDeMagia;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "personagem_id")
	private List<MagiaPersonagem> magias;

	public Personagem(String nome, int nivel, int vidaMax, Raca raca, Classe classe, String img) {
		this.nome = nome;
		this.nivel = nivel;
		this.vidaMax = vidaMax;
		this.vidaAtual = vidaMax;
		this.dadosDeVida = nivel;
		this.raca = raca;
		this.classe = classe;
		this.img = img;
		this.habilidades = new ArrayList<>();
		this.espacosDeMagia = new ArrayList<>();
		this.magias = new ArrayList<>();
		validarDominio();
	}

	@SuppressWarnings("unused")
	private Personagem() {
	}

	public void subirDeNivel() {
		if (nivel < 20) {
			nivel += 1;
		}
	}

	public void setVidaAtual(int vidaAtualizada) {
		if (vidaAtualizada < 0 || vidaAtualizada > vidaMax) {
			throw new DominioInvalidoException("Valor de vida não válido");
		}
		vidaAtual = vidaAtualizada;
	}

	public void descansar() {
		vidaAtual = vidaMax;
		dadosDeVida = nivel;
		restaurarTodosEspacosDeMagia();
		restaurarTodosUsosDeHabilidade();
	}

	public void usarDadoDeVida() {
		if (dadosDeVida > 0) {
			dadosDeVida -= 1;
		}
	}

	public void adicionarHabilidade(Habilidade habilidade, int qtdUsosMaximos, Recuperacao recuperacao) {
		habilidades.add(new HabilidadePersonagem(habilidade, qtdUsosMaximos, recuperacao));
	}

	public void usarHabilidade(Integer idHabilidade) {
		getHabilidadePersonagem(idHabilidade).usarHabilidade();
	}

	public void restaurarUsosHabilidade(Integer idHabilidade) {
		getHabilidadePersonagem(idHabilidade).restaurarUsos();
	}

	public void removerHabilidade(Integer id) {
		habilidades.remove(getHabilidadePersonagem(id));
	}

	public void adicionarEspacoDeMagia(int nivel, int qtdEspacos) {
		EspacoDeMagia espacoDeMagia = new EspacoDeMagia(nivel, qtdEspacos);

		if (classe.equals(Classe.BRUXO) && espacosDeMagia.size() > 0) {
			espacosDeMagia.remove(0);
		} else if (espacosDeMagia.stream().anyMatch(e -> e.getNivel() == nivel)) {
			Optional<EspacoDeMagia> espaco = espacosDeMagia.stream().filter(e -> e.getNivel() == nivel).findFirst();
			espacosDeMagia.remove(espaco.get());
		}

		espacosDeMagia.add(espacoDeMagia);
	}

	public void conjurarMagia(int nivel) {
		getEspacoDeMagia(nivel).usarEspaco();
	}

	public void restaurarEspacoDeMagia(int nivel) {
		getEspacoDeMagia(nivel).restauraUmEspaco();
	}

	public void adicionarMagia(Magia magia) {
		if (magias.stream().anyMatch(m -> m.getMagia().equals(magia))) {
			throw new DominioInvalidoException("Magia já adicionada");
		}
		magias.add(new MagiaPersonagem(magia));
	}

	public void prepararMagia(Integer idMagia) {
		if (classesQuePreparamMagia().contains(classe)) {
			getMagiaPersonagem(idMagia).prepararMagia();
		} else {
			throw new DominioInvalidoException("Esta classe não prepara magias");
		}
	}

	public void desprepararMagia(Integer idMagia) {
		if (classesQuePreparamMagia().contains(classe)) {
			getMagiaPersonagem(idMagia).desprepararMagia();
		} else {
			throw new DominioInvalidoException("Esta classe não prepara magias");
		}
	}
	
	private List<Classe> classesQuePreparamMagia() {
		List<Classe> classesQuePreparamMagia = new ArrayList<>();
		classesQuePreparamMagia.add(Classe.CLERIGO);
		classesQuePreparamMagia.add(Classe.DRUIDA);
		classesQuePreparamMagia.add(Classe.MAGO);
		classesQuePreparamMagia.add(Classe.PALADINO);
		
		return classesQuePreparamMagia;
	}
	
	private void restaurarTodosEspacosDeMagia() {
		espacosDeMagia.stream().forEach(e -> e.restaurarTodosEspacos());
	}

	private void restaurarTodosUsosDeHabilidade() {
		habilidades.stream().forEach(h -> h.restaurarUsos());
	}

	private MagiaPersonagem getMagiaPersonagem(Integer idMagia) {
		Optional<MagiaPersonagem> magiaPersonagem = magias.stream().filter(m -> m.getMagia().getId() == idMagia)
				.findFirst();
		if (magiaPersonagem.isPresent()) {
			return magiaPersonagem.get();
		}
		throw new DominioInvalidoException("Magia não encontrada");
	}

	private HabilidadePersonagem getHabilidadePersonagem(Integer idHabilidade) {
		Optional<HabilidadePersonagem> habilidadePersonagem = habilidades.stream()
				.filter(h -> h.getHabilidade().getId() == idHabilidade).findFirst();
		if (habilidadePersonagem.isPresent()) {
			return habilidadePersonagem.get();
		}
		throw new DominioInvalidoException("Habilidade não encontrada");
	}

	public EspacoDeMagia getEspacoDeMagia(int nivel) {
		Optional<EspacoDeMagia> espacoDeMagiaEncontrado = espacosDeMagia.stream()
				.filter(espaco -> espaco.getNivel() == nivel).findFirst();
		if (espacoDeMagiaEncontrado.isPresent()) {
			return espacoDeMagiaEncontrado.get();
		}
		throw new DominioInvalidoException("Espaço de Magia não encontrado");
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public Raca getRaca() {
		return raca;
	}

	public Classe getClasse() {
		return classe;
	}

	public int getNivel() {
		return nivel;
	}

	public int getVidaMax() {
		return vidaMax;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public int getDadosDeVida() {
		return dadosDeVida;
	}

	public String getImg() {
		return img;
	}

	public List<HabilidadePersonagem> getHabilidades() {
		return Collections.unmodifiableList(habilidades);
	}

	public List<EspacoDeMagia> getEspacosDeMagia() {
		return Collections.unmodifiableList(espacosDeMagia);
	}

	public List<MagiaPersonagem> getMagias() {
		return Collections.unmodifiableList(magias);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Personagem alvoComparacao = (Personagem) obj;
		return new EqualsBuilder().append(id, alvoComparacao.getId()).append(nome, alvoComparacao.getNome()).build();
	}

}
