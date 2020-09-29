package com.unipampa.padel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dupla")
//@NamedQueries({
//    @NamedQuery(name = "Dupla.findAll", query = "SELECT d FROM Dupla d")
//    , @NamedQuery(name = "Dupla.findById", query = "SELECT d FROM Dupla d WHERE d.id = :id")
//    , @NamedQuery(name = "Dupla.findByImpedimento", query = "SELECT d FROM Dupla d WHERE d.impedimento = :impedimento")
//    , @NamedQuery(name = "Dupla.findByNome", query = "SELECT d FROM Dupla d WHERE d.nome = :nome")
//    , @NamedQuery(name = "Dupla.findByPontosrank", query = "SELECT d FROM Dupla d WHERE d.pontosrank = :pontosrank")
//    , @NamedQuery(name = "Dupla.findBySuplente", query = "SELECT d FROM Dupla d WHERE d.suplente = :suplente")})
public class Dupla implements Comparable, Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private int id;
	
	@Basic(optional = false)
    @Column(name = "nome")
	private String nome;
	
	@Basic(optional = false)
	@Column(name = "impedimento")
	private String impedimento;
	
	@Basic(optional = false)
	@Column(name = "pontosrank")
	private int pontosRank;//*
	
	@JoinColumn(name = "categoria", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Categoria categoria;
	
	@ManyToMany(mappedBy = "duplaList")
	private List<Chave> chaves;//*
	
	@Basic(optional = false)
    @Column(name = "suplente")
	private boolean suplente = true;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dupla1")
	private List<Inscricao> inscList;
	
	@JoinTable(name = "dupla_partida", joinColumns = {
	        @JoinColumn(name = "dupla", referencedColumnName = "id")}, inverseJoinColumns = {
	        @JoinColumn(name = "partida", referencedColumnName = "id")})
	@ManyToMany
	private List<Partida> partidaList;
	
	//,cascade = {CascadeType.MERGE}
	@ManyToMany(mappedBy = "duplas")
	private List<Atleta> atletaList;
	
	
	
public List<Partida> getPartidaList() {
		return partidaList;
	}

	public void setPartidaList(List<Partida> partidaList) {
		this.partidaList = partidaList;
	}

	public List<Atleta> getAtletaList() {
		return atletaList;
	}

	public void setAtletaList(List<Atleta> atletaList) {
		this.atletaList = atletaList;
	}

public String getImpedimento() {
		return impedimento;
	}

	public void setImpedimento(String impedimento) {
		this.impedimento = impedimento;
	}

	

	public boolean isSuplente() {
		return suplente;
	}

	public void setSuplente(boolean suplente) {
		this.suplente = suplente;
	}
	
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

	public int getPontosRank() {
		return pontosRank;
	}

	public void setPontosRank(int pontosHank) {
		this.pontosRank = pontosHank;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	

	public List<Inscricao> getInscList() {
		return inscList;
	}

	public void setInscList(List<Inscricao> inscList) {
		this.inscList = inscList;
	}

	public List<Chave> getChaves() {
		return chaves;
	}

	public void setChaves(List<Chave> chaves) {
		this.chaves = chaves;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}

	@OneToMany
	private List<Partida> partidas;

	@Override
	public int compareTo(Object arg0) {
		int pontosParaComparar=((Dupla)arg0).getPontosRank();
        /* For Ascending order*/
        return pontosParaComparar - this.getPontosRank();
	}
	
	public ArrayList<Dupla> ordenaDuplas(){
		
		return null;
	}
	
	private void atualizaSituacao() {
		
		suplente = false;
		
	}
	
	
//	@Transient
//	@ManyToMany(mappedBy = "duplas")
//	private List<Torneio> torneio;

}
