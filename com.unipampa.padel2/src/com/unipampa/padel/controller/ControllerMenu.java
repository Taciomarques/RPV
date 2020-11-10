package com.unipampa.padel.controller;

import java.io.IOException;
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
	private Button encerraCadastro;

	@FXML
	private Button botaoMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void clickBotaoEncerraCadastro(ActionEvent event) {

		if (encerraCadastro.getText().equals("Encerrar Inscrição de Duplas")) {
			encerraCadastro.setText("Reabrir Inscrição Duplas");
			cadastrarDuplas.setVisible(false);

		} else {

			encerraCadastro.setText("Encerrar Inscrição de Duplas");
			cadastrarDuplas.setVisible(true);
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

	public void clickBotaoGerChaves(ActionEvent event) throws IOException {
		try {
			ControllerChaves.geraChaves();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
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

		ViewPrevisaoPartidas vp = new ViewPrevisaoPartidas();
		vp.start(new Stage());

	}
}