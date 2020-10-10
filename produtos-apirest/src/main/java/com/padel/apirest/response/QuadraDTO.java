package com.padel.apirest.response;

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

public class QuadraDTO implements Serializable{
	
	private int id;
	
	private int etapa;
	
	private List<Integer> partidaList;
	
	public QuadraDTO () {
		
	}
	
	
	public QuadraDTO(int id) {
		this.id = id;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getEtapa() {
		return etapa;
	}


	public void setEtapa(int etapa) {
		this.etapa = etapa;
	}


	public List<Integer> getPartidaList() {
		return partidaList;
	}


	public void setPartidaList(List<Integer> partidaList) {
		this.partidaList = partidaList;
	}
	
	
}
