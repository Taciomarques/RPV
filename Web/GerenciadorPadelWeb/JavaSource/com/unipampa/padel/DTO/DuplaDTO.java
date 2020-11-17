package com.unipampa.padel.DTO;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class DuplaDTO implements Comparable, Serializable {

	private int id;

	private String nome;

	private String impedimento;

	private int pontosRank;

	private int categoria;

	private List<Integer> chaves;

	private boolean suplente = true;

	private List<Integer> inscList;

	private List<Integer> partidaList;

	private List<Integer> atletaList;

	public List<Integer> getPartidaList() {
		return partidaList;
	}

	public void setPartidaList(List<Integer> partidaList) {
		this.partidaList = partidaList;
	}

	public List<Integer> getAtletaList() {
		return atletaList;
	}

	public void setAtletaList(List<Integer> atletaList) {
		this.atletaList = atletaList;
	}

	public String getImpedimento() {
		return impedimento;
	}

	public void setImpedimento(String impedimento) {
		this.impedimento = impedimento;
	}

	public boolean isSuplente() {
		return suplente;
	}

	public void setSuplente(boolean suplente) {
		this.suplente = suplente;
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

	public int getPontosRank() {
		return pontosRank;
	}

	public void setPontosRank(int pontosHank) {
		this.pontosRank = pontosHank;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	public List<Integer> getInscList() {
		return inscList;
	}

	public void setInscList(List<Integer> inscList) {
		this.inscList = inscList;
	}

	public List<Integer> getChaves() {
		return chaves;
	}

	public void setChaves(List<Integer> chaves) {
		this.chaves = chaves;
	}

//	public List<Integer> getPartidas() {
//		return partidas;
//	}
//
//	public void setPartidas(List<Integer> partidas) {
//		this.partidas = partidas;
//	}
//
//	private List<Integer> partidas;

	public int compareTo(Object arg0) {
		int pontosParaComparar = ((DuplaDTO) arg0).getPontosRank();
		/* For Ascending order */
		return pontosParaComparar - this.getPontosRank();
	}

	public ArrayList<DuplaDTO> ordenaDuplas() {

		return null;
	}

	private void atualizaSituacao() {

		suplente = false;

	}

}
