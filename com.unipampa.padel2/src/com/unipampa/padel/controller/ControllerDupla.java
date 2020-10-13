package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.model.InscricaoPK;
import com.unipampa.padel.model.Ranking;
import com.unipampa.padel.model.RankingPK;
import com.unipampa.padel.view.CadastrarDupla;
import connection.Connection;
import interfaces.PersisteAtletaIF;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteCircuitoIF;
import interfaces.PersisteEtapaIF;
import interfaces.PersisteInscricaoIF;
import interfaces.PersisteRankingIF;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ControllerDupla implements Initializable {

	@FXML
	private Button botaoInscrever;

	@FXML
	private Button botaoCancelar;

	@FXML
	private TextField nomeAtleta1;

	@FXML
	private TextField nomeAtleta2;

	@FXML
	private TextField emailAtleta1;

	@FXML
	private TextField emailAtleta2;

	@FXML
	private TextField numCelAtleta1;

	@FXML
	private TextField numCelAtleta2;

	@FXML
	private TextField cpfAtleta1;

	@FXML
	private TextField cpfAtleta2;

	@FXML
	private MenuButton catDupla;

	@FXML
	private MenuButton selectImpedimento;

	private ArrayList<Categoria> categorias;

	private EventHandler<ActionEvent> eventoSelectImpedimento;

	private EventHandler<ActionEvent> eventoSelectCat;

	private List<Dupla> listDuplas;

	private ArrayList<Circuito> circuitos;

	private ArrayList<Etapa> etapas;

	private MenuItem sabadoManhaMenuItem;

	private MenuItem sabadoTardeMenuItem;

	private MenuItem sabadoNoiteMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mascaraCPF(cpfAtleta1);
		mascaraCPF(cpfAtleta2);
		mascaraEmail(emailAtleta1);
		mascaraEmail(emailAtleta2);
		mascaraTelefone(numCelAtleta1);
		mascaraTelefone(numCelAtleta2);

		eventoSelectImpedimento = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				selectImpedimento.setText(((MenuItem) e.getSource()).getText());
			}
		};

		getSabadoManhaItem();
		getSabadoTardeMenuItem();
		getSabadoNoiteMenuItem();

		eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Categoria c = new Categoria();
				c.setNome(((MenuItem) e.getSource()).getText());
				catDupla.setText(c.getNome());
			}
		};
		populaItemCategoria();
	}

	public MenuItem getSabadoManhaItem() {
		if (sabadoManhaMenuItem != null) {
			sabadoManhaMenuItem = new MenuItem("Sábado Manhã");
			sabadoManhaMenuItem.setOnAction(eventoSelectImpedimento);
			selectImpedimento.getItems().add(sabadoManhaMenuItem);
		}
		return sabadoManhaMenuItem;
	}

	public MenuItem getSabadoTardeMenuItem() {
		if (sabadoTardeMenuItem != null) {
			sabadoTardeMenuItem = new MenuItem("Sábado Tarde");
			sabadoTardeMenuItem.setOnAction(eventoSelectImpedimento);
			selectImpedimento.getItems().add(sabadoTardeMenuItem);
		}
		return sabadoTardeMenuItem;
	}

	public MenuItem getSabadoNoiteMenuItem() {
		if (sabadoNoiteMenuItem != null) {
			sabadoNoiteMenuItem = new MenuItem("Sábado Noite");
			sabadoNoiteMenuItem.setOnAction(eventoSelectImpedimento);
			selectImpedimento.getItems().add(sabadoNoiteMenuItem);
		}
		return sabadoNoiteMenuItem;
	}

	public void populaItemCategoria() {
		try {
			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
			categorias = pC.recuperaCategorias();
			for (Categoria c : categorias) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				catDupla.getItems().add(m);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}

	public void clickBotaoCancelar(ActionEvent event) {
		CadastrarDupla.sair();
	}

	public void clickBotaoInscrever(ActionEvent event)
			throws RemoteException, MalformedURLException, NotBoundException {
		if (nomeAtleta1.getText().equals("") || nomeAtleta2.getText().equals("") || nomeAtleta1.getText() == null
				|| nomeAtleta2.getText() == null) {
			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("ERRO");
			alertErro.setHeaderText("Cadastro Invalido");
			alertErro.setContentText("Campos obrigatorios nao foram preenchidos!");
			alertErro.showAndWait();
		} else {
			createAtleta();
		}
	}

	public void createAtleta() {
		try {
			Atleta at1 = new Atleta();
			at1.setNome(nomeAtleta1.getText());
			at1.setEmail(emailAtleta1.getText());
			at1.setCpf(cpfAtleta1.getText());
			at1.setNumCel(numCelAtleta1.getText());

			Atleta at2 = new Atleta();
			at2.setNome(nomeAtleta2.getText());
			at2.setEmail(emailAtleta2.getText());
			at2.setCpf(cpfAtleta2.getText());
			at2.setNumCel(numCelAtleta2.getText());

			PersisteAtletaIF pa = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");
			at1 = pa.cadastroAtleta(at1);
			at2 = pa.cadastroAtleta(at2);

			Categoria categoriaDupla = new Categoria();
			for (Categoria c : categorias) {
				if (c.getNome().equals(catDupla.getText())) {
					categoriaDupla.setId(c.getId());
					categoriaDupla.setNome(c.getNome());
				}
			}
			Dupla dupla = new Dupla();
			ArrayList<Atleta> atletas = new ArrayList<Atleta>();
			atletas.add(at1);
			atletas.add(at2);
			dupla.setAtletaList(atletas);
			dupla.setPontosRank(0);

			dupla.setNome(nomeAtleta1.getText() + "/" + nomeAtleta2.getText());
			dupla.setCategoria(categoriaDupla);

			String impedimento = selectImpedimento.getText();

			if (!impedimento.equalsIgnoreCase("Escolher Impedimento") && impedimento != null) {
				dupla.setImpedimento(impedimento);
			}
			dupla = pa.cadastroDupla(dupla);
			dupla = pa.atualizaDupla(dupla);

			listDuplas = new ArrayList<Dupla>();
			listDuplas.add(dupla);
			at1.setDuplas(listDuplas);
			at2.setDuplas(listDuplas);
			at1 = pa.atualizaAtleta(at1);
			at2 = pa.atualizaAtleta(at2);

			PersisteCircuitoIF pCircuito = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl() + "circuito");
			circuitos = pCircuito.recuperaCircuitos();
			Circuito cct = circuitos.get(0);

			Ranking ranking = new Ranking();

			ranking.setRankingPK(new RankingPK(at1.getId(), cct.getId()));
			ranking.setAtleta1(at1);
			ranking.setPontosrank(0);
			ranking.setCategoria(categoriaDupla);
			ranking.setCircuito1(circuitos.get(0));

			Ranking ranking2 = new Ranking();

			ranking2.setRankingPK(new RankingPK(at2.getId(), cct.getId()));
			ranking2.setAtleta1(at2);
			ranking2.setPontosrank(0);
			ranking2.setCategoria(categoriaDupla);
			ranking2.setCircuito1(circuitos.get(0));

			PersisteRankingIF pRanking = (PersisteRankingIF) Naming.lookup(Connection.getUrl() + "ranking");

			ranking = pRanking.cadastroRanking(ranking);
			ranking2 = pRanking.cadastroRanking(ranking2);

			PersisteEtapaIF pe = (PersisteEtapaIF) Naming.lookup(Connection.getUrl() + "etapa");
			etapas = pe.recuperaEtapas();

			Inscricao inscricao = new Inscricao();

			Etapa etp = etapas.get(0);

			inscricao.setInscricaoPK(new InscricaoPK(dupla.getId(), etp.getId()));
			inscricao.setDupla1(dupla);

			Date horaInsc = new Date();

			inscricao.setEtapa1(etapas.get(0));
			inscricao.setHorainsc(horaInsc);

			PersisteInscricaoIF pInscricao = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");

			if (pInscricao.cadastroInscricao(inscricao) != null) {
				System.out.println("dupla " + dupla.getNome() + " cadastrada com sucesso");
			}
			alertSuccess(atletas);
		} catch (Exception e) {
			alertError(e);
		}
	}

	public void alertError(Exception e) {
		Alert alertErro = new Alert(AlertType.ERROR);
		alertErro.setTitle("ERRO");
		alertErro.setHeaderText("ERRO");
		alertErro.setContentText("ERRO inesperado, tente novamente!");
		alertErro.showAndWait();
		e.printStackTrace();
	}

	public void alertSuccess(ArrayList<Atleta> atletas) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("INFORMATION");
		alert.setHeaderText("SUCESSO");
		alert.setContentText("Dupla cadastrada com Sucesso!");
		alert.showAndWait();
		CadastrarDupla.sair();
		atletas.clear();
	}

	public static void mascaraCPF(TextField campoCPF) {
		campoCPF.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}
			if (event.getCharacter().trim().length() == 0) {
				deleteCharactersCPF(campoCPF);
			} else {
				writeCharactersCPF(campoCPF, event);
			}
		});
		campoCPF.setOnKeyReleased((KeyEvent evt) -> {
			if (!campoCPF.getText().matches("\\d.-*")) {
				campoCPF.setText(campoCPF.getText().replaceAll("[^\\d.-]", ""));
				campoCPF.positionCaret(campoCPF.getText().length());
			}
		});
	}

	public static void writeCharactersCPF(TextField writeCampoCPF, KeyEvent event) {
		if (writeCampoCPF.getText().length() == 14)
			event.consume();
		if (writeCampoCPF.getText().length() == 3) {
			writeCampoCPF.setText(writeCampoCPF.getText() + ".");
			writeCampoCPF.positionCaret(writeCampoCPF.getText().length());
		}
		if (writeCampoCPF.getText().length() == 7) {
			writeCampoCPF.setText(writeCampoCPF.getText() + ".");
			writeCampoCPF.positionCaret(writeCampoCPF.getText().length());
		}
		if (writeCampoCPF.getText().length() == 11) {
			writeCampoCPF.setText(writeCampoCPF.getText() + "-");
			writeCampoCPF.positionCaret(writeCampoCPF.getText().length());
		}
	}

	public static void deleteCharactersCPF(TextField deleteCampoCPF) {
		if (deleteCampoCPF.getText().length() == 4) {
			deleteCampoCPF.setText(deleteCampoCPF.getText().substring(0, 3));
			deleteCampoCPF.positionCaret(deleteCampoCPF.getText().length());
		}
		if (deleteCampoCPF.getText().length() == 8) {
			deleteCampoCPF.setText(deleteCampoCPF.getText().substring(0, 7));
			deleteCampoCPF.positionCaret(deleteCampoCPF.getText().length());
		}
		if (deleteCampoCPF.getText().length() == 12) {
			deleteCampoCPF.setText(deleteCampoCPF.getText().substring(0, 11));
			deleteCampoCPF.positionCaret(deleteCampoCPF.getText().length());
		}
	}

	public static void mascaraEmail(TextField campoEmail) {

		String alphabetNumeral = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@";
		campoEmail.setOnKeyTyped((KeyEvent event) -> {
			if (alphabetNumeral.contains(event.getCharacter()) == false) {
				event.consume();
			}
			if ("@".equals(event.getCharacter()) && campoEmail.getText().contains("@")) {
				event.consume();
			}
			if ("@".equals(event.getCharacter()) && campoEmail.getText().length() == 0) {
				event.consume();
			}
		});
	}

	public static void mascaraTelefone(TextField campoTelefone) {

		String numeral = "0123456789";
		campoTelefone.setOnKeyTyped((KeyEvent event) -> {
			if (numeral.contains(event.getCharacter()) == false) {
				event.consume();
			}
			if (event.getCharacter().trim().length() == 0) {
				deleteCharactersTelefone(campoTelefone);
			} else {
				writeCharactersTelefone(campoTelefone, event);
			}
		});
		campoTelefone.setOnKeyReleased((KeyEvent evt) -> {
			if (!campoTelefone.getText().matches("\\d()-*")) {
				campoTelefone.setText(campoTelefone.getText().replaceAll("[^\\d()-]", ""));
				campoTelefone.positionCaret(campoTelefone.getText().length());
			}
		});
	}

	public static void writeCharactersTelefone(TextField writeCampoTelefone, KeyEvent event) {
		if (writeCampoTelefone.getText().length() == 14)
			event.consume();
		if (writeCampoTelefone.getText().length() == 0) {
			writeCampoTelefone.setText("(" + event.getCharacter());
			writeCampoTelefone.positionCaret(writeCampoTelefone.getText().length());
			event.consume();
		}
		if (writeCampoTelefone.getText().length() == 3) {
			writeCampoTelefone.setText(writeCampoTelefone.getText() + ")" + event.getCharacter());
			writeCampoTelefone.positionCaret(writeCampoTelefone.getText().length());
			event.consume();
		}
		if (writeCampoTelefone.getText().length() == 8) {
			writeCampoTelefone.setText(writeCampoTelefone.getText() + "-" + event.getCharacter());
			writeCampoTelefone.positionCaret(writeCampoTelefone.getText().length());
			event.consume();
		}
		if (writeCampoTelefone.getText().length() == 9 && writeCampoTelefone.getText().substring(8, 9) != "-") {
			writeCampoTelefone.setText(writeCampoTelefone.getText() + "-" + event.getCharacter());
			writeCampoTelefone.positionCaret(writeCampoTelefone.getText().length());
			event.consume();
		}
		if (writeCampoTelefone.getText().length() == 13 && writeCampoTelefone.getText().substring(8, 9).equals("-")) {
			writeCampoTelefone.setText(
					writeCampoTelefone.getText().substring(0, 8) + writeCampoTelefone.getText().substring(9, 10) + "-"
							+ writeCampoTelefone.getText().substring(10, 13) + event.getCharacter());
			writeCampoTelefone.positionCaret(writeCampoTelefone.getText().length());
			event.consume();
		}
	}

	public static void deleteCharactersTelefone(TextField deleteCampoTelefone) {
		if (deleteCampoTelefone.getText().length() == 10
				&& deleteCampoTelefone.getText().substring(9, 10).equals("-")) {
			deleteCampoTelefone.setText(deleteCampoTelefone.getText().substring(0, 9));
			deleteCampoTelefone.positionCaret(deleteCampoTelefone.getText().length());
		}
		if (deleteCampoTelefone.getText().length() == 9 && deleteCampoTelefone.getText().substring(8, 9).equals("-")) {
			deleteCampoTelefone.setText(deleteCampoTelefone.getText().substring(0, 8));
			deleteCampoTelefone.positionCaret(deleteCampoTelefone.getText().length());
		}
		if (deleteCampoTelefone.getText().length() == 4) {
			deleteCampoTelefone.setText(deleteCampoTelefone.getText().substring(0, 3));
			deleteCampoTelefone.positionCaret(deleteCampoTelefone.getText().length());
		}
		if (deleteCampoTelefone.getText().length() == 1) {
			deleteCampoTelefone.setText("");
		}
	}
}