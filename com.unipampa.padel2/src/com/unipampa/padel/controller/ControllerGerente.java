package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.parser.CircuitoParser;
import com.unipampa.padel.view.Menu;
import com.unipampa.padel.view.ViewGerente;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ControllerGerente implements Initializable {

	@FXML
	private MenuButton menuBtnCircuitos;

	@FXML
	private Button btnCriar;

	@FXML
	private Button btnGerenciar;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Menu menu = new Menu();
				menu.start(new Stage());
				ViewGerente.sair();
			}
		};
		addItensCategoria(eventoSelectCat);
	}

	public void addItensCategoria(EventHandler<ActionEvent> eventoSelectCat) {
		try {
//			PersisteCircuitoIF pC = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl()
//					+ "circuito");
			CircuitoParser pC = new CircuitoParser();
			for (Circuito c : pC.createCircuitos()) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				menuBtnCircuitos.getItems().add(m);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void clickBotaoCriar() {
		Menu menu = new Menu();
		menu.start(new Stage());
		ViewGerente.sair();
	}
}
