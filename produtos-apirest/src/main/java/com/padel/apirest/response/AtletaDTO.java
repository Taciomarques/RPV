package com.padel.apirest.response;

import java.io.Serializable;
import java.util.List;

public class AtletaDTO implements Serializable{
	
	
	private int id;
	
	
	private String nome;
	
	
	private String cpf;
	
	
	private String numCel;
	
	
	private String email;
	
	private List<Integer> duplas;
	
	
	private List<Integer> rankList;
	
	public List<Integer> getRankList() {
		return rankList;
	}
	public void setRankList(List<Integer> rankList) {
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
	public List<Integer> getDuplas() {
		return duplas;
	}
	public void setDuplas(List<Integer> duplas) {
		this.duplas = duplas;
	}
	
}
