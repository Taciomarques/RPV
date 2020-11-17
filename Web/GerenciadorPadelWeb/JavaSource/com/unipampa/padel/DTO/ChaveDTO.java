package com.unipampa.padel.DTO;

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


public class ChaveDTO implements Serializable, Comparable  {


	private int id;
	
	
	private String nome;
	
	
	private List<Integer>duplaList;
	
	
	private List<Integer> etapaList;
	
	
	
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
	public List<Integer> getDuplaList() {
		if (this.duplaList == null) {
			this.duplaList = new ArrayList<Integer>();
		}
		return this.duplaList;
	}
	public void setDuplaList(List<Integer> duplaList) {
		this.duplaList = duplaList;
	}
	public List<Integer> getEtapaList() {
		return etapaList;
	}
	public void setEtapaList(List<Integer> etapaList) {
		this.etapaList = etapaList;
	}
	@Override
	public int compareTo(Object o) {
	
		ChaveDTO c = (ChaveDTO) o;
		if(this.getNome().compareTo(c.getNome()) < 0 ) {
			return -1;
		}else if(this.getNome().compareTo(c.getNome()) > 0){
			 return 1;
		 }
		return 0;
	}
	
}
