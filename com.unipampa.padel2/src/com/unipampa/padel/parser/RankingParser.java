package com.unipampa.padel.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Ranking;
import com.unipampa.padel.model.RankingPK;
import com.unipampa.padel.remote.RestUtil;

public class RankingParser {

	private static String url_get = "http://localhost:8080/api/rankings";
	private static String url_post = "http://localhost:8080/api/ranking";
	
	public static ArrayList<Ranking> createRanking() {
		return converteJsonParaRanking(RestUtil.retornaDados(url_get));
	}

	public static String cadastroRanking(Ranking ranking) throws IOException {
		return RestUtil.persisteDados(url_post, convertRankingParaJson(ranking));
	}

	private static String convertRankingParaJson(Ranking ranking) {

		Gson gson = new Gson();
		String json = gson.toJson(ranking);
		System.out.println(json);
	
		return json;
	}
	
	private static ArrayList<Ranking> converteJsonParaRanking(String output) {

		ArrayList<Ranking> listaRanking = new ArrayList<Ranking>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
			Ranking ranking = new Ranking();
			Atleta atleta = new Atleta();
			atleta.setId(Integer.parseInt(e.get("atleta1").toString()));
			ranking.setAtleta1(atleta);
			ranking.setPontosrank(Integer.parseInt(e.get("pontosrank").toString()));
			listaRanking.add(ranking);

		}
		return listaRanking;

	}
	
	private static ArrayList<Ranking> converteJsonParaRanking(BufferedReader br) {

		try {
		ArrayList<Ranking> listaRanking = new ArrayList<Ranking>();

		String line;

		Gson gson = new Gson();
		
		while ((line = br.readLine()) != null) {
			
			Ranking obj = gson.fromJson(line, Ranking.class);
			listaRanking.add(obj);
			
		}
		
		return listaRanking;
		
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}

