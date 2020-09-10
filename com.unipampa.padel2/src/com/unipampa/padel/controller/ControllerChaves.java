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
import com.unipampa.padel.persistence.PersisteAtleta;
import com.unipampa.padel.persistence.PersisteCategoria;
import com.unipampa.padel.persistence.PersisteChave;
import com.unipampa.padel.persistence.PersistePartida;

import connection.Connection;
import interfaces.PersisteAtletaIF;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteChaveIF;
import interfaces.PersistePartidaIF;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerChaves implements Initializable {

	@FXML
	private ScrollPane mainPane;

	@FXML
	private MenuButton menuButtonCategoria;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

//		-- Evento para quando escolher o item do menu ele atualizar a tabela--
		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Categoria c = new Categoria();
				c.setNome(((MenuItem) e.getSource()).getText());
				menuButtonCategoria.setText(c.getNome());

				try {
					loadChaves(c);
				} catch (MalformedURLException | RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};

//      -- adicionando os itens de categoria nos botoes e adicionando o evento --
		try {
			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");

			for (Categoria c : pC.recuperaCategorias()) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				menuButtonCategoria.getItems().add(m);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void loadChaves(Categoria c) throws MalformedURLException, RemoteException, NotBoundException {
		PersisteChaveIF pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
		ArrayList<Chave> todasChaves = pchave.recuperaChaves();
		ArrayList<Chave> chaves = new ArrayList<Chave>();

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

			int x = 0;
			int y = 0;
			for (int i = 0; i < chaves.size(); i++) {
				Chave chave = chaves.get(i);
				GridPane chaveGrid = new GridPane();

				Text text1 = new Text();
				text1.setText(chave.getNome() + " - " + chave.getDuplaList().get(0).getCategoria().getNome());
				chaveGrid.add(text1, 0, 0);

				Text text2 = new Text();
				text2.setText(chave.getDuplaList().get(0).getNome());
				chaveGrid.add(text2, 0, 1);

				Text text3 = new Text();
				text3.setText(chave.getDuplaList().get(1).getNome());
				chaveGrid.add(text3, 0, 2);

				Text text4 = new Text();
				text4.setText(chave.getDuplaList().get(2).getNome());
				chaveGrid.add(text4, 0, 3);

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

	}

//    x,y  x,y
//    0,0  1,0
//    0,1  1,1
//    0,2  1,2
//    0,3  1,3

	@SuppressWarnings("unchecked")
	public static void geraChaves() throws MalformedURLException, RemoteException, NotBoundException {
		PersisteAtletaIF pA = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");

		ArrayList<Dupla> todasDuplas = pA.recuperaDuplas();

		PersisteCategoriaIF pc = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
		ArrayList<Categoria> categorias = pc.recuperaCategorias();

		PersisteChaveIF pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");

		for (Categoria categoria : categorias) {

			ArrayList<Dupla> duplasPorCategoria = new ArrayList<>();
			for (Dupla dupla : todasDuplas) {
				if (dupla.getCategoria().getId() == categoria.getId() && dupla.isSuplente() == false) {
					duplasPorCategoria.add(dupla);
				}
			}

			Collections.sort(duplasPorCategoria);
			
			System.out.println(duplasPorCategoria.size() + " Duplas na Categoria "+ categoria.getNome());

			char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
			ArrayList<Chave> chaves = new ArrayList<>();

			for (int i = 0; i < duplasPorCategoria.size() / 3; i++) {
				Chave chave = new Chave();
				chave.setNome("" + alphabet[i]);
				chaves.add(chave);
			}

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
				pchave.cadastroChave(chave);
				String nomeChave = chave.getNome();
				String nomeCategoria = chave.getDuplaList().get(0).getCategoria().getNome();
				String nomeDupla1 = chave.getDuplaList().get(0).getNome();
				String nomeDupla2 = chave.getDuplaList().get(1).getNome();
				String nomeDupla3 = chave.getDuplaList().get(2).getNome();
				System.out.println(nomeChave + nomeCategoria + " \n " + nomeDupla1 + " \n " + nomeDupla2 + " \n "
						+ nomeDupla3 + " \n\n ");
			}
		}

		geraJogos();
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
			p.setDuplaList(duplas);
			duplas.clear();
			
			duplas.add(chave.getDuplaList().get(1));
			duplas.add(chave.getDuplaList().get(2));
			p1.setDuplaList(duplas);
			duplas.clear();

			duplas.add(chave.getDuplaList().get(0));
			duplas.add(chave.getDuplaList().get(2));
			p2.setDuplaList(duplas);
			duplas.clear();

			ptida.cadastroPartida(p);
			ptida.cadastroPartida(p1);
			ptida.cadastroPartida(p2);

		}
	}

}
