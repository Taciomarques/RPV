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

			String url = "http://localhost:8080/api/duplas";

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "";
			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}

			conn.disconnect();

//            Gson gson = new Gson();
//            Dupla dupla = gson.fromJson(new String(output.getBytes()), Dupla.class);

//            JSONArray response_json = new JSONArray (output);
//            ObjectMapper mapper = new ObjectMapper();
//            List<Object> listTest = mapper.readValue(String.valueOf(response_json), List.class);

//            for(Object o : listTest) {
//            	Dupla d = (Dupla)o;
//            	System.out.println(d.getNome());
//            }

//			String json = new Gson().toJson(output);

			ArrayList<Dupla> listaDupla = new ArrayList<Dupla>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
//				System.out.println(e.get("nome"));
				Dupla dupla = new Dupla();
				dupla.setId(Integer.parseInt(e.get("id").toString()));
				dupla.setNome(e.get("nome").toString());
				Categoria cat = new Categoria();
//				JsonObject c = (JsonObject) e.get("categoria");
				cat.setId(Integer.parseInt(e.get("categoria").toString()));
//				cat.setId(Integer.parseInt(c.get("id").toString()));
//				cat.setNome(c.get("nome").toString());
				dupla.setCategoria(cat);
				dupla.setImpedimento(e.get("impedimento").toString());
				if(e.get("suplente").toString().equalsIgnoreCase("true")) {
					dupla.setSuplente(true);
				}else {
					dupla.setSuplente(false);
				}
				dupla.setPontosRank(Integer.parseInt(e.get("pontosRank").toString()));
				listaDupla.add(dupla);

			}

//			System.out.println(listaDupla.size());
//			
//			for (Dupla d : listaDupla) {
//				System.out.println("Id: " + d.getId());
//				System.out.println("Nome: " + d.getNome());
//			}

			return listaDupla;

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
