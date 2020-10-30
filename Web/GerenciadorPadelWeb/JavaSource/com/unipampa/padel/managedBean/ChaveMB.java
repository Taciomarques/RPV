package com.unipampa.padel.managedBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.parsers.ChaveParser;
import com.unipampa.padel.util.RestUtil;

@ManagedBean(name = "chaveMB")
@SessionScoped
public class ChaveMB implements Serializable {

	private List<Chave> chaves = null;
	private List<Chave> todasChaves = null;

	private Categoria categoria = new Categoria();

	private Categoria selectCategoria = new Categoria();	

	public ArrayList<Chave> retornaChaves() {

		try {

			return ChaveParser.createChaves();

		} catch (Exception e) {

			return null;

		}
	}

	public void methodAjax() {

		try {
			if(todasChaves == null) {
				todasChaves = retornaChaves();
			}
			ArrayList<Chave> chaveList = (ArrayList<Chave>) todasChaves;
			chaves = new ArrayList<Chave>();
			if (chaveList != null) {

				for (Chave cs : chaveList) {
					if (cs.getDuplaList().get(0).getCategoria().getId() == selectCategoria.getId()) {
						System.out.println(cs.getNome());
						chaves.add(cs);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public boolean removeChaves(ArrayList<Chave> chaves) {
		return false;
	
	}
	

	public List<Chave> getChaves() {
		return chaves;
	}

	public void setChaves(List<Chave> chaves) {
		this.chaves = chaves;
	}

	public Categoria getSelectCategoria() {
		return selectCategoria;
	}

	public void setSelectCategoria(Categoria selectCategoria) {
		this.selectCategoria = selectCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public static void main(String args[]) {
		ChaveMB c = new ChaveMB();
		c.retornaChaves();
	}
	
	public List<Chave> getTodasChaves() {
		return todasChaves;
	}

	public void setTodasChaves(List<Chave> todasChaves) {
		this.todasChaves = todasChaves;
	}

}
