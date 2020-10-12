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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "quadra")
@NamedQueries({ @NamedQuery(name = "Quadra.findAll", query = "SELECT q FROM Quadra q"),
		@NamedQuery(name = "Quadra.findById", query = "SELECT q FROM Quadra q WHERE q.id = :id") })
public class Quadra implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private int id;

	@JoinColumn(name = "etapa", referencedColumnName = "id")
	@ManyToOne(optional = false)
	private Etapa etapa;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quadra")
	private List<Partida> partidaList;

	public Quadra() {

	}

	public Quadra(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public List<Partida> getPartidaList() {
		return partidaList;
	}

	public void setPartidaList(List<Partida> partidaList) {
		this.partidaList = partidaList;
	}

}
