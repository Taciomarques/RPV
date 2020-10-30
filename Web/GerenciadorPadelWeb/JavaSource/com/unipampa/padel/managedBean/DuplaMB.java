package com.unipampa.padel.managedBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.parsers.DuplaParser;
import com.unipampa.padel.util.JsfUtil;
import com.unipampa.padel.util.RestUtil;

@ManagedBean(name = "duplaMB")
@RequestScoped
public class DuplaMB implements Serializable {

	private List<Dupla> duplas = null;
	
	private Dupla dupla = new Dupla();

	private Atleta atleta1 = new Atleta();

	private Atleta atleta2 = new Atleta();

	private Categoria categoria = new Categoria();

	private Categoria selectCategoria = new Categoria();

	private void populaDupla() {
		ArrayList<Atleta> atletas = new ArrayList<Atleta>();
		atletas.add(atleta1);
		atletas.add(atleta2);
		dupla.setAtletaList(atletas);
	}

	public ArrayList<Dupla> retornaDuplas() {

		try {

			return DuplaParser.createDupla();

		} catch (Exception e) {

			return null;

		}
	}

	public String inscrever() {

		populaDupla();
		dupla.setCategoria(categoria);
		dupla.setNome(atleta1.getNome() + "/" + atleta2.getNome());

//		if(pA.cadastroDupla(dupla) != null) {
		JsfUtil.addSuccessMessage("Dupla " + dupla.getNome() + " cadastrada com sucesso!");
// 		}

		return "";
	}
	
	public void methodAjax() {

		try {
		ArrayList<Dupla> duplaList = retornaDuplas();
		duplas = new ArrayList<Dupla>();
		if (duplaList != null) {

			for (Dupla ds : duplaList) {
				if (ds.getCategoria().getId() == selectCategoria.getId()) {
					duplas.add(ds);
				}
			}
		}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	public Categoria getSelectCategoria() {
		return selectCategoria;
	}

	public void setSelectCategoria(Categoria selectCategoria) {
		this.selectCategoria = selectCategoria;
	}

	public List<Dupla> getDuplas() {
		return duplas;
	}

	public void setDuplas(List<Dupla> duplas) {
		this.duplas = duplas;
	}

	public Dupla getDupla() {
		return dupla;
	}

	public void setDupla(Dupla dupla) {
		this.dupla = dupla;
	}

	public Atleta getAtleta1() {
		return atleta1;
	}

	public void setAtleta1(Atleta atleta1) {
		this.atleta1 = atleta1;
	}

	public Atleta getAtleta2() {
		return atleta2;
	}

	public void setAtleta2(Atleta atleta2) {
		this.atleta2 = atleta2;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
