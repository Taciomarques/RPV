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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
@NamedQueries({ @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
		@NamedQuery(name = "Categoria.findById", query = "SELECT c FROM Categoria c WHERE c.id = :id"),
		@NamedQuery(name = "Categoria.findByNome", query = "SELECT c FROM Categoria c WHERE c.nome = :nome") })
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int id;

	@Basic(optional = false)
	@Column(name = "nome", nullable = false, length = 10)
	private String nome;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
	private List<Ranking> rankList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
	private List<Dupla> duplaList;

	public List<Ranking> getRankList() {
		return rankList;
	}

	public void setRankList(List<Ranking> rankList) {
		this.rankList = rankList;
	}

	public List<Dupla> getDuplaList() {
		return duplaList;
	}

	public void setDuplaList(List<Dupla> duplaList) {
		this.duplaList = duplaList;
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

}
