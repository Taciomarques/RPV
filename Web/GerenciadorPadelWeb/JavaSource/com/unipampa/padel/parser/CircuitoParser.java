package com.unipampa.padel.parser;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.util.RestUtil;

public class CircuitoParser {
		
		public static ArrayList<Circuito> createCircuitos(){
			
			String url = "http://localhost:8080/api/circuitos";
			return converteJsonParaCircuito(RestUtil.retornaDados(url));
			
		}

		private static ArrayList<Circuito> converteJsonParaCircuito(String output){
			
			ArrayList<Circuito> listaCircuito = new ArrayList<Circuito>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
				Circuito circuito = new Circuito();
				circuito.setId(Integer.parseInt(e.get("id").toString()));
				circuito.setNome(e.get("nome").toString());
				listaCircuito.add(circuito);

			}
			
			return listaCircuito;

		}
		
	}
