package com.unipampa.padel.DTO;

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

public class RankingDTO implements Serializable {
	
//	protected RankingPK rankingPK;
	
	private Integer pontosrank;
	
	private Integer atleta1;
	
	private Integer categoria;
	
	private Integer circuito1;

	public Integer getPontosrank() {
		return pontosrank;
	}

	public void setPontosrank(Integer pontosrank) {
		this.pontosrank = pontosrank;
	}
	

//	public RankingPK getRankingPK() {
//		return rankingPK;
//	}
//
//	public void setRankingPK(RankingPK rankingPK) {
//		this.rankingPK = rankingPK;
//	}

	public Integer getAtleta1() {
		return atleta1;
	}

	public void setAtleta1(Integer atleta1) {
		this.atleta1 = atleta1;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public Integer getCircuito1() {
		return circuito1;
	}

	public void setCircuito1(Integer circuito) {
		this.circuito1 = circuito;
	}
	
	
}
