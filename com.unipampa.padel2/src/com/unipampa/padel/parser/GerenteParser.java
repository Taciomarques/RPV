package com.unipampa.padel.parser;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Gerente;
import com.unipampa.padel.remote.RestUtil;

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
	
	private static ArrayList<Gerente> converteJsonParaGerente(BufferedReader br) {

		try {
		ArrayList<Gerente> listaGerente = new ArrayList<Gerente>();

		String line;

		Gson gson = new Gson();
		
		while ((line = br.readLine()) != null) {
			
			Gerente obj = gson.fromJson(line, Gerente.class);
			listaGerente.add(obj);
			
		}
		
		return listaGerente;
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
}
