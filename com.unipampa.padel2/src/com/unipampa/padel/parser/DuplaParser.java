package com.unipampa.padel.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.remote.RestUtil;

public class DuplaParser {
	
	private static String url_get = "http://localhost:8080/api/duplas";
	private static String url_post = "http://localhost:8080/api/dupla";
	
	public static ArrayList<Dupla> createDupla(){
		return converteJsonParaDupla(RestUtil.retornaDados(url_get));	
	}
	
	public static String cadastroDupla(Dupla dupla) throws IOException {
		return RestUtil.persisteDados(url_post, convertDuplaParaJson(dupla));
	}
	
	public static void atualizaDupla(Dupla dupla) {
		RestUtil.atualizaDados(url_post,convertDuplaParaJson(dupla));
	}
	
	private static String convertDuplaParaJson(Dupla dupla) {

		Gson gson = new Gson();
		String json = gson.toJson(dupla);
		System.out.println(json);

		return json;
	}

	private static ArrayList<Dupla> converteJsonParaDupla(String output){
		
		ArrayList<Dupla> listaDupla = new ArrayList<Dupla>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
//			System.out.println(e.get("nome"));
			Dupla dupla = new Dupla();
			dupla.setId(Integer.parseInt(e.get("id").toString()));
			dupla.setNome(e.get("nome").toString());
			Categoria cat = new Categoria();
//			JsonObject c = (JsonObject) e.get("categoria");
			cat.setId(Integer.parseInt(e.get("categoria").toString()));
//			cat.setId(Integer.parseInt(c.get("id").toString()));
//			cat.setNome(c.get("nome").toString());
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
		return listaDupla;
	}
	
}
