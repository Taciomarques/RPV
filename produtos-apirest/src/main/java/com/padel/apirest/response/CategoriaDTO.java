package com.padel.apirest.response;

import java.io.Serializable;
import java.util.List;

public class CategoriaDTO implements Serializable {
	
	
	private int id;
	
	
	private String nome;
	
	
	private List<Integer> rankList;
	
	
	private List<Integer> duplaList;

	public List<Integer> getRankList() {
		return rankList;
	}
	public void setRankList(List<Integer> rankList) {
		this.rankList = rankList;
	}
	public List<Integer> getDuplaList() {
		return duplaList;
	}
	public void setDuplaList(List<Integer> duplaList) {
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
