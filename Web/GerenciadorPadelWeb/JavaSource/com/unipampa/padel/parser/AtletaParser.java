package com.unipampa.padel.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.DTO.AtletaDTO;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.util.RestUtil;

public class AtletaParser {

	private static String url_get = "http://localhost:8080/api/atletas";
	private static String url_post = "http://localhost:8080/api/atleta";

	public static ArrayList<Atleta> createAtletas() {
		return converteJsonParaAtletas(RestUtil.retornaDados(url_get));
	}

	public static Atleta cadastroAtleta(Atleta atleta) throws IOException {
		return converteJsonParaAtleta(RestUtil.persisteDados(url_post, convertAtletaParaJson(convertAtletaParaAtletaDTO(atleta,false))));
	}

	public static void atualizaAtleta(Atleta atleta) {
		RestUtil.atualizaDados(url_post,convertAtletaParaJson(convertAtletaParaAtletaDTO(atleta,true)));
	}
	
	
	public static AtletaDTO convertAtletaParaAtletaDTO(Atleta atleta,boolean atualiza) {
		
		AtletaDTO atletaDTO = new AtletaDTO();
		atletaDTO.setCpf(atleta.getCpf());
		atletaDTO.setEmail(atleta.getEmail());
		atletaDTO.setNome(atleta.getNome());
		atletaDTO.setNumCel(atleta.getNumCel());
		
		if(atualiza) {
			atletaDTO.setId(atleta.getId());
			
		}
		
		return atletaDTO;
	}

	private static String convertAtletaParaJson(AtletaDTO atletaDTO) {

		Gson gson = new Gson();
		String json = gson.toJson(atletaDTO);
		System.out.println(json);

		return json;
	}

	private static ArrayList<Atleta> converteJsonParaAtletas(String output) {

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
	
	private static Atleta converteJsonParaAtleta(String output) {

		Atleta atleta = new Atleta();

		JsonParser jsonParser = new JsonParser();
		JsonObject j = (JsonObject) jsonParser.parse(output);
		
		atleta.setId(Integer.parseInt(j.get("id").toString()));
		atleta.setNome(j.get("nome").toString());
		atleta.setCpf(j.get("cpf").toString());
		atleta.setEmail(j.get("email").toString());

		return atleta;

	}

}
