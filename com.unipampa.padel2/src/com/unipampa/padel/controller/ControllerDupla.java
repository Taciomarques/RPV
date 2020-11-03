package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.hibernate.Hibernate;

import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.model.InscricaoPK;
import com.unipampa.padel.model.Ranking;
import com.unipampa.padel.model.RankingPK;
import com.unipampa.padel.parser.AtletaParser;
import com.unipampa.padel.parser.CategoriaParser;
import com.unipampa.padel.parser.CircuitoParser;
import com.unipampa.padel.parser.DuplaParser;
import com.unipampa.padel.parser.EtapaParser;
import com.unipampa.padel.view.CadastrarDupla;

import connection.Connection;
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
		sabadoManhaMenuItem = new MenuItem("Sábado Manhã");
		sabadoManhaMenuItem.setOnAction(eventoSelectImpedimento);
		selectImpedimento.getItems().add(sabadoManhaMenuItem);

		return sabadoManhaMenuItem;
	}

	public MenuItem getSabadoTardeMenuItem() {
		sabadoTardeMenuItem = new MenuItem("Sábado Tarde");
		sabadoTardeMenuItem.setOnAction(eventoSelectImpedimento);
		selectImpedimento.getItems().add(sabadoTardeMenuItem);

		return sabadoTardeMenuItem;
	}

	public MenuItem getSabadoNoiteMenuItem() {
		sabadoNoiteMenuItem = new MenuItem("Sábado Noite");
		sabadoNoiteMenuItem.setOnAction(eventoSelectImpedimento);
		selectImpedimento.getItems().add(sabadoNoiteMenuItem);
		return sabadoNoiteMenuItem;
	}

	public void populaItemCategoria() {
		try {
//			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
			categorias = CategoriaParser.createCategoria();
			for (Categoria c : categorias) {
				
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				catDupla.getItems().add(m);
			}
		} catch (Exception e1) {
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
		Alert alert = new Alert(AlertType.INFORMATION);
		boolean carregado = false, carregado2 = false;

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

//			PersisteAtletaIF pa = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");

			for (Atleta att : AtletaParser.createAtletas()) {
				if (att.getCpf().equals(at1.getCpf())) {

					at1 = att;

					alert.setTitle("INFORMATION");
					alert.setHeaderText("INFORMATION");
					alert.setContentText("Atleta1 já cadastrado, dados carregados do Banco!");
					alert.showAndWait();
					carregado = true;
				}
				if (att.getCpf().equals(at2.getCpf())) {
					at2.setId(att.getId());
					at2.setNome(att.getNome());
					at2.setNumCel(att.getNumCel());
					at2.setEmail(att.getEmail());
					at2.setDuplas(att.getDuplas());
					at2.setRankList(att.getRankList());

					alert.setTitle("INFORMATION");
					alert.setHeaderText("INFORMATION");
					alert.setContentText("Atleta2 já cadastrado, dados carregados do Banco!");
					alert.showAndWait();
					carregado2 = true;

				}
			}
			//TODO
			if (!carregado) {
//				at1 = AtletaParser.cadastroAtleta(at1);
			}
			if (!carregado2) {
//				at2 = AtletaParser.cadastroAtleta(at2);
			}

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

			int pontosRank = 0;
			//TODO
			PersisteRankingIF pRanking = (PersisteRankingIF) Naming.lookup(Connection.getUrl() + "ranking");

			if (carregado) {
				for (Ranking r : pRanking.recuperaRankings()) {
					if (r.getAtleta1().getId() == at1.getId()) {
						pontosRank = pontosRank + r.getPontosrank();
					}
				}
			}
			if (carregado2) {
				for (Ranking r : pRanking.recuperaRankings()) {
					if (r.getAtleta1().getId() == at2.getId()) {
						pontosRank = pontosRank + r.getPontosrank();
					}
				}
			}

			dupla.setPontosRank(pontosRank);

			dupla.setNome(at1.getNome() + "/" + at2.getNome());
			dupla.setCategoria(categoriaDupla);

			String impedimento = selectImpedimento.getText();

			if (!impedimento.equalsIgnoreCase("Escolher Impedimento") && impedimento != null) {
				dupla.setImpedimento(impedimento);
			}

			//TODO
//			dupla = DuplaParser.cadastroDupla(dupla);


			Hibernate.initialize(at1);
			Hibernate.initialize(at2);

			Set<Dupla> listDuplas = new HashSet<Dupla>();
			listDuplas.add(dupla);

			if (carregado) {
				for (Dupla duplasss : at1.getDuplas()) {
					listDuplas.add(duplasss);
				}
			}
			if (carregado2) {
				for (Dupla duplasss : at2.getDuplas()) {
					listDuplas.add(duplasss);
				}
			}

			at1.setDuplas(listDuplas);
			at2.setDuplas(listDuplas);

			//TODO
//			at1 = AtletaParser.atualizaAtleta(at1);
//			at2 = AtletaParser.atualizaAtleta(at2);

//			PersisteCircuitoIF pCircuito = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl() + "circuito");
			ArrayList<Circuito> c = CircuitoParser.createCircuitos();

			Circuito cct = c.get(0);

			if (!carregado) {
				Ranking ranking = new Ranking();
				ranking.setRankingPK(new RankingPK(at1.getId(), cct.getId()));
				ranking.setAtleta1(at1);
				ranking.setPontosrank(0);
				ranking.setCategoria(categoriaDupla);
				ranking.setCircuito1(c.get(0));
				ranking = pRanking.cadastroRanking(ranking);

			}
			if (!carregado2) {
				Ranking ranking2 = new Ranking();
				ranking2.setRankingPK(new RankingPK(at2.getId(), cct.getId()));
				ranking2.setAtleta1(at2);
				ranking2.setPontosrank(0);
				ranking2.setCategoria(categoriaDupla);
				ranking2.setCircuito1(c.get(0));
				ranking2 = pRanking.cadastroRanking(ranking2);

			}

//			PersisteEtapaIF pe = (PersisteEtapaIF) Naming.lookup(Connection.getUrl() + "etapa");
			ArrayList<Etapa> e = EtapaParser.createEtapas();

			Inscricao inscricao = new Inscricao();
			Etapa etp = e.get(0);
			inscricao.setInscricaoPK(new InscricaoPK(dupla.getId(), etp.getId()));
			inscricao.setDupla1(dupla);
			Date horaInsc = new Date();
			inscricao.setEtapa1(e.get(0));
			inscricao.setHorainsc(horaInsc);

			//TODO
			PersisteInscricaoIF pInscricao = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");

			if (pInscricao.cadastroInscricao(inscricao) != null) {
				System.out.println("dupla " + dupla.getNome() + " cadastrada com sucesso");
			}

			alert.setTitle("INFORMATION");
			alert.setHeaderText("SUCESSO");
			alert.setContentText("Dupla cadastrada com Sucesso!");
			alert.showAndWait();
			CadastrarDupla.sair();

			atletas.clear();

		} catch (Exception e) {

			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("ERRO");
			alertErro.setHeaderText("ERRO");
			alertErro.setContentText("ERRO inesperado, tente novamente!");
			alertErro.showAndWait();
			e.printStackTrace();
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