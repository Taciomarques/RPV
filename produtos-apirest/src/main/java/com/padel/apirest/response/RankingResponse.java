package com.padel.apirest.response;

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

public class RankingResponse implements Serializable {
	
//	protected RankingPK rankingPK;
	
	private int pontosrank;
	
	private int atleta1;
	
	private int categoria;
	
	private int circuito1;

	public int getPontosrank() {
		return pontosrank;
	}

	public void setPontosrank(int pontosrank) {
		this.pontosrank = pontosrank;
	}
	

//	public RankingPK getRankingPK() {
//		return rankingPK;
//	}
//
//	public void setRankingPK(RankingPK rankingPK) {
//		this.rankingPK = rankingPK;
//	}

	public int getAtleta1() {
		return atleta1;
	}

	public void setAtleta1(int atleta1) {
		this.atleta1 = atleta1;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public int getCircuito1() {
		return circuito1;
	}

	public void setCircuito1(int circuito) {
		this.circuito1 = circuito;
	}
	
	
}
