package com.unipampa.padel.parser;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.remote.RestUtil;

public class AtletaParser {
	
	public static ArrayList<Atleta> createAtletas(){
		
		String url = "http://localhost:8080/api/atletas";
		return converteJsonParaAtleta(RestUtil.retornaDados(url));
		
	}

	private static ArrayList<Atleta> converteJsonParaAtleta(String output){
		
		ArrayList<Atleta> listaAtleta = new ArrayList<Atleta>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
			Atleta atleta = new Atleta();
			atleta.setId(Integer.parseInt(e.get("id").toString()));
			atleta.setNome(e.get("nome").toString());
			listaAtleta.add(atleta);

		}
		
		return listaAtleta;

	}
	
}
