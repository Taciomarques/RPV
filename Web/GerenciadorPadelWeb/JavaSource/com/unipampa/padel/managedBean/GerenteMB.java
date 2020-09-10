package com.unipampa.padel.managedBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.context.RequestContext;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Gerente;

@ManagedBean(name = "gerenteMB")
@SessionScoped
public class GerenteMB {

	private Gerente gerenteLogado = new Gerente();
	private Gerente gerente = new Gerente();
	private ArrayList<Gerente> gerentes = new ArrayList<Gerente>();
	private Circuito circuito = new Circuito();

	public ArrayList<Gerente> retornaGerentes() {

		try {

			String url = "http://localhost:8080/api/gerentes";

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = "";
			String line;
			while ((line = br.readLine()) != null) {
				output += line;
			}

			conn.disconnect();

//            Gson gson = new Gson();
//            Dupla dupla = gson.fromJson(new String(output.getBytes()), Dupla.class);

//            JSONArray response_json = new JSONArray (output);
//            ObjectMapper mapper = new ObjectMapper();
//            List<Object> listTest = mapper.readValue(String.valueOf(response_json), List.class);

//            for(Object o : listTest) {
//            	Dupla d = (Dupla)o;
//            	System.out.println(d.getNome());
//            }

//			String json = new Gson().toJson(output);

			ArrayList<Gerente> listaGerente = new ArrayList<Gerente>();

			JsonParser jsonParser = new JsonParser();
			JsonArray jsonArray = (JsonArray) jsonParser.parse(output);

			int i = 0;
			for (i = 0; i < jsonArray.size(); i++) {

				JsonObject e = (JsonObject) jsonArray.get(i);
//				System.out.println(e.get("nome"));
				Gerente gerente = new Gerente();
				gerente.setId(Integer.parseInt(e.get("id").toString()));
				gerente.setLogin(e.get("login").toString());
				gerente.setNome(e.get("nome").toString());
				gerente.setSenha(e.get("senha").toString());
				
				ArrayList<Circuito> circuitos = new ArrayList<>();

				JsonArray c = (JsonArray) e.get("circuitoList");
//				System.out.println(c.size());
				for (int j = 0; j < c.size(); j++) {

					Circuito cir = new Circuito();
					cir.setId(Integer.parseInt(c.get(j).toString()));
					circuitos.add(cir);
				}
				gerente.setCircuitoList(circuitos);
				
				listaGerente.add(gerente);

			}
			
//			System.out.println(listaGerente.size());
			
			gerentes = listaGerente;

			return listaGerente;

		} catch (Exception e) {

			return null;

		}
	}

	public String login() {
		for (Gerente g : retornaGerentes()) {
//			System.out.println(gerente.getLogin()+" "+gerente.getSenha());
//			System.out.println(g.getLogin()+" "+g.getSenha());
			
			if (g.getLogin().equals("\""+gerente.getLogin()+"\"") && g.getSenha().equals("\""+gerente.getSenha()+"\"")) {
				JsfUtil.addSuccessMessage("Login Efetuado com Sucesso!");
				gerenteLogado = new Gerente();
				gerenteLogado.setId(g.getId());
				gerenteLogado.setLogin(g.getLogin());
				gerenteLogado.setNome(g.getNome());
				gerenteLogado.setSenha(g.getSenha());
				gerenteLogado.setCircuitoList(g.getCircuitoList());
//				System.out.println(gerenteLogado.getCircuitoList().get(0).getId());
				return "paginaGerente";
			}
		}
		return "paginaInicial";
	}

	public Gerente getGerenteLogado() {
		return gerenteLogado;
	}

	public void setGerenteLogado(Gerente gerenteLogado) {
		this.gerenteLogado = gerenteLogado;
	}

	public ArrayList<Gerente> getGerentes() {
		return gerentes;
	}

	public void setGerentes(ArrayList<Gerente> gerentes) {
		this.gerentes = gerentes;
	}

	public String logout() {

		gerente = new Gerente();
		JsfUtil.addSuccessMessage("Logout Efetuado com Sucesso!");
		return "paginaInicial";
	}

	public void carregaCircuito() {
		circuito.setNome("test");
		if (circuito.getId() == 1) {
			JsfUtil.addSuccessMessage("Circuito " + circuito.getNome() + " carregado com Sucesso!");

		}

	}

	public Circuito getCircuito() {
		return circuito;
	}

	public void setCircuito(Circuito circuito) {
		this.circuito = circuito;
	}

	public Gerente getGerente() {
		return gerente;
	}

	public void setGerente(Gerente gerente) {
		this.gerente = gerente;
	}

}
