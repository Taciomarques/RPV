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

import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Gerente;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.persistence.PersisteAtleta;
import com.unipampa.padel.persistence.PersisteCategoria;
import com.unipampa.padel.view.CadastrarDupla;
import com.unipampa.padel.view.Menu;
import com.unipampa.padel.view.ViewGerente;
import com.unipampa.padel.view.ViewInscritos;
import com.unipampa.padel.view.ViewLogin;

import connection.Connection;
import interfaces.PersisteAtletaIF;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteGerenteIF;
import interfaces.PersisteInscricaoIF;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class ControllerLogin implements Initializable {

	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtFieldLogin;
	
	@FXML
	private PasswordField passwordFieldLogin;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public void clickBotaoLogin(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
		PersisteGerenteIF pG = (PersisteGerenteIF) Naming.lookup(Connection.getUrl() + "gerente");
		
		ArrayList<Gerente> gerentes = pG.recuperaGerentes();
		Boolean sucesso = false;
		
		for (Gerente gerente : gerentes ) {
			if (txtFieldLogin.getText().equals( gerente.getLogin() )) {
				if (passwordFieldLogin.getText().equals(gerente.getSenha())) {
					sucesso = true;
					ViewGerente viewGerente = new ViewGerente();
					viewGerente.start(new Stage());
				}
			}
		}
		
		if(sucesso) {
			ViewLogin.sair();
		} else {
			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("ERRO");
			alertErro.setHeaderText("Credenciais Invalidas");
			alertErro.setContentText("Não foi possivel validar o seu login!");
			alertErro.showAndWait();
		}

	}

	
}