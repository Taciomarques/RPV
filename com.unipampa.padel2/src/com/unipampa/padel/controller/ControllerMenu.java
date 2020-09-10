package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.unipampa.padel.view.CadastrarDupla;
import com.unipampa.padel.view.ViewChaves;
import com.unipampa.padel.view.ViewInscritos;
import com.unipampa.padel.view.ViewPrevisaoPartidas;
import com.unipampa.padel.view.ViewRanking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMenu implements Initializable {

	@FXML
	private Button gerChaves;

	@FXML
	private Button cadastrarDuplas;

	@FXML
	private Button verificarParticipantes;

	@FXML
	private Button ranq;

	@FXML
	private Button encerraCadastro;

	@FXML
	private Button botaoMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).

	}

	public void clickBotaoEncerraCadastro(ActionEvent event) {

		if (encerraCadastro.getText().equals("Encerrar Inscrição de Duplas")) {
			encerraCadastro.setText("Reabrir Inscrição Duplas");
			cadastrarDuplas.setVisible(false);
//		  cadastrarDuplas.setEnabled(false);

		} else {

			encerraCadastro.setText("Encerrar Inscrição de Duplas");
			cadastrarDuplas.setVisible(true);
//		  cadastrarDuplas.setEnabled(true);
		}

	}

	public void clickBotaoCadastrarDupla(ActionEvent event) {

		CadastrarDupla cD = new CadastrarDupla();
		cD.start(new Stage());

	}

	public void clickVerificarInscritos(ActionEvent event) {

		ViewInscritos vI = new ViewInscritos();
		vI.start(new Stage());

	}

	public void clickBotaoRanking(ActionEvent event) {
		ViewRanking r = new ViewRanking();
		try {
			r.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickBotaoGerChaves(ActionEvent event) {
		try {
			ControllerChaves.geraChaves();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ViewChaves cv = new ViewChaves();
		cv.start(new Stage());

	}

	public void clickBotaoVerChaves(ActionEvent event) {

		ViewChaves cv = new ViewChaves();
		cv.start(new Stage());

	}

	public void clickBotaoVerJogos(ActionEvent event) {
//		ViewPartidas vp = new ViewPartidas();
		ViewPrevisaoPartidas vp = new ViewPrevisaoPartidas();
		vp.start(new Stage());

	}
}