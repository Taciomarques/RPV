package com.unipampa.padel.model;

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
//@NamedQueries({
//    @NamedQuery(name = "Atleta.findAll", query = "SELECT a FROM Atleta a")
//    , @NamedQuery(name = "Atleta.findById", query = "SELECT a FROM Atleta a WHERE a.id = :id")
//    , @NamedQuery(name = "Atleta.findByCpf", query = "SELECT a FROM Atleta a WHERE a.cpf = :cpf")
//    , @NamedQuery(name = "Atleta.findByEmail", query = "SELECT a FROM Atleta a WHERE a.email = :email")
//    , @NamedQuery(name = "Atleta.findByNome", query = "SELECT a FROM Atleta a WHERE a.nome = :nome")
//    , @NamedQuery(name = "Atleta.findByNumcel", query = "SELECT a FROM Atleta a WHERE a.numcel = :numcel")})
public class Atleta implements Serializable{//*
	
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
	
	public Atleta() {
		
	}
	
	public Atleta(String nome, String cpf, List<Ranking> rankList) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.rankList = rankList;
	}
	
	public Atleta(String nome, String cpf, String numCel, String email, List<Dupla> duplas,
			List<Ranking> rankList) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.numCel = numCel;
		this.email = email;
		this.duplas = duplas;
		this.rankList = rankList;
	}
	
}
