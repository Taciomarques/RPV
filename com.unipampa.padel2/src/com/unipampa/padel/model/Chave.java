package com.unipampa.padel.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "chave")
@NamedQueries({
    @NamedQuery(name = "Chave.findAll", query = "SELECT c FROM Chave c")
    , @NamedQuery(name = "Chave.findById", query = "SELECT c FROM Chave c WHERE c.id = :id")
    , @NamedQuery(name = "Chave.findByNome", query = "SELECT c FROM Chave c WHERE c.nome = :nome")})
public class Chave implements Serializable, Comparable  {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
	private int id;
	
	@Basic(optional = false)
	@Column(name = "nome",nullable = false, length = 2)
	private String nome;
	
	@JoinTable(name = "dupla_chave", joinColumns = {
	        @JoinColumn(name = "chave", referencedColumnName = "id")}, inverseJoinColumns = {
	        @JoinColumn(name = "dupla", referencedColumnName = "id")})
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Dupla> duplaList;
	
	@JoinTable(name = "chave_etapa", joinColumns = {
		        @JoinColumn(name = "chave", referencedColumnName = "id")}, inverseJoinColumns = {
		        @JoinColumn(name = "etapa", referencedColumnName = "id")})
	@ManyToMany
	private List<Etapa> etapaList;
	
	
	
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
	public List<Dupla> getDuplaList() {
		if (this.duplaList == null) {
			this.duplaList = new ArrayList<Dupla>();
		}
		return this.duplaList;
	}
	public void setDuplaList(List<Dupla> duplaList) {
		this.duplaList = duplaList;
	}
	public List<Etapa> getEtapaList() {
		return etapaList;
	}
	public void setEtapaList(List<Etapa> etapaList) {
		this.etapaList = etapaList;
	}
	@Override
	public int compareTo(Object o) {
	
		Chave c = (Chave) o;
		if(this.getNome().compareTo(c.getNome()) < 0 ) {
			return -1;
		}else if(this.getNome().compareTo(c.getNome()) > 0){
			 return 1;
		 }
		return 0;
	}
	
}
