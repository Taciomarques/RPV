package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.unipampa.padel.controller.ControllerInscritos.Inscritos;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.persistence.PersisteAtleta;
import com.unipampa.padel.persistence.PersisteCategoria;
import com.unipampa.padel.view.ViewRanking;

import connection.Connection;
import interfaces.PersisteAtletaIF;
import interfaces.PersisteCategoriaIF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerRanking implements Initializable {

	@FXML
	private MenuButton categoriaLista;

	@FXML
	private Button botaoVoltar;

	@FXML
	private TableView<Atleta> tabela_ranking;

	@FXML
	private TableColumn<Atleta, String> nome;

	@FXML
	private TableColumn<Atleta, String> cpf;

	@FXML
	private TableColumn<Atleta, Integer> pontos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {

			nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
			pontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));
			
			EventHandler<ActionEvent> eventoCategoria = eventoAtualizaTabela();

			adicionaItensEvento(eventoCategoria);

			Categoria c = new Categoria();
			c.setNome(categoriaLista.getAccessibleText());

			tabela_ranking.setItems(FXCollections.observableArrayList(atualizaRanking(c)));
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			
			e1.printStackTrace();
		}
	}

	private void adicionaItensEvento(EventHandler<ActionEvent> eventoCategoria)
			throws NotBoundException, MalformedURLException, RemoteException {
		PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");

		for (Categoria c : pC.recuperaCategorias()) {
			MenuItem m = new MenuItem(c.getNome());
			m.setOnAction(eventoCategoria);
			categoriaLista.getItems().add(m);
		}
	}

	private EventHandler<ActionEvent> eventoAtualizaTabela() {

		EventHandler<ActionEvent> eventoCategoria = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Categoria c = new Categoria();
				c.setNome(((MenuItem) e.getSource()).getText());
				categoriaLista.setText(c.getNome());
				try {
					tabela_ranking.setItems(FXCollections.observableArrayList(atualizaRanking(c)));
				} catch (MalformedURLException | RemoteException | NotBoundException e1) {
					e1.printStackTrace();
				}
			}
		};
		return eventoCategoria;
	}

	public void clickBotaoVoltar(ActionEvent event) {

		ViewRanking.sair();

	}

	public ArrayList<Atleta> atualizaRanking(Categoria c)
			throws MalformedURLException, RemoteException, NotBoundException {
		PersisteAtletaIF pA = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");
		ArrayList<Atleta> atleta = new ArrayList<>();

		ArrayList<Atleta> atletasRanking = new ArrayList<>();
//		-- filtra as duplas por categoria escolhida para serem mostradas-- 
		for (Dupla d : pA.recuperaDuplas()) {
			if (d.getCategoria().getNome().equals(c.getNome())) {
				atleta.add(d.getAtletaList().get(0));
				atleta.add(d.getAtletaList().get(1));
			}
		}

		for (Atleta d : atleta) {
			System.out.println(d.getCpf());
			System.out.println(d.getNome());

			System.out.println(d.getRankList().get(0));
			Atleta ranking = new Atleta(d.getNome(), d.getCpf(), d.getRankList());
			atletasRanking.add(ranking);
		}

		return atletasRanking;
	}
}
