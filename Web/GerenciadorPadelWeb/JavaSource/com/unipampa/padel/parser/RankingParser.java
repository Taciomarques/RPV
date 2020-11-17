package com.unipampa.padel.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.DTO.RankingDTO;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Ranking;
import com.unipampa.padel.util.RestUtil;

public class RankingParser {

	private static String url_get = "http://localhost:8080/api/rankings";
	private static String url_post = "http://localhost:8080/api/ranking";

	public static ArrayList<Ranking> createRanking() {
		return converteJsonParaRanking(RestUtil.retornaDados(url_get));
	}

	public static String cadastroRanking(Ranking ranking) throws IOException {
		return RestUtil.persisteDados(url_post, convertRankingParaJson(convertRankingParaRankingDTO(ranking)));
	}

	public static RankingDTO convertRankingParaRankingDTO(Ranking ranking) {

		RankingDTO rankingResponse = new RankingDTO();
		rankingResponse.setAtleta1(new Integer(ranking.getAtleta1().getId()));
		rankingResponse.setCategoria(new Integer(ranking.getCategoria().getId()));
		rankingResponse.setCircuito1(new Integer(ranking.getCircuito1().getId()));
		rankingResponse.setPontosrank(new Integer(ranking.getPontosrank()));

		return rankingResponse;
	}

	private static String convertRankingParaJson(RankingDTO rankingDTO) {

		Gson gson = new Gson();
		String json = gson.toJson(rankingDTO);
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

}
