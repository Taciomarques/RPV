
package com.unipampa.padel.parser;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.util.RestUtil;

public class EtapaParser {

	public static ArrayList<Etapa> createEtapas() {

		String url = "http://localhost:8080/api/etapas";
		return converteJsonParaEtapa(RestUtil.retornaDados(url));

	}

	private static ArrayList<Etapa> converteJsonParaEtapa(String output) {

		ArrayList<Etapa> listaEtapa = new ArrayList<Etapa>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
			Etapa etapa = new Etapa();
			etapa.setId(Integer.parseInt(e.get("id").toString()));
			etapa.setNome(e.get("nome").toString());
			listaEtapa.add(etapa);

		}

		return listaEtapa;

	}

}