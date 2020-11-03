package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Partida;
import com.unipampa.padel.parser.CategoriaParser;
import com.unipampa.padel.parser.ChaveParser;
import com.unipampa.padel.parser.DuplaParser;

import connection.Connection;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteChaveIF;
import interfaces.PersistePartidaIF;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerChaves implements Initializable {

	@FXML
	private ScrollPane mainPane;

	@FXML
	private MenuButton menuButtonCategoria;

	private EventHandler<ActionEvent> eventoSelectCat;

	private ArrayList<Chave> todasChaves;

	private ArrayList<Chave> chaves;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
				carregaChaves(e);
			}
		};
		addItensCategoria();
	}

	public void carregaChaves(ActionEvent e) {
		try {
			Categoria c = new Categoria();
			c.setNome(((MenuItem) e.getSource()).getText());
			menuButtonCategoria.setText(c.getNome());
			loadChaves(c);
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}

	public void addItensCategoria() {
		try {
			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl()
					+ "categoria");
			for (Categoria c : pC.recuperaCategorias()) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				menuButtonCategoria.getItems().add(m);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}

	public void loadChaves(Categoria c) throws MalformedURLException, RemoteException, NotBoundException {
		
//		PersisteChaveIF pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
		ChaveParser pchave = new ChaveParser();	
		todasChaves = pchave.createChaves();
		chaves = new ArrayList<Chave>();
		
		for (Chave chaveIterator : todasChaves) {
			if (chaveIterator.getDuplaList().get(0).getCategoria().getNome().equalsIgnoreCase(c.getNome())) {
				chaves.add(chaveIterator);
			}
		}

		if (chaves.isEmpty()) {
			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("Opa!");
			alertErro.setHeaderText("Não há chaves nesta categoria!");
			alertErro.setContentText(
					"Por favor, certifique-se de que há duplas cadastradas nesta categoria e gere as chaves utilizando o menu.");
			alertErro.showAndWait();
		} else {
			GridPane mainGrid = new GridPane();
			mainGrid.setHgap(20);
			mainGrid.setVgap(20);
			mainGrid.setPadding(new Insets(20));

			mainPane.autosize();
			mainPane.setContent(mainGrid);

			addChaveDupla(mainGrid);
		}
	}

	public void addChaveDupla(GridPane mainGrid) {
		int x = 0;
		int y = 0;

		for (int i = 0; i < chaves.size(); i++) {

			Chave chave = chaves.get(i);
			GridPane chaveGrid = new GridPane();

			Text dupla = new Text();
			dupla.setText(chave.getNome() + " - " + chave.getDuplaList().get(0).getCategoria().getNome());
			chaveGrid.add(dupla, 0, 0);

			Text dupla1 = new Text();
			dupla1.setText(chave.getDuplaList().get(0).getNome());
			chaveGrid.add(dupla1, 0, 1);

			Text dupla3 = new Text();
			dupla3.setText(chave.getDuplaList().get(1).getNome());
			chaveGrid.add(dupla3, 0, 2);

			Text dupla4 = new Text();
			dupla4.setText(chave.getDuplaList().get(2).getNome());
			chaveGrid.add(dupla4, 0, 3);

			mainGrid.add(chaveGrid, x, y);

			if (i % 2 == 0) {
				x = 1;
			} else {
				x = 0;
				if (i != 0) {
					y++;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void geraChaves() throws MalformedURLException, RemoteException, NotBoundException {
//		PersisteAtletaIF pA = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");
		
		DuplaParser pA = new DuplaParser();
		ArrayList<Dupla> todasDuplas = pA.createDupla();

		
//		PersisteCategoriaIF pc = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
		
		CategoriaParser pc = new CategoriaParser();
		ArrayList<Categoria> categorias = pc.createCategoria();

//		PersisteChaveIF pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
		
		ChaveParser pchave = new ChaveParser();
		
		for (Categoria categoria : categorias) {

			ArrayList<Dupla> duplasPorCategoria = new ArrayList<>();
			for (Dupla dupla : todasDuplas) {
				if (dupla.getCategoria().getId() == categoria.getId() && dupla.isSuplente() == false) {
					duplasPorCategoria.add(dupla);
				}
			}
			Collections.sort(duplasPorCategoria);

			System.out.println(duplasPorCategoria.size() + " Duplas na Categoria " + categoria.getNome());

			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
			ArrayList<Chave> chaves = new ArrayList<>();

			for (int i = 0; i < duplasPorCategoria.size() / 3; i++) {
				Chave chave = new Chave();
				chave.setNome("" + alphabet[i]);
				chaves.add(chave);
			}

			createDuplaChave(pchave, duplasPorCategoria, chaves);
		}
//		geraJogos();
	}

	public static void createDuplaChave(ChaveParser pchave, ArrayList<Dupla> duplasPorCategoria,
			ArrayList<Chave> chaves) throws RemoteException {
		int i = 0;
		
		for (Chave chave : chaves) {
			Dupla dupla = duplasPorCategoria.get(i);
			chave.getDuplaList().add(dupla);
			i++;
		}
		for (int y = chaves.size(); y > 0; y--) {
			Dupla dupla = duplasPorCategoria.get(i);
			chaves.get(y - 1).getDuplaList().add(dupla);
			i++;
		}
		for (Chave chave : chaves) {
			Dupla dupla = duplasPorCategoria.get(i);
			chave.getDuplaList().add(dupla);
			i++;
		}
		for (Chave chave : chaves) {
			//TODO
//			pchave.cadastroChave(chave);
			String nomeChave = chave.getNome();
			String nomeCategoria = chave.getDuplaList().get(0).getCategoria().getNome();
			String nomeDupla1 = chave.getDuplaList().get(0).getNome();
			String nomeDupla2 = chave.getDuplaList().get(1).getNome();
			String nomeDupla3 = chave.getDuplaList().get(2).getNome();
			System.out.println(nomeChave + nomeCategoria + " \n " + nomeDupla1 + " \n " + nomeDupla2 + " \n "
					+ nomeDupla3 + " \n\n ");
		}
	}

	public static void geraJogos() throws MalformedURLException, RemoteException, NotBoundException {
		PersisteChaveIF pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
		ArrayList<Chave> chaves = pchave.recuperaChaves();

		PersistePartidaIF ptida = (PersistePartidaIF) Naming.lookup(Connection.getUrl() + "partida");

		for (int i = 0; i < chaves.size(); i++) {

			Chave chave = chaves.get(i);
			ArrayList<Dupla> duplas = new ArrayList<Dupla>();

			Partida p = new Partida();
			Partida p1 = new Partida();
			Partida p2 = new Partida();

			duplas.add(chave.getDuplaList().get(0));
			duplas.add(chave.getDuplaList().get(1));
			p.setNome(chave.getDuplaList().get(0).getNome()+"|X|"+chave.getDuplaList().get(1).getNome());
			p.setPontosDupla1(chave.getDuplaList().get(0).getPontosRank());
			p.setPontosDupla2(chave.getDuplaList().get(1).getPontosRank());
			p.setDuplaList(duplas);
			duplas.clear();

			duplas.add(chave.getDuplaList().get(1));
			duplas.add(chave.getDuplaList().get(2));
			p.setNome(chave.getDuplaList().get(1).getNome()+"|X|"+chave.getDuplaList().get(2).getNome());
			p.setPontosDupla1(chave.getDuplaList().get(1).getPontosRank());
			p.setPontosDupla2(chave.getDuplaList().get(2).getPontosRank());
			p1.setDuplaList(duplas);
			duplas.clear();

			duplas.add(chave.getDuplaList().get(0));
			duplas.add(chave.getDuplaList().get(2));
			p.setNome(chave.getDuplaList().get(0).getNome()+"|X|"+chave.getDuplaList().get(2).getNome());
			p.setPontosDupla1(chave.getDuplaList().get(0).getPontosRank());
			p.setPontosDupla2(chave.getDuplaList().get(2).getPontosRank());
			p2.setDuplaList(duplas);
			duplas.clear();

			ptida.cadastroPartida(p);
			ptida.cadastroPartida(p1);
			ptida.cadastroPartida(p2);
		}
	}
}