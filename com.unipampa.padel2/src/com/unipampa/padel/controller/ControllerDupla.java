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
import com.unipampa.padel.persistence.PersisteAtleta;
import com.unipampa.padel.persistence.PersisteCategoria;
import com.unipampa.padel.view.CadastrarDupla;

import connection.Connection;
import interfaces.PersisteAtletaIF;
import interfaces.PersisteCategoriaIF;
import interfaces.PersisteCircuitoIF;
import interfaces.PersisteEtapaIF;
import interfaces.PersisteInscricaoIF;
import interfaces.PersistePartidaIF;
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

	private ArrayList<Categoria> cats;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mascaraCPF(cpfAtleta1);
		mascaraCPF(cpfAtleta2);
		mascaraEmail(emailAtleta1);
		mascaraEmail(emailAtleta2);
		mascaraTelefone(numCelAtleta1);
		mascaraTelefone(numCelAtleta2);

//		-- Evento para quando escolher o item do menu ele atualizar a tabela--
		EventHandler<ActionEvent> eventoSelectImpedimento = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				selectImpedimento.setText(((MenuItem) e.getSource()).getText());
			}
		};

		MenuItem I1 = new MenuItem("Sábado Manhã");
		MenuItem I2 = new MenuItem("Sábado Tarde");
		MenuItem I3 = new MenuItem("Sábado Noite");
		I1.setOnAction(eventoSelectImpedimento);
		I2.setOnAction(eventoSelectImpedimento);
		I3.setOnAction(eventoSelectImpedimento);
		selectImpedimento.getItems().add(I1);
		selectImpedimento.getItems().add(I2);
		selectImpedimento.getItems().add(I3);

//		-- Evento para quando escolher o item do menu ele atualizar a tabela--
		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Categoria c = new Categoria();
				c.setNome(((MenuItem) e.getSource()).getText());
				catDupla.setText(c.getNome());
			}
		};

