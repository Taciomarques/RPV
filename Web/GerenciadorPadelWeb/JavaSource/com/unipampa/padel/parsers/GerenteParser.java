package com.unipampa.padel.parsers;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Gerente;
import com.unipampa.padel.util.RestUtil;

public class GerenteParser {

	public static ArrayList<Gerente> createGerente(){
		String url = "http://localhost:8080/api/gerentes";
		return converteJsonGerente(RestUtil.retornaDados(url));
	}
	
	private static ArrayList<Gerente> converteJsonGerente(String output) {
		
		ArrayList<Gerente> listaGerente = new ArrayList<Gerente>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
//			System.out.println(e.get("nome"));
			Gerente gerente = new Gerente();
			gerente.setId(Integer.parseInt(e.get("id").toString()));
			gerente.setLogin(e.get("login").toString());
			gerente.setNome(e.get("nome").toString());
			gerente.setSenha(e.get("senha").toString());
			
			ArrayList<Circuito> circuitos = new ArrayList<>();

			JsonArray c = (JsonArray) e.get("circuitoList");
//			System.out.println(c.size());
			for (int j = 0; j < c.size(); j++) {

				Circuito cir = new Circuito();
				cir.setId(Integer.parseInt(c.get(j).toString()));
				circuitos.add(cir);
			}
			gerente.setCircuitoList(circuitos);
			
			listaGerente.add(gerente);

		}
		
		return listaGerente;
		
	}
	
}
