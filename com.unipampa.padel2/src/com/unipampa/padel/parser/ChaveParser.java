package com.unipampa.padel.parser;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.remote.RestUtil;

public class ChaveParser {
	
	private static String url = "http://localhost:8080/api/chaves";
	
	public static ArrayList<Chave> createChaves(){
		
		return montaDuplaChave(convertJsonParaChave(RestUtil.retornaDados(url)));
		
	}
	
//	public static void cadastroChaves(ArrayList<Chave> chaves){
//		
//		return RestUtil.persisteDados(url,convertChaveParaJson(chaves));
//		
//	}

	private static ArrayList<Chave> convertJsonParaChave(String output) {

		ArrayList<Chave> listaChave = new ArrayList<Chave>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);

//			System.out.println(e);
			Chave chave = new Chave();
			chave.setId(Integer.parseInt(e.get("id").toString()));
			chave.setNome(e.get("nome").toString());
			ArrayList<Dupla> duplas = new ArrayList<>();

			JsonArray c = (JsonArray) e.get("duplaList");
//			System.out.println(c.size());
			for (int j = 0; j < c.size(); j++) {

				Dupla d = new Dupla();
				d.setId(Integer.parseInt(c.get(j).toString()));
				duplas.add(d);
			}
			chave.setDuplaList(duplas);

			listaChave.add(chave);

		}
		return listaChave;

	}
	
	private static JsonArray convertChaveParaJson(ArrayList<Chave> chaves) {

		for(Chave c: chaves) {
			Gson gson = new Gson();    
		    String json = gson.toJson(c);
		    System.out.println(json);
		}

		return null;
	}

	private static ArrayList<Chave> montaDuplaChave(ArrayList<Chave> listaChave) {
		int index = 0;
		DuplaParser duplaPr = new DuplaParser();
		for (Chave chave : listaChave) {
			for (Dupla d2 : chave.getDuplaList()) {
				for (Dupla d : duplaPr.createDupla()) {
//					System.out.println(chave.getDuplaList().get(0).getId() + " " + d.getId());
//					if (chave.getDuplaList().get(0).getId() == d.getId()) {
					if (d2.getId() == d.getId()) {
						chave.getDuplaList().get(index).setNome(d.getNome());
						Categoria cat = new Categoria();
						cat.setId(d.getCategoria().getId());
						chave.getDuplaList().get(index).setCategoria(cat);
					}

				}
				index++;

			}
//			System.out.println(chave.getDuplaList().get(0).getNome() + " " + chave.getDuplaList().get(1).getNome()
//					+ " " + chave.getDuplaList().get(2).getNome()+" "+chave.getDuplaList().get(0).getCategoria().getNome());
			index = 0;
		}
		return listaChave;
	}

}