//        -- adicionando os itens de categoria nos botoes e adicionando o evento --
		try {
			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");

			cats = pC.recuperaCategorias();
			for (Categoria c : cats) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				catDupla.getItems().add(m);
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void clickBotaoCancelar(ActionEvent event) {

		CadastrarDupla.sair();

	}

	public void clickBotaoInscrever(ActionEvent event)
			throws RemoteException, MalformedURLException, NotBoundException {
		Alert alert = new Alert(AlertType.INFORMATION);
		boolean carregado = false, carregado2 = false;

		if (nomeAtleta1.getText().equals("") || nomeAtleta2.getText().equals("") || nomeAtleta1.getText() == null
				|| nomeAtleta2.getText() == null) {
			Alert alertErro = new Alert(AlertType.ERROR);
			alertErro.setTitle("ERRO");
			alertErro.setHeaderText("Cadastro Invï¿½lido");
			alertErro.setContentText("Campos obrigatï¿½rios nï¿½o foram preenchidos!");
			alertErro.showAndWait();
		} else {

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

				for (Atleta att : pa.recuperaAtleta()) {
					if (att.getCpf().equals(at1.getCpf())) {
//						at1.setId(att.getId());
//						at1.setNome(att.getNome());
//						at1.setNumCel(att.getNumCel());
//						at1.setEmail(att.getEmail());
//						at1.setDuplas(att.getDuplas());
//						at1.setRankList(att.getRankList());
						at1 = att;

						alert.setTitle("INFORMATION");
						alert.setHeaderText("INFORMATION");
						alert.setContentText("Atleta1 já cadastrado, dados carregados do Banco!");
						alert.showAndWait();
						carregado = true;
					}if (att.getCpf().equals(at2.getCpf())) {
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
				if(!carregado) {
					at1 = pa.cadastroAtleta(at1);
				}if(!carregado2) {
					at2 = pa.cadastroAtleta(at2);
				}
				
				Categoria categoriaDupla = new Categoria();
				for (Categoria c : cats) {
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

				dupla = pa.cadastroDupla(dupla);
//				dupla = pa.atualizaDupla(dupla);
				

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
			
				at1 = pa.atualizaAtleta(at1);
				at2 = pa.atualizaAtleta(at2);

				PersisteCircuitoIF pCircuito = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl() + "circuito");
				ArrayList<Circuito> c = pCircuito.recuperaCircuitos();
				
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

				PersisteEtapaIF pe = (PersisteEtapaIF) Naming.lookup(Connection.getUrl() + "etapa");
				ArrayList<Etapa> e = pe.recuperaEtapas();

				Inscricao inscricao = new Inscricao();
				Etapa etp = e.get(0);
				inscricao.setInscricaoPK(new InscricaoPK(dupla.getId(), etp.getId()));
				inscricao.setDupla1(dupla);
				Date horaInsc = new Date();
				inscricao.setEtapa1(e.get(0));
				inscricao.setHorainsc(horaInsc);

				PersisteInscricaoIF pInscricao = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");

				if (pInscricao.cadastroInscricao(inscricao) != null) {
					System.out.println("dupla " + dupla.getNome() + " cadastrada com sucesso");
				} // Mudei a verificação para not null(Boolean tava dando erro)

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

//			if (pA.cadastroAtleta(at1) && pA.cadastroAtleta(at2)) {
//				if (pA.cadastroDupla(dupla)) {
//					Alert alert = new Alert(AlertType.INFORMATION);
//					alert.setTitle("INFORMATION");
//					alert.setHeaderText("SUCESSO");
//					alert.setContentText("Dupla cadastrada com Sucesso!");
//					alert.showAndWait();
//					CadastrarDupla.sair();
//				} else {
//					Alert alertErro = new Alert(AlertType.ERROR);
//					alertErro.setTitle("ERRO");
//					alertErro.setHeaderText("ERRO");
//					alertErro.setContentText("ERRO inesperado, tente novamente!");
//					alertErro.showAndWait();
//				}
//			} else {
//
//			}
		}

	}

	public static void mascaraCPF(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText().substring(0, 7));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 12) {
					textField.setText(textField.getText().substring(0, 11));
					textField.positionCaret(textField.getText().length());
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 7) {
					textField.setText(textField.getText() + ".");
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 11) {
					textField.setText(textField.getText() + "-");
					textField.positionCaret(textField.getText().length());
				}

			}
		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d.-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d.-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

	public static void mascaraEmail(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz._-@"
					.contains(event.getCharacter()) == false) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().contains("@")) {
				event.consume();
			}

			if ("@".equals(event.getCharacter()) && textField.getText().length() == 0) {
				event.consume();
			}
		});

	}

	public static void mascaraTelefone(TextField textField) {

		textField.setOnKeyTyped((KeyEvent event) -> {
			if ("0123456789".contains(event.getCharacter()) == false) {
				event.consume();
			}

			if (event.getCharacter().trim().length() == 0) { // apagando

				if (textField.getText().length() == 10 && textField.getText().substring(9, 10).equals("-")) {
					textField.setText(textField.getText().substring(0, 9));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 4) {
					textField.setText(textField.getText().substring(0, 3));
					textField.positionCaret(textField.getText().length());
				}
				if (textField.getText().length() == 1) {
					textField.setText("");
				}

			} else { // escrevendo

				if (textField.getText().length() == 14)
					event.consume();

				if (textField.getText().length() == 0) {
					textField.setText("(" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 3) {
					textField.setText(textField.getText() + ")" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 8) {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 9 && textField.getText().substring(8, 9) != "-") {
					textField.setText(textField.getText() + "-" + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}
				if (textField.getText().length() == 13 && textField.getText().substring(8, 9).equals("-")) {
					textField.setText(textField.getText().substring(0, 8) + textField.getText().substring(9, 10) + "-"
							+ textField.getText().substring(10, 13) + event.getCharacter());
					textField.positionCaret(textField.getText().length());
					event.consume();
				}

			}

		});

		textField.setOnKeyReleased((KeyEvent evt) -> {

			if (!textField.getText().matches("\\d()-*")) {
				textField.setText(textField.getText().replaceAll("[^\\d()-]", ""));
				textField.positionCaret(textField.getText().length());
			}
		});

	}

}