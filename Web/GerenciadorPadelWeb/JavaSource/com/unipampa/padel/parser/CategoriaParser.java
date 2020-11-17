package com.unipampa.padel.parser;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.util.RestUtil;

public class CategoriaParser {
		
		public static ArrayList<Categoria> createCategoria(){
			
			String url = "http://localhost:8080/api/categorias";
			return converteJsonParaCategoria(RestUtil.retornaDados(url));
			
		}

		private static ArrayList<Categoria> converteJsonParaCategoria(String output){
			
			ArrayList<Categoria> listaCategoria = new ArrayList<Categoria>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
				Categoria categoria = new Categoria();
				categoria.setId(Integer.parseInt(e.get("id").toString()));
				categoria.setNome(e.get("nome").toString());
				listaCategoria.add(categoria);

			}
			
			return listaCategoria;

		}		
}

