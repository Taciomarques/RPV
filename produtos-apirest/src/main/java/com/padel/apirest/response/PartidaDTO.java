package com.padel.apirest.response;

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

public class PartidaDTO implements Serializable{
	
	private int id;
	
	private Date dataHora;

	private int pontosDupla1;
	
	private int pontosDupla2;
	
	private String nome;
	
	private List<Integer> duplaList;
	
	private int etapa;
	
	private int quadra;
	
	
	
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
	public List<Integer> getDuplaList() {
		if (this.duplaList == null) {
			this.duplaList = new ArrayList<Integer>();
		}
		return this.duplaList;
	}
	public void setDuplaList(List<Integer> duplaList) {
		this.duplaList = duplaList;
	}
	public int getEtapa() {
		return etapa;
	}
	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}
	public int getQuadra() {
		return quadra;
	}
	public void setQuadra(int quadra) {
		this.quadra = quadra;
	}
	

	
}
