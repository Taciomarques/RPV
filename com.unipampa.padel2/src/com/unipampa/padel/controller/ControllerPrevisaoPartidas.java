package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Partida;
import com.unipampa.padel.model.Quadra;
import com.unipampa.padel.view.ViewPrevisaoPartidas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerPrevisaoPartidas implements Initializable {

	@FXML
	private Button botaoVoltar;

	@FXML
	private TableView tabela_partidas;

	@FXML
	private TableColumn<Partida, String> horario;

	@FXML
	private TableColumn<Partida, String> nome;

	@FXML
	private TableColumn<Quadra, String> quadra;

//	@FXML
//	private TableColumn categoria;

	@FXML
	private MenuButton diaList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		horario.setCellValueFactory(new PropertyValueFactory<>("dataHora"));
		nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		quadra.setCellValueFactory(new PropertyValueFactory<>("quadra"));
//		categoria.setCellValueFactory(new PropertyValueFactory<>("nomeCat"));

//		-- Evento para quando escolher o item do menu ele atualizar a tabela--
		EventHandler<ActionEvent> eventoSelectDia = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				String data = "";
				data = (((MenuItem) e.getSource()).getText());
				diaList.setText(data);
				try {
					tabela_partidas.setItems(FXCollections.observableArrayList(atualizaSuplencia(data)));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		};

//        -- adicionando os itens de categoria nos botoes e adicionando o evento --
		ArrayList<String> dias = new ArrayList<>();
		String dia1 = 4 + "", dia2 = 5 + "", dia3 = 6 + "", dia4 = 0 + "";
		dias.add(dia1);
		dias.add(dia2);
		dias.add(dia3);
		dias.add(dia4);

		for (String d : dias) {
			MenuItem m = new MenuItem(d);
			m.setOnAction(eventoSelectDia);
			diaList.getItems().add(m);
		}

//        -- popula inicialmente a tabela sem nada --

		String dia = "";

		try {
			tabela_partidas.setItems(FXCollections.observableArrayList(atualizaSuplencia(dia)));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void clickBotaoVoltar(ActionEvent event) {

		ViewPrevisaoPartidas.sair();

	}

	public static boolean alertConfimarion(int maxJogosEtapa, int numPartidasReduzir) {

		Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
		ButtonType btnSim = new ButtonType("Sim");
		ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
		dialogoExe.setTitle("Confirmação");
		dialogoExe.setHeaderText("O numero de partidas por etapa, excedeu o limite!");
		dialogoExe.setContentText(
				"Essa etapa suporta " + maxJogosEtapa + " partidas e o numero de partidas estimado para as \n"
						+ "chaves atuais excede esse limite. Você quer reduzir a quantidade de chaves das \n"
						+ "categorias que mais possuirem, a fim de reduzir a quantidade de partidas em "
						+ numPartidasReduzir + " ?");
		dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
		if (dialogoExe.showAndWait().get() == btnSim) {
			return true;
		} else {

			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("ERRO");
			alertErro.setHeaderText("Ok, você que sabe.");
			alertErro.setContentText("Não será possível gerar os jogos com esse quantitativo!");
			alertErro.showAndWait();

			return false;
		}

	}
	
	public static void alertFeedback(String chavesReduzidas) {
		Alert alertSucces = new Alert(AlertType.CONFIRMATION);
		alertSucces.setTitle("Sucesso");
		alertSucces.setHeaderText("As seguintes chaves foram eliminadas do circuito:");
		alertSucces.setContentText(chavesReduzidas);
		alertSucces.showAndWait();
	}
	
	public static void alertDelete() {
		Alert alertSucces = new Alert(AlertType.CONFIRMATION);
		alertSucces.setTitle("Sucesso");
		alertSucces.setHeaderText("A deleção foi bem sucessedida!");
		alertSucces.showAndWait();
	}
	
	public static void alertNaoDelete() {
		Alert alertSucces = new Alert(AlertType.ERROR);
		alertSucces.setTitle("ERRO");
		alertSucces.setHeaderText("A deleção não foi bem sucessedida!");
		alertSucces.showAndWait();
	}

	public ArrayList<Jogos> atualizaSuplencia(String dia)
			throws NumberFormatException, RemoteException, MalformedURLException, NotBoundException {

		Partidas p = new Partidas();
		try {
			p.geraGradePartidas();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// PersistePartidaIF part = (PersistePartidaIF)
		// Naming.lookup(Connection.getUrl() + "partida");
//		PersisteInscricaoIF pI = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");
		ArrayList<Jogos> partidas = new ArrayList<>();
		ArrayList<Partida> partidasList = new ArrayList<>();

//		ArrayList<Inscritos> inscritos = new ArrayList<>();
//		-- filtra as duplas por categoria escolhida para serem mostradas-- 
		ArrayList<Partida> partidasProntas = p.getPartidasProntas();
		for (Partida parts : partidasProntas) {

			if (dia.equals(parts.getDataHora().getDay() + "")) {

				partidasList.add(parts);

			}
		}

//		-- popula o array de inscritos com as duplas e transforma o atributo horaInscricao de date para String --
		for (Partida list : partidasList) {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");
			String dataFormatada = dateFormat.format(list.getDataHora());
			Jogos insc = new Jogos(list.getNome(), list.getQuadra().getId(), dataFormatada);
			partidas.add(insc);
		}

		return partidas;
	}

	public static class Jogos {

		private final SimpleStringProperty nome;
		// private final SimpleStringProperty nomeCat;
		private final SimpleStringProperty dataHora;
		private final SimpleStringProperty quadra;

		private Jogos(String nome, int quadra, String Hora) {
			this.nome = new SimpleStringProperty(nome);
			this.quadra = new SimpleStringProperty(quadra + "");
			this.dataHora = new SimpleStringProperty(Hora);
//			this.nomeCat = new SimpleStringProperty(categoria);
		}

		public String getNome() {
			return nome.get();
		}

		public String getDataHora() {
			return dataHora.get();
		}

		public String getQuadra() {
			return quadra.get();
		}
	}

}