package com.unipampa.padel.parser;

import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.DTO.DuplaDTO;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.util.RestUtil;

public class DuplaParser {

	private static String url_get = "http://localhost:8080/api/duplas";
	private static String url_post = "http://localhost:8080/api/dupla";

	public static ArrayList<Dupla> createDupla() {
		return converteJsonParaDuplas(RestUtil.retornaDados(url_get));
	}

	public static Dupla cadastroDupla(Dupla dupla) throws IOException {
		return converteJsonParaDupla(RestUtil.persisteDados(url_post, convertDuplaParaJson(convertDuplaParaDuplaDTO(dupla, false))));
	}

	public static void atualizaDupla(Dupla dupla) {
		RestUtil.atualizaDados(url_post, convertDuplaParaJson(convertDuplaParaDuplaDTO(dupla, true)));
	}

	public static DuplaDTO convertDuplaParaDuplaDTO(Dupla dupla, boolean atualiza) {

		DuplaDTO duplaDTO = new DuplaDTO();

		if (atualiza) {
			duplaDTO.setId(dupla.getId());
			duplaDTO.setSuplente(false);
		}

		duplaDTO.setNome(dupla.getNome());
		duplaDTO.setCategoria(dupla.getCategoria().getId());
		duplaDTO.setImpedimento(dupla.getImpedimento());
		duplaDTO.setPontosRank(dupla.getPontosRank());

		ArrayList<Integer> atletasIds = new ArrayList<>();
		for (Atleta a : dupla.getAtletaList()) {
			atletasIds.add(a.getId());
			System.out.println(a.getId());
		}

		duplaDTO.setAtletaList(atletasIds);

		return duplaDTO;
	}

	private static String convertDuplaParaJson(DuplaDTO duplaDTO) {

		Gson gson = new Gson();
		String json = gson.toJson(duplaDTO);
		System.out.println(json);

		return json;
	}

	private static ArrayList<Dupla> converteJsonParaDuplas(String output) {

		ArrayList<Dupla> listaDupla = new ArrayList<Dupla>();

		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

		int i = 0;
		for (i = 0; i < jsonArray.size(); i++) {

			JsonObject e = (JsonObject) jsonArray.get(i);
//			System.out.println(e.get("nome"));
			Dupla dupla = new Dupla();
			dupla.setId(Integer.parseInt(e.get("id").toString()));
			dupla.setNome(e.get("nome").toString());
			Categoria cat = new Categoria();
//			JsonObject c = (JsonObject) e.get("categoria");
			cat.setId(Integer.parseInt(e.get("categoria").toString()));
//			cat.setId(Integer.parseInt(c.get("id").toString()));
//			cat.setNome(c.get("nome").toString());
			dupla.setCategoria(cat);
			dupla.setImpedimento(e.get("impedimento").toString());
			if (e.get("suplente").toString().equalsIgnoreCase("true")) {
				dupla.setSuplente(true);
			} else {
				dupla.setSuplente(false);
			}
			dupla.setPontosRank(Integer.parseInt(e.get("pontosRank").toString()));

			ArrayList<Atleta> atletas = new ArrayList<>();

			JsonArray c = (JsonArray) e.get("atletaList");
//			System.out.println(c.size());
			for (int j = 0; j < c.size(); j++) {

				Atleta a = new Atleta();
				a.setId(Integer.parseInt(c.get(j).toString()));
				atletas.add(a);
			}
			dupla.setAtletaList(atletas);

			listaDupla.add(dupla);

		}
		return listaDupla;
	}

	private static Dupla converteJsonParaDupla(String output) {

		Dupla dupla = new Dupla();

		JsonParser jsonParser = new JsonParser();
		JsonObject j = (JsonObject) jsonParser.parse(output);

		dupla.setId(Integer.parseInt(j.get("id").toString()));
		dupla.setNome(j.get("nome").toString());
		Categoria cat = new Categoria();
		cat.setId(Integer.parseInt(j.get("categoria").toString()));
		dupla.setCategoria(cat);
		dupla.setImpedimento(j.get("impedimento").toString());
		if (j.get("suplente").toString().equalsIgnoreCase("true")) {
			dupla.setSuplente(true);
		} else {
			dupla.setSuplente(false);
		}
		dupla.setPontosRank(Integer.parseInt(j.get("pontosRank").toString()));

		ArrayList<Atleta> atletas = new ArrayList<>();

		JsonArray c = (JsonArray) j.get("atletaList");
//			System.out.println(c.size());
		for (int i = 0; i < c.size(); i++) {

			Atleta a = new Atleta();
			a.setId(Integer.parseInt(c.get(i).toString()));
			atletas.add(a);
		}
		dupla.setAtletaList(atletas);

		return dupla;
	}

}
