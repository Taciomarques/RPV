package com.unipampa.padel.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Inscricao;
import com.unipampa.padel.parser.CategoriaParser;
import com.unipampa.padel.parser.DuplaParser;
import com.unipampa.padel.parser.InscricaoParser;
import com.unipampa.padel.view.ViewInscritos;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class ControllerInscritos implements Initializable {

	@FXML
	private Button botaoVoltar;

	@FXML
	private TableView tabela_inscritos;

	@FXML
	private TableColumn<Dupla, String> data_insc;

	@FXML
	private TableColumn<Dupla, String> nome;

	@FXML
	private TableColumn<Dupla, String> situ;

	@FXML
	private TableColumn<Dupla, String> pontos;

	@FXML
	private MenuButton catList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		data_insc.setCellValueFactory(new PropertyValueFactory<>("horaInscricao"));
		nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		situ.setCellValueFactory(new PropertyValueFactory<>("suplente"));
		pontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));
		
		EventHandler<ActionEvent> eventoSelectCat = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				Categoria c = new Categoria();
				c.setNome(((MenuItem) e.getSource()).getText());
				catList.setText(c.getNome());
				populaItensInscritos(c);
			}
		};
		addItensCategoria(eventoSelectCat);
		Categoria c = new Categoria();
		c.setNome(catList.getAccessibleText());
		populaItensInscritos(c);
	}

	public void addItensCategoria(EventHandler<ActionEvent> eventoSelectCat) {
		try {
//			PersisteCategoriaIF pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
			for (Categoria c : CategoriaParser.createCategoria()) {
				MenuItem m = new MenuItem(c.getNome());
				m.setOnAction(eventoSelectCat);
				catList.getItems().add(m);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void populaItensInscritos(Categoria c) {
		try {
			tabela_inscritos.setItems(FXCollections.observableArrayList(atualizaSuplencia(c)));
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}
	}

	public void clickBotaoVoltar(ActionEvent event) {
		ViewInscritos.sair();
	}

	public ArrayList<Inscritos> atualizaSuplencia(Categoria cat)
			throws MalformedURLException, RemoteException, NotBoundException {

//		PersisteAtletaIF pA = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");

//		PersisteInscricaoIF pI = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");
		ArrayList<Dupla> duplas = new ArrayList<>();
		ArrayList<Inscricao> inscList = new ArrayList<>();
		ArrayList<Inscritos> inscritos = new ArrayList<>();

		for (Inscricao insc : InscricaoParser.createInscricoes()) {
			for(Dupla d : DuplaParser.createDupla()) {
				if(d.getId() == insc.getDupla1().getId()) {
					for(Categoria c : CategoriaParser.createCategoria()) {
						if (d.getCategoria().getId() == c.getId()) {
							if(c.getNome().equals(cat.getNome())) {
								inscList.add(insc);
							}
						}
					}
				}
			}
		}
		for (Inscricao insc : inscList) {
			for (Inscricao insc2 : inscList) {
				if (insc.getHorainsc().compareTo(insc2.getHorainsc()) < 0) {
					Inscricao aux = new Inscricao();

					aux.setDupla1(insc.getDupla1());
					aux.setEtapa1(insc.getEtapa1());
					aux.setHorainsc(insc.getHorainsc());
					aux.setInscricaoPK(insc.getInscricaoPK());

					insc.setDupla1(insc2.getDupla1());
					insc.setEtapa1(insc2.getEtapa1());
					insc.setHorainsc(insc2.getHorainsc());
					insc.setInscricaoPK(insc2.getInscricaoPK());

					insc2.setDupla1(aux.getDupla1());
					insc2.setEtapa1(aux.getEtapa1());
					insc2.setHorainsc(aux.getHorainsc());
					insc2.setInscricaoPK(aux.getInscricaoPK());
				}
			}
		}
		for (Inscricao insc : inscList) {
			duplas.add(insc.getDupla1());
		}
		duplasSuplentes(duplas);
		return populaDuplas(inscList, inscritos);
	}

	public ArrayList<Inscritos> populaDuplas(ArrayList<Inscricao> listInscritos, ArrayList<Inscritos> inscritos) {
		for (Inscricao i : listInscritos) {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dataFormatada = dateFormat.format(i.getHorainsc());
			Inscritos insc = new Inscritos(i.getDupla1().getNome(), i.getDupla1().isSuplente(), dataFormatada,
					i.getDupla1().getCategoria(), i.getDupla1().getPontosRank());
			inscritos.add(insc);
		}
		return inscritos;
	}

	public void duplasSuplentes(ArrayList<Dupla> duplas) throws RemoteException {
		int resto = duplas.size() % 3;
		for (int i = 0; i < duplas.size() - resto; i++) {
			duplas.get(i).setSuplente(false);
			
			DuplaParser.atualizaDupla(duplas.get(i));
		}
	}

	public static class Inscritos {

		private final SimpleStringProperty nome;
		private final SimpleStringProperty suplente;
		private final SimpleStringProperty horaInscricao;
		private final ObjectProperty<Categoria> categoria;
		private final SimpleStringProperty pontos;

		private Inscritos(String nome, boolean suplente, String horaInscricao, Categoria categoria, int pontos) {
			this.nome = new SimpleStringProperty(nome);
			if (suplente) {
				this.suplente = new SimpleStringProperty("Suplente");

			} else {
				this.suplente = new SimpleStringProperty("Inscrito");
			}
			this.horaInscricao = new SimpleStringProperty(horaInscricao);
			this.categoria = new SimpleObjectProperty<Categoria>(categoria);
			this.pontos = new SimpleStringProperty(Integer.toString(pontos));
		}
		public String getNome() {
			return nome.get();
		}
		public void setNome(String nome) {
			this.nome.set(nome);
		}
		public String getSuplente() {
			return suplente.get();
		}
		public void setSuplente(String suplente) {
			this.suplente.set(suplente);
		}
		public String getHoraInscricao() {
			return horaInscricao.get();
		}
		public void setHoraInscricao(String horaInicio) {
			this.horaInscricao.set(horaInicio);
		}
		public String getPontos() {
			return pontos.get();
		}
		public void setPontos(String pontos) {
			this.pontos.set(pontos);
		}
	}
}