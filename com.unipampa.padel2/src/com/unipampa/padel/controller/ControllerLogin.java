package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Gerente;
import com.unipampa.padel.view.ViewGerente;
import com.unipampa.padel.view.ViewLogin;

import connection.Connection;
import interfaces.PersisteGerenteIF;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
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
	private TextField campoLogin;
	
	@FXML
	private PasswordField campoPassword;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	public void clickBotaoLogin(ActionEvent event) throws MalformedURLException, RemoteException, NotBoundException {
		PersisteGerenteIF pG = (PersisteGerenteIF) Naming.lookup(Connection.getUrl() + "gerente");
		ArrayList<Gerente> gerentes = pG.recuperaGerentes();	
		Boolean sucesso = false;
		for (Gerente g : gerentes ) {
			if (campoLogin.getText().equals( g.getLogin() )) {
				if (campoPassword.getText().equals(g.getSenha())) {
					sucesso = true;
					ViewGerente viewGerente = new ViewGerente();
					viewGerente.start(new Stage());
				}
			}
		}
		if(sucesso) ViewLogin.sair(); 
		else {
			errorAlert();
		}
	}

	public void errorAlert() {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setTitle("ERRO");
		errorAlert.setHeaderText("Credenciais Inv�lidas");
		errorAlert.setContentText("N�o foi poss�vel validar o seu login!");
		errorAlert.showAndWait();
	}
}