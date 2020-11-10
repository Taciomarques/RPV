package com.unipampa.padel.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Ranking;
import com.unipampa.padel.remote.RestUtil;

public class AtletaParser {

	private static String url_get = "http://localhost:8080/api/atletas";
	private static String url_post = "http://localhost:8080/api/atleta";

	public static ArrayList<Atleta> createAtletas() {
		return converteJsonParaAtleta(RestUtil.retornaDados(url_get));
	}

	public static String cadastroAtleta(Atleta atleta) throws IOException {
		return RestUtil.persisteDados(url_post, convertAtletaParaJson(atleta));
	}

	public static Atleta atualizaAtleta(String cpf) {
		return atualizaDados(cpf, converteJsonParaAtleta(RestUtil.retornaDados(url_get)));
	}

	private static String convertAtletaParaJson(Atleta atleta) {

		Gson gson = new Gson();
		String json = gson.toJson(atleta);
		System.out.println(json);

		return json;
	}

	private static ArrayList<Atleta> converteJsonParaAtleta(String output) {

		ArrayList<Atleta> listaAtleta = new ArrayList<Atleta>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
			Atleta atleta = new Atleta();
			atleta.setId(Integer.parseInt(e.get("id").toString()));
			atleta.setNome(e.get("nome").toString());
			atleta.setCpf(e.get("cpf").toString());
			atleta.setEmail(e.get("email").toString());
			listaAtleta.add(atleta);

		}

		return listaAtleta;

	}

	private static ArrayList<Atleta> converteJsonParaAtleta(BufferedReader br) {

		try {
			ArrayList<Atleta> listaAtleta = new ArrayList<Atleta>();

			String line;

			Gson gson = new Gson();

			while ((line = br.readLine()) != null) {

				Atleta obj = gson.fromJson(line, Atleta.class);
				listaAtleta.add(obj);

			}

			return listaAtleta;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Atleta atualizaDados(String cpf, ArrayList<Atleta> atletas) {
		for (Atleta a : atletas) {
			String cpf_validacao = a.getCpf().substring(1, a.getCpf().length() - 1);
			if (cpf.equals(cpf_validacao)) {
				return a;
			}
		}
		return null;
	}

}
