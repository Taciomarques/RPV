package com.padel.apirest.models;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;




@Entity
@Table(name = "atleta")
public class Atleta implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "id")
	private int id;
	
	@Basic(optional = false)
    @Column(name = "nome")
	private String nome;
	
	@Basic(optional = false)
    @Column(name = "cpf")
	private String cpf;
	
	@Basic(optional = false)
	@Column(name = "numcel")
	private String numCel;
	
	@Basic(optional = false)
    @Column(name = "email")
	private String email;
	
	@JoinTable(name = "atleta_dupla", joinColumns = {
	    @JoinColumn(name = "atleta", referencedColumnName = "id")}, inverseJoinColumns = {
	    @JoinColumn(name = "dupla", referencedColumnName = "id")})
	@ManyToMany
	private List<Dupla> duplas;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "atleta1")
	private List<Ranking> rankList;
	
	public List<Ranking> getRankList() {
		return rankList;
	}
	public void setRankList(List<Ranking> rankList) {
		this.rankList = rankList;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNumCel() {
		return numCel;
	}
	public void setNumCel(String numCel) {
		this.numCel = numCel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Dupla> getDuplas() {
		return duplas;
	}
	public void setDuplas(List<Dupla> duplas) {
		this.duplas = duplas;
	}
	
}
