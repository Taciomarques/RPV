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

@ManagedBean(name = "chaveMB")
@SessionScoped
public class ChaveMB implements Serializable {

	private List<Chave> chaves = null;
	private List<Chave> todasChaves = null;

	private Categoria categoria = new Categoria();

	private Categoria selectCategoria = new Categoria();	

	public ArrayList<Chave> retornaChaves() {

		try {

			String url = "http://localhost:8080/api/chaves";

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

			ArrayList<Chave> listaChave = new ArrayList<Chave>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);

//				System.out.println(e);
				Chave chave = new Chave();
				chave.setId(Integer.parseInt(e.get("id").toString()));
				chave.setNome(e.get("nome").toString());
				ArrayList<Dupla> duplas = new ArrayList<>();

				JsonArray c = (JsonArray) e.get("duplaList");
//				System.out.println(c.size());
				for (int j = 0; j < c.size(); j++) {

					Dupla d = new Dupla();
					d.setId(Integer.parseInt(c.get(j).toString()));
					duplas.add(d);
				}
				chave.setDuplaList(duplas);

				listaChave.add(chave);

			}

//			System.out.println(listaChave.size());
			int index = 0;
			DuplaMB duplaMB = new DuplaMB();
			for (Chave chave : listaChave) {
				for (Dupla d2 : chave.getDuplaList()) {
					for (Dupla d : duplaMB.retornaDuplas()) {
//						System.out.println(chave.getDuplaList().get(0).getId() + " " + d.getId());
//						if (chave.getDuplaList().get(0).getId() == d.getId()) {
						if (d2.getId() == d.getId()) {
							chave.getDuplaList().get(index).setNome(d.getNome());
							Categoria cat = new Categoria();
							cat.setId(d.getCategoria().getId());
							chave.getDuplaList().get(index).setCategoria(cat);
						}

					}
					index++;

				}
//				System.out.println(chave.getDuplaList().get(0).getNome() + " " + chave.getDuplaList().get(1).getNome()
//						+ " " + chave.getDuplaList().get(2).getNome()+" "+chave.getDuplaList().get(0).getCategoria().getNome());
				index = 0;
			}

//			for (Chave c : listaChave) {
//				System.out.println(c.getId());
//				System.out.println(c.getNome());
//				System.out.println(c.getDuplaList().get(1).getNome());
//			}

			return listaChave;

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
