package com.unipampa.padel.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "etapa")
//@NamedQueries({
//    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e")
//    , @NamedQuery(name = "Etapa.findById", query = "SELECT e FROM Etapa e WHERE e.id = :id")
//    , @NamedQuery(name = "Etapa.findByDatafinal", query = "SELECT e FROM Etapa e WHERE e.datafinal = :datafinal")
//    , @NamedQuery(name = "Etapa.findByDatainicial", query = "SELECT e FROM Etapa e WHERE e.datainicial = :datainicial")
//    , @NamedQuery(name = "Etapa.findByNome", query = "SELECT e FROM Etapa e WHERE e.nome = :nome")})
public class Etapa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int id;

	@Basic(optional = false)
	@Column(name = "nome", nullable = false, length = 20)
	private String nome;

	@Basic(optional = false)
	@Column(name = "datainicial", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Basic(optional = false)
	@Column(name = "datafinal", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etapa1")
	private List<Inscricao> inscList;

	@JoinColumn(name = "circuito", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Circuito circuito;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etapa")
	private List<Quadra> quadras;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "etapa")
	private List<Partida> partidas;

	@ManyToMany(mappedBy = "etapaList")
	private List<Chave> chaves;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Inscricao> getInscList() {
		return inscList;
	}

	public void setInscList(List<Inscricao> inscList) {
		this.inscList = inscList;
	}

	public Circuito getCircuito() {
		return circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public List<Quadra> getQuadras() {
		return quadras;
	}

	public void setQuadras(List<Quadra> quadras) {
		this.quadras = quadras;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	public List<Chave> getChaves() {
		return chaves;
	}

	public void setChaves(List<Chave> chaves) {
		this.chaves = chaves;
	}

}
