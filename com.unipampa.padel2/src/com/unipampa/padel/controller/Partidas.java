//package com.unipampa.padel.controller;
//
//import java.net.MalformedURLException;
//import java.rmi.Naming;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//import javax.swing.JOptionPane;
//
//import com.unipampa.padel.model.Categoria;
//import com.unipampa.padel.model.Chave;
//import com.unipampa.padel.model.Dupla;
//import com.unipampa.padel.model.Partida;
//import com.unipampa.padel.model.Quadra;
//
//import connection.Connection;
//import interfaces.PersisteCategoriaIF;
//import interfaces.PersisteChaveIF;
//import interfaces.PersistePartidaIF;
//
//public class Partidas {
//
//	private ArrayList<Chave> todasChaves;
//
//	private ArrayList<Partida> partidasProntas;
//
//	public ArrayList<Partida> getPartidasProntas() {
//		return partidasProntas;
//	}
//
//	public void setPartidasProntas(ArrayList<Partida> partidasProntas) {
//		this.partidasProntas = partidasProntas;
//	}
//
//	private ArrayList<HashMap<String, ArrayList<Dupla>>> mapa;
//	private ArrayList<Date> datas;
//	private HashMap<String, ArrayList<Partida>> partidas;
//	private HashMap<String, ArrayList<Chave>> chavesCat;
//
//	private PersisteChaveIF pchave;
//	private PersisteCategoriaIF pC;
//	private PersistePartidaIF ptida;
//
//	public static final int MAX_HORARIOS_ETAPA = 53;
//	public int maxJogosEtapa;
//	private int numeroQuadras = 3;
//	private int numeroPartidasAtual;
//	private int numChave = 0, numChave2 = 0, numChave3 = 0;
//	private String indexCategoriaMaisChave = "", indexCategoriaMaisChave2 = "", indexCategoriaMaisChave3 = "";
//
//	public String getIndexCategoriaMaisChave() {
//		return indexCategoriaMaisChave;
//	}
//
//	public void setIndexCategoriaMaisChave(String indexCategoriaMaisChave) {
//		this.indexCategoriaMaisChave = indexCategoriaMaisChave;
//	}
//
//	public String getIndexCategoriaMaisChave2() {
//		return indexCategoriaMaisChave2;
//	}
//
//	public void setIndexCategoriaMaisChave2(String indexCategoriaMaisChave2) {
//		this.indexCategoriaMaisChave2 = indexCategoriaMaisChave2;
//	}
//
//	public String getIndexCategoriaMaisChave3() {
//		return indexCategoriaMaisChave3;
//	}
//
//	public void setIndexCategoriaMaisChave3(String indexCategoriaMaisChave3) {
//		this.indexCategoriaMaisChave3 = indexCategoriaMaisChave3;
//	}
//
//	public int getNumChave() {
//		return numChave;
//	}
//
//	public void setNumChave(int numChave) {
//		this.numChave = numChave;
//	}
//
//	public int getNumChave2() {
//		return numChave2;
//	}
//
//	public void setNumChave2(int numChave2) {
//		this.numChave2 = numChave2;
//	}
//
//	public int getNumChave3() {
//		return numChave3;
//	}
//
//	public void setNumChave3(int numChave3) {
//		this.numChave3 = numChave3;
//	}
//
//	public int getNumeroPartidasAtual() {
//		return numeroPartidasAtual;
//	}
//
//	public void setNumeroPartidasAtual(int numeroPartidasAtual) {
//		this.numeroPartidasAtual = numeroPartidasAtual;
//	}
//
//	public static void main(String args[]) throws Exception {
//
//		Partidas p = new Partidas();
//		p.geraGradePartidas();
//
//	}
//
//	public void salvaPartidas() {
//		for (Partida p : partidasProntas) {
//			try {
//				ptida.cadastroPartida(p);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public void geraGradePartidas() throws Exception {
//
//		populaMapChavePorCategorias();
//
//		int numeroChaves = todasChaves.size();
//
//		this.numeroPartidasAtual = (numeroChaves * 5) + 4;
//
//		System.out.println("Número Total de Partidas no circuito " + this.numeroPartidasAtual);
//
//		if (this.numeroPartidasAtual > getMaxJogosEtapa()) {
//
//			alertReducaoChavesParaPartidas();
//
//		}
//
//		geraPartidasFaseGrupo();
//		geraPartidasPosFaseGrupo();
//
//	}
//
//	private void alertReducaoChavesParaPartidas() throws RemoteException, Exception {
//		int numeroChaves;
//		int numeroChavesReduzir = numeroChavesReduzir();
//
//		if (ControllerPrevisaoPartidas.alertConfimarion(getMaxJogosEtapa(), numeroChavesReduzir)) {
//
//			numeroChaves = todasChaves.size() - numeroChavesReduzir;
//
//			this.numeroPartidasAtual = (numeroChaves * 5) + 4;
//			setNumeroPartidasAtual(numeroPartidasAtual);
//			ControllerPrevisaoPartidas.alertFeedback(redutorChaves(numeroChavesReduzir));
//
//		}
//	}
//
//	private void populaMapChavePorCategorias() throws NotBoundException, MalformedURLException, RemoteException {
//		pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
//		this.todasChaves = pchave.recuperaChaves();
//
//		pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
//		ArrayList<Categoria> cats = pC.recuperaCategorias();
//
//		this.chavesCat = new LinkedHashMap<>();
//
//		for (Categoria cat : cats) {
//
//			ArrayList<Chave> chaves = new ArrayList<>();
//			for (Chave chave : todasChaves) {
//				Dupla dupla = chave.getDuplaList().get(0);
//				if (cat.getNome().equals(dupla.getCategoria().getNome())) {
//					chaves.add(chave);
//				}
//			}
//			Collections.sort(chaves);
//			chavesCat.put(cat.getNome(), chaves);
//
//		}
//	}
//
//	public void geraPartidasPosFaseGrupo() throws Exception {
//
//		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesCat.entrySet()) {
//			ArrayList<Chave> cs = entrada.getValue();
//			Partida p = new Partida();
//			ArrayList<Partida> ps = new ArrayList<>();
//			switch (cs.size()) {
//
//			case 2:
//				partidasComDuasChaves(entrada, p, ps);
//				break;
//			case 3:
//				partidasComTresChaves(entrada, p, ps);
//				break;
//			case 4:
//				partidasComQuatroChaves(entrada, p, ps);
//				break;
//			case 5:
//				partidasComCincoChaves(entrada, p, ps);
//				break;
//			case 6:
//				partidasComSeisChaves(entrada, p, ps);
//				break;
//			case 7:
//				partidasComSeteChaves(entrada, p, ps);
//				break;
//			case 8:
//				partidasComOitoChaves(entrada, p, ps);
//				break;
//			default:
//				break;
//			}
//		}
//
//		alocaPartidasHorarios();
//
//	}
//
//	private void partidasComDuasChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("S1 - 1A| x |2B");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("S2 - 2A| x |1B");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Semi", ps);
//
//		adicionaFinaisNoMap(entrada);
//
//	}
//
//	private void partidasComTresChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("Q1 - 2B| x |2C");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - 2A| x |1C");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("S1 - 1A| x |Q1");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("S2 - Q2| x |1B");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Semi", ps);
//
//		adicionaFinaisNoMap(entrada);
//
//	}
//
//	private void partidasComQuatroChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p,
//			ArrayList<Partida> ps) {
//
//		p.setNome("Q1 - 1A| x |2B");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - 2C| x |1D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q3 - 1C| x |2D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q4 - 2A| x |1B");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		adicionaSemiNoMap(entrada);
//
//		adicionaFinaisNoMap(entrada);
//	}
//
//	private void partidasComCincoChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("O1 - 2B| x |2C");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O2 - 2D| x |2A");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);
//
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("Q1 - 1A| x |O1");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - 2E| x |1D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q3 - 1C| x |1E");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q4 - O2| x |1B");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		adicionaSemiNoMap(entrada);
//
//		adicionaFinaisNoMap(entrada);
//	}
//
//	private void partidasComSeisChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("O1 - 2B| x |2C");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O2 - 2E| x |1F");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O3 - 1E| x |2F");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O4 - 2A| x |2D");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);
//
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("Q1 - 1A| x |O1");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - O2| x |1D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q3 - 1C| x |O3");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q4 - O4| x |1B");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		adicionaSemiNoMap(entrada);
//
//		adicionaFinaisNoMap(entrada);
//	}
//
//	private void partidasComSeteChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("O1 - 2F| x |2G");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O2 - 2C| x |1E");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O3 - 1D| x |2B");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O4 - 1C| x |2A");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O5 - 1F| x |2D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O6 - 2E| x |1G");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);
//
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("Q1 - 1A| x |O1");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - O2| x |O3");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q3 - O4| x |O5");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q4 - 1B| x |O6");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		adicionaSemiNoMap(entrada);
//
//		adicionaFinaisNoMap(entrada);
//	}
//
//	private void partidasComOitoChaves(Map.Entry<String, ArrayList<Chave>> entrada, Partida p, ArrayList<Partida> ps) {
//
//		p.setNome("O1 - 1A| x |2B");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O2 - 1H| x |2G");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O3 - 1E| x |2F");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O4 - 1D| x |2C");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O5 - 1C| x |2D");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O6 - 1F| x |2E");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O7 - 1G| x |2H");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("O8 - 1B| x |2A");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);
//
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("Q1 - O1| x |O2");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q2 - O3| x |O4");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q3 - O5| x |O6");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("Q4 - O7| x |O8");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);
//
//		adicionaSemiNoMap(entrada);
//
//		adicionaFinaisNoMap(entrada);
//	}
//
//	private void adicionaSemiNoMap(Map.Entry<String, ArrayList<Chave>> entrada) {
//		Partida p;
//		ArrayList<Partida> ps;
//		ps = new ArrayList<>();
//		p = new Partida();
//		p.setNome("S1 - Q1| x |Q2");
//		ps.add(p);
//		p = new Partida();
//		p.setNome("S2 - Q3| x |Q4");
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Semi", ps);
//	}
//
//	private void adicionaFinaisNoMap(Map.Entry<String, ArrayList<Chave>> entrada) {
//		Partida p;
//		ArrayList<Partida> ps;
//		p = new Partida();
//		p.setNome("F - S1| x |S2");
//		ps = new ArrayList<>();
//		ps.add(p);
//		partidas.put("Categoria " + entrada.getKey() + " Final", ps);
//	}
//
//	public void alocaGradeHorarios() {
//
//		// 0 seg - 1 terç - 2 quart - 3 quint - 4 sext - 5 sab - 6 domin
//		int hora = 18;
//		int min = 0;
//		int dia = 26;
//
//		int qtdHr = 7;
//		int hrInicial = 18;
//
//		datas = new ArrayList<>();
//		// For de Quinta, Sexta, Sábado e Domingo
//		for (int d = 0; d < 4; d++) {
//
//			if (datas.size() == 0) {
//				geraDias(dia, hora, min, hrInicial, qtdHr);
//			} else {
//
//				if (d < 2) {
//					geraDias(dia, hora, min, hrInicial, qtdHr);
//				} else if (d >= 2) {
//					qtdHr = 19;
//					hrInicial = 8;
//					geraDias(dia, hora, min, hrInicial, qtdHr);
//				}
//			}
//			dia++;
//		}
//		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//
//	}
//
//	public void geraDias(int dia, int hora, int min, int hrInicial, int qtdHr) {
//
//		for (int h = 0; h < qtdHr; h++) {
//
//			if (h == 0) {
//				hora = hrInicial;
//			}
//
//			Date data = new Date(119, 8, dia, hora, min, 0);
//			datas.add(data);
//
//			min = min + 50;
//			if (min >= 60) {
//				hora++;
//				min = min - 60;
//			}
//		}
//
//	}
//
//	public void geraPartidasFaseGrupo() throws Exception {
//
//		this.partidas = new LinkedHashMap<>();
//
//		ptida = (PersistePartidaIF) Naming.lookup(Connection.getUrl() + "partida");
//
//		for (int i = 0; i < this.todasChaves.size(); i++) {
//			Chave chave = this.todasChaves.get(i);
//			ArrayList<Dupla> duplas = new ArrayList<Dupla>();
//			ArrayList<Partida> ps = new ArrayList<>();
//
//			Partida p = new Partida();
//			duplas.add(chave.getDuplaList().get(0));
//			duplas.add(chave.getDuplaList().get(1));
//			p.setDuplaList(duplas);
//			p.setNome(chave.getDuplaList().get(0).getNome() + "| x |" + chave.getDuplaList().get(1).getNome());
//			duplas.clear();
//			ps.add(p);
//
//			Partida p1 = new Partida();
//			duplas.add(chave.getDuplaList().get(1));
//			duplas.add(chave.getDuplaList().get(2));
//			p1.setDuplaList(duplas);
//			p1.setNome(chave.getDuplaList().get(1).getNome() + "| x |" + chave.getDuplaList().get(2).getNome());
//			duplas.clear();
//			ps.add(p1);
//
//			Partida p2 = new Partida();
//			duplas.add(chave.getDuplaList().get(0));
//			duplas.add(chave.getDuplaList().get(2));
//			p2.setDuplaList(duplas);
//			p2.setNome(chave.getDuplaList().get(0).getNome() + "| x |" + chave.getDuplaList().get(2).getNome());
//			duplas.clear();
//			ps.add(p2);
//
//			partidas.put("Categoria " + chave.getDuplaList().get(0).getCategoria().getNome()
//					+ " Fase de Grupo da Chave " + chave.getNome(), ps);
//
//		}
//
//	}
//
//	public void alocaPartidasHorarios() {
//
//		ArrayList<Partida> faseGps = new ArrayList<>();
//		ArrayList<Partida> oitavas = new ArrayList<>();
//		ArrayList<Partida> quartas = new ArrayList<>();
//		ArrayList<Partida> semis = new ArrayList<>();
//		ArrayList<Partida> finais = new ArrayList<>();
//
//		alocaGradeHorarios();
//
//		separaFaseGruposEmArrays(faseGps, oitavas, quartas, semis, finais);
//
//		int index_datas = 0;
//		int quadra = 0;
//
//		ArrayList<Quadra> quadras = criaQuadras();
//
//		faseGps = alocaPartidasHorariosQuadras(index_datas, quadra, quadras, faseGps);
//
//		oitavas = alocaPartidasHorariosQuadras(index_datas, quadra, quadras, oitavas);
//
//		quartas = alocaPartidasHorariosQuadras(index_datas, quadra, quadras, quartas);
//
//		semis = alocaPartidasHorariosQuadras(index_datas, quadra, quadras, semis);
//
//		finais = alocaPartidasHorariosQuadras(index_datas, quadra, quadras, finais);
//
//		this.partidasProntas = new ArrayList<>();
//		this.partidasProntas.addAll(faseGps);
//		this.partidasProntas.addAll(oitavas);
//		this.partidasProntas.addAll(quartas);
//		this.partidasProntas.addAll(semis);
//		this.partidasProntas.addAll(finais);
//
//		for (Partida p : partidasProntas) {
//			System.out.println(p.getDataHora() + " | " + p.getNome() + " | " + "Quadra " + p.getQuadra().getId());
//		}
//
//	}
//
//	private ArrayList<Quadra> criaQuadras() {
//		Quadra quadra1 = new Quadra();
//		quadra1.setId(1);
//		Quadra quadra2 = new Quadra();
//		quadra2.setId(2);
//		Quadra quadra3 = new Quadra();
//		quadra3.setId(3);
//		ArrayList<Quadra> quadras = new ArrayList<>();
//		quadras.add(quadra1);
//		quadras.add(quadra2);
//		quadras.add(quadra3);
//		return quadras;
//	}
//
//	private void separaFaseGruposEmArrays(ArrayList<Partida> faseGps, ArrayList<Partida> oitavas,
//			ArrayList<Partida> quartas, ArrayList<Partida> semis, ArrayList<Partida> finais) {
//		for (Map.Entry<String, ArrayList<Partida>> entrada : partidas.entrySet()) {
//			ArrayList<Partida> p = entrada.getValue();
//
//			if (entrada.getKey().contains("Grupo")) {
//				System.out.println("Grupo =" + entrada.getKey());
//				for (Partida part : p) {
//					faseGps.add(part);
//				}
//			} else if (entrada.getKey().contains("Oitavas")) {
//				for (Partida part : p) {
//					oitavas.add(part);
//				}
//			} else if (entrada.getKey().contains("Quartas")) {
//				for (Partida part : p) {
//					quartas.add(part);
//				}
//			} else if (entrada.getKey().contains("Semi")) {
//				for (Partida part : p) {
//					semis.add(part);
//				}
//			} else if (entrada.getKey().contains("Final")) {
//				for (Partida part : p) {
//					finais.add(part);
//				}
//			}
//		}
//	}
//
//	private ArrayList<Partida> alocaPartidasHorariosFaseGrupo(int index_datas, int quadra, ArrayList<Quadra> quadras,
//			ArrayList<Partida> faseGps) {
//
//		for (Partida p : faseGps) {
//
//			p.setDataHora(datas.get(index_datas));
//			p.setQuadra(quadras.get(quadra));
//
//			if (quadra == 2) {
//				index_datas++;
//				quadra = -1;
//			}
//			quadra++;
//		}
//
//		return faseGps;
//
//	}
//
//	private ArrayList<Partida> alocaPartidasHorariosQuadras(int index_datas, int quadra, ArrayList<Quadra> quadras,
//			ArrayList<Partida> etapas) {
//
//		for (Partida p : etapas) {
//
//			p.setDataHora(datas.get(index_datas));
//			p.setQuadra(quadras.get(quadra));
//
//			if (quadra == 2) {
//				index_datas++;
//				quadra = -1;
//			}
//			quadra++;
//		}
//
//		return etapas;
//
//	}
//
//	public int numeroChavesReduzir() {
//
//		int numeroPartidas = getNumeroPartidasAtual(), numeroPartidasTotal = getNumeroPartidasAtual(), redutor = 0,
//				numeroReduzir = 0;
//
//		for (redutor = 0; numeroPartidas > getMaxJogosEtapa(); redutor++) {
//
//			numeroReduzir = redutor;
//			numeroPartidas = numeroPartidasTotal - ((redutor * 5) + 4);
//		}
//
//		return numeroReduzir;
//	}
//
//	public String redutorChaves(int numeroReduzir) throws RemoteException {
//
//		HashMap<String, ArrayList<Chave>> chavesEliminadas = new LinkedHashMap();
//
//		boolean erro = false;
//
//		selecionaChavesMaiores();
//
//		ArrayList<ArrayList<Chave>> chavesReduzir = eliminaChaves(numeroReduzir);
//
//		ArrayList<Chave> eliminadas = new ArrayList<>();
//		eliminadas.addAll(chavesReduzir.get(0));
//		eliminadas.addAll(chavesReduzir.get(1));
//		eliminadas.addAll(chavesReduzir.get(2));
//
//		try {
//			pchave.removeChaves(eliminadas);
//			ControllerPrevisaoPartidas.alertDelete();
//		}catch(Exception e) {
//			e.printStackTrace();
//			ControllerPrevisaoPartidas.alertNaoDelete();
//		}
//
//		chavesEliminadas.put(indexCategoriaMaisChave, chavesReduzir.get(0));
//		chavesEliminadas.put(indexCategoriaMaisChave2, chavesReduzir.get(1));
//		chavesEliminadas.put(indexCategoriaMaisChave3, chavesReduzir.get(2));
//
//		String chavesReduzidas = chavesReduzidas(chavesEliminadas);
//
//		return chavesReduzidas;
//
//	}
//
//	private String chavesReduzidas(HashMap<String, ArrayList<Chave>> chavesEliminadas) {
//		String chavesReduzidas = "";
//		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesEliminadas.entrySet()) {
//
//			ArrayList<Chave> chaves = entrada.getValue();
//
//			chavesReduzidas = chavesReduzidas + "\nCategoria " + entrada.getKey() + "\n";
//
//			for (Chave cs : chaves) {
//				chavesReduzidas = chavesReduzidas + "\t Chaves: " + cs.getNome() + "\n";
//			}
//
//		}
//		return chavesReduzidas;
//	}
//
//	private ArrayList<ArrayList<Chave>> eliminaChaves(int numeroReduzir) {
//		int divisao = numeroReduzir / 3;
//
//		ArrayList<Chave> chaves1 = new ArrayList<>();
//		ArrayList<Chave> chaves2 = new ArrayList<>();
//		ArrayList<Chave> chaves3 = new ArrayList<>();
//
//		for (int i = 1; i <= divisao; i++) {
//			chaves1.add(chavesCat.get(indexCategoriaMaisChave).get(numChave - i));
//			chavesCat.get(indexCategoriaMaisChave).remove(numChave - i);
//
//			chaves2.add(chavesCat.get(indexCategoriaMaisChave2).get(numChave2 - i));
//			chavesCat.get(indexCategoriaMaisChave2).remove(numChave2 - i);
//
//			chaves3.add(chavesCat.get(indexCategoriaMaisChave3).get(numChave3 - i));
//			chavesCat.get(indexCategoriaMaisChave3).remove(numChave3 - i);
//		}
//		ArrayList<ArrayList<Chave>> chaves = new ArrayList<>();
//		chaves.add(chaves1);
//		chaves.add(chaves2);
//		chaves.add(chaves3);
//		return chaves;
//	}
//
//	private void selecionaChavesMaiores() {
//		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesCat.entrySet()) {
//
//			ArrayList<Chave> chaves = entrada.getValue();
//			if (numChave < chaves.size()) {
//
//				if (numChave2 <= numChave) {
//
//					if (numChave3 <= numChave2) {
//						indexCategoriaMaisChave3 = indexCategoriaMaisChave2;
//						numChave3 = numChave2;
//					}
//
//					indexCategoriaMaisChave2 = indexCategoriaMaisChave;
//					numChave2 = numChave;
//				}
//				indexCategoriaMaisChave = entrada.getKey();
//				numChave = chaves.size();
//
//			}
//		}
//	}
//
//	public int getMaxJogosEtapa() {
//
//		this.maxJogosEtapa = MAX_HORARIOS_ETAPA * numeroQuadras;
//		return this.maxJogosEtapa;
//
//	}
//
//	public ArrayList<HashMap<String, ArrayList<Dupla>>> getMapa() {
//		return mapa;
//	}
//
//	public void setMapa(ArrayList<HashMap<String, ArrayList<Dupla>>> mapa) {
//		this.mapa = mapa;
//	}
//}
