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
import com.unipampa.padel.parsers.GerenteParser;
import com.unipampa.padel.util.JsfUtil;

@ManagedBean(name = "gerenteMB")
@SessionScoped
public class GerenteMB {

	private Gerente gerenteLogado = new Gerente();
	private Gerente gerente = new Gerente();
	private ArrayList<Gerente> gerentes = new ArrayList<Gerente>();
	private Circuito circuito = new Circuito();

	public ArrayList<Gerente> retornaGerentes() {
		try {

			gerentes = GerenteParser.createGerente();

			return this.gerentes;

		} catch (Exception e) {
			return null;
		}
	}

	public String login() {
		for (Gerente g : retornaGerentes()) {
			
			if (g.getLogin().equals("\""+gerente.getLogin()+"\"") && g.getSenha().equals("\""+gerente.getSenha()+"\"")) {
				JsfUtil.addSuccessMessage("Login Efetuado com Sucesso!");
				gerenteLogado = new Gerente();
				gerenteLogado.setId(g.getId());
				gerenteLogado.setLogin(g.getLogin());
				gerenteLogado.setNome(g.getNome());
				gerenteLogado.setSenha(g.getSenha());
				gerenteLogado.setCircuitoList(g.getCircuitoList());
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
