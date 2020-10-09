package com.unipampa.padel.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ranking")
@NamedQueries({
    @NamedQuery(name = "Ranking.findAll", query = "SELECT r FROM Ranking r")
    , @NamedQuery(name = "Ranking.findByAtleta", query = "SELECT r FROM Ranking r WHERE r.rankingPK.atleta = :atleta")
    , @NamedQuery(name = "Ranking.findByCircuito", query = "SELECT r FROM Ranking r WHERE r.rankingPK.circuito = :circuito")
    , @NamedQuery(name = "Ranking.findByPontosrank", query = "SELECT r FROM Ranking r WHERE r.pontosrank = :pontosrank")})
public class Ranking implements Serializable {
	
	@EmbeddedId
	protected RankingPK rankingPK;
	
	@Basic(optional = false)
    @Column(name = "pontosrank", nullable = false, length = 4)
	private int pontosrank;
	
	@JoinColumn(name = "atleta", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Atleta atleta1;
	
	@JoinColumn(name = "categoria", referencedColumnName = "id")
    @ManyToOne(optional = false)
	private Categoria categoria;
	
	@JoinColumn(name = "circuito", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
	private Circuito circuito1;

	public int getPontosrank() {
		return pontosrank;
	}

	public void setPontosrank(int pontosrank) {
		this.pontosrank = pontosrank;
	}
	

	public RankingPK getRankingPK() {
		return rankingPK;
	}

	public void setRankingPK(RankingPK rankingPK) {
		this.rankingPK = rankingPK;
	}

	public Atleta getAtleta1() {
		return atleta1;
	}

	public void setAtleta1(Atleta atleta1) {
		this.atleta1 = atleta1;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Circuito getCircuito1() {
		return circuito1;
	}

	public void setCircuito1(Circuito circuito) {
		this.circuito1 = circuito;
	}
	
	
}
