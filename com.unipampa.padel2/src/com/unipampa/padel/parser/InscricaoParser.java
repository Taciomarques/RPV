package com.unipampa.padel.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.remote.RestUtil;

public class InscricaoParser {

	private static String url_get = "http://localhost:8080/api/inscricoes";
	private static String url_post = "http://localhost:8080/api/inscricao";

	public static ArrayList<Inscricao> createInscricoes() {
		return converteJsonParaInscricao(RestUtil.retornaDados(url_get));
	}

	public static String cadastroInscricao(Inscricao inscricao) throws IOException {
		return RestUtil.persisteDados(url_post, convertInscricaoParaJson(inscricao));
	}

	private static String convertInscricaoParaJson(Inscricao inscricao) {

		Gson gson = new Gson();
		String json = gson.toJson(inscricao);
		System.out.println(json);

		return json;
	}

	private static ArrayList<Inscricao> converteJsonParaInscricao(String output) {
		try {
			ArrayList<Inscricao> listaInscricao = new ArrayList<Inscricao>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
				Dupla dupla = new Dupla();
				dupla.setId(Integer.parseInt(e.get("dupla1").toString()));
				Etapa etapa = new Etapa();
				etapa.setId(Integer.parseInt(e.get("etapa1").toString()));
				Inscricao inscricao = new Inscricao();
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date data = formato.parse(e.get("horainsc").toString());
				inscricao.setHorainsc(data);

				listaInscricao.add(inscricao);

			}

			return listaInscricao;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
