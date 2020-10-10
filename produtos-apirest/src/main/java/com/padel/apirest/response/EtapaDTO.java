package com.padel.apirest.response;

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

public class EtapaDTO implements Serializable{

	private int id;
	
	private String nome;
	
	private Date dataInicial;
	
	private Date dataFinal;
	
	private List<Integer> inscList;
	
	private int circuito;
	
	private List<Integer> quadras;
	

	private List<Integer> partidas;
	
	private List<Integer> chaves;

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

	public List<Integer> getInscList() {
		return inscList;
	}

	public void setInscList(List<Integer> inscList) {
		this.inscList = inscList;
	}

	public int getCircuito() {
		return circuito;
	}

	public void setCircuito(int circuito) {
		this.circuito = circuito;
	}

	public List<Integer> getQuadras() {
		return quadras;
	}

	public void setQuadras(List<Integer> quadras) {
		this.quadras = quadras;
	}

	public List<Integer> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Integer> partidas) {
		this.partidas = partidas;
	}

	public List<Integer> getChaves() {
		return chaves;
	}

	public void setChaves(List<Integer> chaves) {
		this.chaves = chaves;
	}
	
	

	
}
