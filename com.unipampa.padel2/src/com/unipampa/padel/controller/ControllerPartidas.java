package com.unipampa.padel.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Partida;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ControllerPartidas implements Initializable {

	@FXML
	private ScrollPane mainPane;

	@FXML
	private MenuButton menuButtonCategoria;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

//		EventHandler<ActionEvent> eventoSelectCat = eventoCategoriaSelecionada();

//		try {
//			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");

//			adicionaCategoriasBotao(eventoSelectCat, pC);

//		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
//			e1.printStackTrace();
//		}
	}

//	private EventHandler<ActionEvent> eventoCategoriaSelecionada() {
//		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				Categoria c = new Categoria();
//				c.setNome(((MenuItem) e.getSource()).getText());
//				menuButtonCategoria.setText(c.getNome());
//				try {
//					carregaPartidas(c);
//				} catch (MalformedURLException | RemoteException | NotBoundException e1) {
//					e1.printStackTrace();
//				}
//			}
//		};
//		return eventoSelectCat;
//	}

//	private void adicionaCategoriasBotao(EventHandler<ActionEvent> eventoSelectCat, PersisteCategoriaIF pC)
//			throws RemoteException {
//		for (Categoria c : pC.recuperaCategorias()) {
//			MenuItem m = new MenuItem(c.getNome());
//			m.setOnAction(eventoSelectCat);
//			menuButtonCategoria.getItems().add(m);
//		}
//	}

//	public void carregaPartidas(Categoria c) throws MalformedURLException, RemoteException, NotBoundException {
//		ArrayList<Partida> partidas = filtraPartidasPorCategoria(c);
//
//		if (partidas.isEmpty()) {
//			alertWarningSemPartidas();
//		} else {
//			adicionaPartidasPainel(partidas);
//		}
//	}

	private void adicionaPartidasPainel(ArrayList<Partida> partidas) {
		GridPane mainGrid = new GridPane();
		mainGrid.setHgap(40);
		mainGrid.setVgap(40);
		mainGrid.setPadding(new Insets(20));

		mainPane.autosize();
		mainPane.setContent(mainGrid);

		int x = 0;
		int y = 0;
		for (int i = 0; i < partidas.size(); i++) {
			Partida partida = partidas.get(i);
			GridPane partidaGrid = new GridPane();

			Text text1 = new Text();
			text1.setText(partida.getDuplaList().get(0).getNome());
			partidaGrid.add(text1, 0, 0);

			Text text2 = new Text();
			text2.setText("VS.");
			partidaGrid.add(text2, 0, 1);

			Text text3 = new Text();
			text3.setText(partida.getDuplaList().get(1).getNome());
			partidaGrid.add(text3, 0, 2);

			mainGrid.add(partidaGrid, x, y);

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

//	private ArrayList<Partida> filtraPartidasPorCategoria(Categoria c)
//			throws NotBoundException, MalformedURLException, RemoteException {
//		PersistePartidaIF ptida = (PersistePartidaIF) Naming.lookup(Connection.getUrl() + "partida");
//		ArrayList<Partida> todasPartidas = ptida.recuperaPartida();
//
//		ArrayList<Partida> partidas = new ArrayList<Partida>();
//
//		for (Partida partidaIterator : todasPartidas) {
//			if (partidaIterator.getDuplaList().get(0).getCategoria().getNome().equalsIgnoreCase(c.getNome())) {
//				partidas.add(partidaIterator);
//			}
//		}
//		return partidas;
//	}

	private void alertWarningSemPartidas() {
		Alert alertErro = new Alert(AlertType.ERROR);
		alertErro.setTitle("Opa!");
		alertErro.setHeaderText("Não há partidas nesta categoria!");
		alertErro.setContentText(
				"Por favor, certifique-se de que há duplas cadastradas nesta categoria e gere as chaves e partidas utilizando o menu.");
		alertErro.showAndWait();
	}
}
