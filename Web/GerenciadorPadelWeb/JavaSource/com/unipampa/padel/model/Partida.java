package com.unipampa.padel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "partida")
//@NamedQueries({
//    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p")
//    , @NamedQuery(name = "Partida.findById", query = "SELECT p FROM Partida p WHERE p.id = :id")
//    , @NamedQuery(name = "Partida.findByHorainicio", query = "SELECT p FROM Partida p WHERE p.horainicio = :horainicio")
//    , @NamedQuery(name = "Partida.findByPontosdupla1", query = "SELECT p FROM Partida p WHERE p.pontosdupla1 = :pontosdupla1")
//    , @NamedQuery(name = "Partida.findByPontosdupla2", query = "SELECT p FROM Partida p WHERE p.pontosdupla2 = :pontosdupla2")
//    , @NamedQuery(name = "Partida.findByNome", query = "SELECT p FROM Partida p WHERE p.nome = :nome")})
public class Partida implements Serializable{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private int id;
	
	@Basic(optional = false)
    @Column(name = "dataHora")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@Basic(optional = false)
    @Column(name = "pontosdupla1")
	private int pontosDupla1;
	
	@Basic(optional = false)
    @Column(name = "pontosdupla2")
	private int pontosDupla2;
	
	@Basic(optional = false)
    @Column(name = "nome")
	private String nome;
	
	@ManyToMany(mappedBy = "partidaList",fetch = FetchType.EAGER)
	private List<Dupla> duplaList;
	
	@JoinColumn(name = "etapa", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Etapa etapa;
	
	@JoinColumn(name = "quadra", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Quadra quadra;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public int getPontosDupla1() {
		return pontosDupla1;
	}
	public void setPontosDupla1(int pontosDupla1) {
		this.pontosDupla1 = pontosDupla1;
	}
	public int getPontosDupla2() {
		return pontosDupla2;
	}
	public void setPontosDupla2(int pontosDupla2) {
		this.pontosDupla2 = pontosDupla2;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Dupla> getDuplaList() {
		if (this.duplaList == null) {
			this.duplaList = new ArrayList<Dupla>();
		}
		return this.duplaList;
	}
	public void setDuplaList(List<Dupla> duplaList) {
		this.duplaList = duplaList;
	}
	public Etapa getEtapa() {
		return etapa;
	}
	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}
	public Quadra getQuadra() {
		return quadra;
	}
	public void setQuadra(Quadra quadra) {
		this.quadra = quadra;
	}
	

	
}
