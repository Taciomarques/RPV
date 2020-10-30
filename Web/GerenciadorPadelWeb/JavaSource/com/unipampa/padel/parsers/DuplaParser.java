package com.unipampa.padel.parsers;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.util.RestUtil;

public class DuplaParser {
	
	public static ArrayList<Dupla> createDupla(){
		
		String url = "http://localhost:8080/api/duplas";
		return converteJsonParaDupla(RestUtil.retornaDados(url));
		
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
