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
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Partida;
import com.unipampa.padel.persistence.PersisteAtleta;
import com.unipampa.padel.persistence.PersisteCategoria;
import com.unipampa.padel.persistence.PersisteChave;
import com.unipampa.padel.persistence.PersistePartida;
import com.unipampa.padel.view.Menu;
import com.unipampa.padel.view.ViewGerente;

import connection.Connection;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteCircuitoIF;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
		
//		-- Evento para quando escolher o item do menu ele atualizar a tabela--
		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Menu menu = new Menu();
				menu.start(new Stage());
				ViewGerente.sair();
			}
		};
		
//      -- adicionando os itens de categoria nos botoes e adicionando o evento --
		try {
			PersisteCircuitoIF pC = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl() + "circuito");
		
			for (Circuito c : pC.recuperaCircuitos()) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				menuBtnCircuitos.getItems().add(m);
			}
		
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
    
    
    public void clickBotaoCriar() {
    	Menu menu = new Menu();
		menu.start(new Stage());
		ViewGerente.sair();
    }
    
}
