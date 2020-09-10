package com.unipampa.padel.managedBean;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Chave;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Partida;
import com.unipampa.padel.model.Quadra;


public class Partidas {

	private ArrayList<Chave> todasChaves;

	private ArrayList<Partida> partidasProntas;

	public ArrayList<Partida> getPartidasProntas() {
		return partidasProntas;
	}

	public void setPartidasProntas(ArrayList<Partida> partidasProntas) {
		this.partidasProntas = partidasProntas;
	}

	private ArrayList<HashMap<String, ArrayList<Dupla>>> mapa;
	private ArrayList<Date> datas;
	private HashMap<String, ArrayList<Partida>> partidas;
	private HashMap<String, ArrayList<Chave>> chavesCat;

	private ChaveMB pchave;
//	private PersisteCategoriaIF pC;
//	private PersistePartidaIF ptida;

	public static final int MAX_HORARIOS_ETAPA = 53;
	public int maxJogosEtapa;
	private int numeroQuadras = 3;
	private int numeroPartidasAtual;

	public int getNumeroPartidasAtual() {
		return numeroPartidasAtual;
	}

	public void setNumeroPartidasAtual(int numeroPartidasAtual) {
		this.numeroPartidasAtual = numeroPartidasAtual;
	}

	public static void main(String args[]) throws Exception {

		Partidas p = new Partidas();
		p.geraGradePartidas();
	}

	/*
	 * 1 - verificar a quantidade de partidas que dar�o, de todas as chaves de todas
	 * as categorias se for menor que o max suportado por etapas, gera os mapas das
	 * chaves por categoria senra notifica com alert quais categoria possuim muitas
	 * chaves, por conseguinte mandar escolher de qual chave ser eliminadas as
	 * duplas para fechar o limite.
	 * 
	 * 2 - com os mapas das partidas das chaves por categoria criados, alocar as
	 * partidas nos hor�rios das tres quadras disponiveis, verificando os
	 * impedimentos se existentes de cada dupla.
	 * 
	 * 3 - popular uma grade mostrando os jogos e seus hor�rios, escolhendo de um
	 * combobox o dia para visualizar
	 */

//	public void salvaPartidas() {
//		for (Partida p : partidasProntas) {
//			try {
//				ptida.cadastroPartida(p);
//			} catch (RemoteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public void geraGradePartidas() throws Exception {

//		pchave = (PersisteChaveIF) Naming.lookup(Connection.getUrl() + "chave");
		pchave = new ChaveMB();
		this.todasChaves = pchave.retornaChaves();

//		pC = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
		
		ArrayList<Categoria> cats = new ArrayList<>();
		Categoria c = new Categoria();
		c.setId(0);
		c.setNome("1 Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(1);
		c.setNome("2 Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(2);
		c.setNome("3 Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(3);
		c.setNome("4 Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(4);
		c.setNome("5 Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(5);
		c.setNome("Iniciantes Livre");
		cats.add(c);
		c = new Categoria();
		c.setId(6);
		c.setNome("Iniciantes Feminina");
		cats.add(c);
		c = new Categoria();
		c.setId(7);
		c.setNome("2 Feminina");
		cats.add(c);
		c = new Categoria();
		c.setId(8);
		c.setNome("3 Feminina");
		cats.add(c);
		c = new Categoria();
		c.setId(9);
		c.setNome("4 Feminina");
		cats.add(c);
		c = new Categoria();
		c.setId(10);
		c.setNome("5 Feminina");
		cats.add(c);
		
		this.chavesCat = new LinkedHashMap<>();

		for (Categoria cat : cats) {

			ArrayList<Chave> chaves = new ArrayList<>();
			for (Chave chave : todasChaves) {
				Dupla dupla = chave.getDuplaList().get(0);
				if (cat.getId() == dupla.getCategoria().getId()) {
					chaves.add(chave);
				}
			}
			Collections.sort(chaves);
			chavesCat.put(cat.getNome(), chaves);

		}

		int numeroChaves = todasChaves.size();

		this.numeroPartidasAtual = (numeroChaves * 5) + 4;

		System.out.println("Número Total de Partidas no circuito " + this.numeroPartidasAtual);

		if (this.numeroPartidasAtual > getMaxJogosEtapa()) {
//			JOptionPane.showMessageDialog(null, "Erro! Essa etapa suporta " + getMaxJogosEtapa() + "partidas e o numero "
//					+ "de partidas estimado para as chaves atuais excede esse limite.");
//			
			int numeroChavesReduzir = numeroChavesReduzir();

//			if (ControllerPrevisaoPartidas.alertConfimarion(getMaxJogosEtapa(), numeroChavesReduzir)) {

				numeroChaves = todasChaves.size() - numeroChavesReduzir;

				this.numeroPartidasAtual = (numeroChaves * 5) + 4;
				setNumeroPartidasAtual(numeroPartidasAtual);
				redutorChaves(numeroChavesReduzir);
				geraPartidasFaseGrupo();
				geraPartidasPosFaseGrupo();
//
//			}

			// Alert dialogoExe = new Alert(Alert.AlertType.CONFIRMATION);
//            ButtonType btnSim = new ButtonType("Sim");
//            ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
//
//            dialogoExe.setTitle("Confirmação");
//            dialogoExe.setHeaderText("O numero de partidas por etapa, excedeu o limite!");
//            dialogoExe.setContentText("Para ser possível gerar a tabela dos jogos, teremos que reduzir as chaves de determinadas "
//            		+ "categorias. Você deseja fazer isso?");
//            dialogoExe.getButtonTypes().setAll(btnSim, btnNao);
//            dialogoExe.showAndWait().ifPresent(b -> {
//                if (b == btnSim) {
//                    
//                } else if (b == btnNao) {
//                    
//                }
//            });

		} else {

			geraPartidasFaseGrupo();
			geraPartidasPosFaseGrupo();

		}

//		if(partidasProntas!=null) {
//			salvaPartidas();
//		}

	}

	public void geraPartidasPosFaseGrupo() throws Exception {

		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesCat.entrySet()) {
			ArrayList<Chave> cs = entrada.getValue();
//				System.out.println("Categoria " + entrada.getKey());
			Partida p;
			ArrayList<Partida> ps;
			switch (cs.size()) {

			case 2:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - 1A| x |2B");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - 2A| x |1B");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps = new ArrayList<>();
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 3:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - 2B| x |2C");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - 2A| x |1C");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - 1A| x |Q1");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q2| x |1B");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 4:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - 1A| x |2B");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - 2C| x |1D");
				ps.add(p);
				p = new Partida();
				p.setNome("Q3 - 1C| x |2D");
				ps.add(p);
				p = new Partida();
				p.setNome("Q4 - 2A| x |1B");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - Q1| x |Q2");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q3| x |Q4");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 5:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("O1 - 2B| x |2C");
				ps.add(p);
				p = new Partida();
				p.setNome("O2 - 2D| x |2A");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - 1A| x |O1");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - 2E| x |1D");
				ps.add(p);
				p = new Partida();
				p.setNome("Q3 - 1C| x |1E");
				ps.add(p);
				p = new Partida();
				p.setNome("Q4 - O2| x |1B");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - Q1| x |Q2");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q3| x |Q4");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 6:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("O1 - 2B| x |2C");
				ps.add(p);
				p = new Partida();
				p.setNome("O2 - 2E| x |1F");
				ps.add(p);
				p = new Partida();
				p.setNome("O3 - 1E| x |2F");
				ps.add(p);
				p = new Partida();
				p.setNome("O4 - 2A| x |2D");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - 1A| x |O1");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - O2| x |1D");
				ps.add(p);
				p = new Partida();
				p.setNome("Q3 - 1C| x |O3");
				ps.add(p);
				p = new Partida();
				p.setNome("Q4 - O4| x |1B");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - Q1| x |Q2");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q3| x |Q4");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 7:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("O1 - 2F| x |2G");
				ps.add(p);
				p = new Partida();
				p.setNome("O2 - 2C| x |1E");
				ps.add(p);
				p = new Partida();
				p.setNome("O3 - 1D| x |2B");
				ps.add(p);
				p = new Partida();
				p.setNome("O4 - 1C| x |2A");
				ps.add(p);
				p = new Partida();
				p.setNome("O5 - 1F| x |2D");
				ps.add(p);
				p = new Partida();
				p.setNome("O6 - 2E| x |1G");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - 1A| x |O1");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - O2| x |O3");
				ps.add(p);
				p = new Partida();
				p.setNome("Q3 - O4| x |O5");
				ps.add(p);
				p = new Partida();
				p.setNome("Q4 - 1B| x |O6");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - Q1| x |Q2");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q3| x |Q4");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;
			case 8:

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("O1 - 1A| x |2B");
				ps.add(p);
				p = new Partida();
				p.setNome("O2 - 1H| x |2G");
				ps.add(p);
				p = new Partida();
				p.setNome("O3 - 1E| x |2F");
				ps.add(p);
				p = new Partida();
				p.setNome("O4 - 1D| x |2C");
				ps.add(p);
				p = new Partida();
				p.setNome("O5 - 1C| x |2D");
				ps.add(p);
				p = new Partida();
				p.setNome("O6 - 1F| x |2E");
				ps.add(p);
				p = new Partida();
				p.setNome("O7 - 1G| x |2H");
				ps.add(p);
				p = new Partida();
				p.setNome("O8 - 1B| x |2A");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Oitavas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("Q1 - O1| x |O2");
				ps.add(p);
				p = new Partida();
				p.setNome("Q2 - O3| x |O4");
				ps.add(p);
				p = new Partida();
				p.setNome("Q3 - O5| x |O6");
				ps.add(p);
				p = new Partida();
				p.setNome("Q4 - O7| x |O8");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Quartas", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("S1 - Q1| x |Q2");
				ps.add(p);
				p = new Partida();
				p.setNome("S2 - Q3| x |Q4");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Semi", ps);

				ps = new ArrayList<>();
				p = new Partida();
				p.setNome("F - S1| x |S2");
				ps.add(p);
				partidas.put("Categoria " + entrada.getKey() + " Final", ps);

				break;

			default:

				break;
			}

		}

		for (Map.Entry<String, ArrayList<Partida>> entrada : partidas.entrySet()) {
			ArrayList<Partida> p = entrada.getValue();
			System.out.println(entrada.getKey());
			for (Partida part : p) {
				System.out.println("\t" + part.getNome());

			}
			System.out.println("\n");

		}

		alocaPartidasHorarios();

	}

	public void alocaGradeHorarios() {

		// 0 seg - 1 terç - 2 quart - 3 quint - 4 sext - 5 sab - 6 domin

		int hora = 18;
		int min = 0;
		int dia = 26;

		int qtdHr = 7;
		int hrInicial = 18;

		datas = new ArrayList<>();
		// For de Quinta, Sexta, Sábado e Domingo
		for (int d = 0; d < 4; d++) {

			// If que verifica se o array não possui nenhum objeto
			if (datas.size() == 0) {
				geraDias(dia, hora, min, hrInicial, qtdHr);
			} else {

				// If que verifica em qual dia está, se for menor que dois é na quinta ou na
				// sexta
				if (d < 2) {
					geraDias(dia, hora, min, hrInicial, qtdHr);
				} // Else If que se o dia que está for maior ou igual a dois é no sábado e no
					// domingo
					// assim é necessário alterar a quantidade de horarios por dia e o horário
					// inicial do primeiro jogo do dia.
				else if (d >= 2) {
					qtdHr = 19;
					hrInicial = 8;
					geraDias(dia, hora, min, hrInicial, qtdHr);
				}
			}

			dia++;

		}

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

//		for (Date lDT : datas) {
//			System.out.println(lDT.toString());
//			System.out.println("Dia da semana: " + lDT.getDay());
//			System.out.println("Mes: " + lDT.getMonth());
//			System.out.println("Ano: " + lDT.getYear());
//			System.out.println("Horas: " + lDT.getHours());
//			System.out.println("Minutos: " + lDT.getMinutes());
//			System.out.println("==================================");
//		}

	}

	public void geraDias(int dia, int hora, int min, int hrInicial, int qtdHr) {

		// For da quantidade de horários no dia, que pode ser 19 ou 7.
		for (int h = 0; h < qtdHr; h++) {

			// If para verificar se o for se encontra na primeira execução sendo assim
			// resetando a hora para seu estado inicial
			if (h == 0) {
				hora = hrInicial;
			}

			Date data = new Date(119, 8, dia, hora, min, 0);
			datas.add(data);

			// Incrementa 50 minutos na variavel dos minutos
			min = min + 50;
			// If que verifica se os minutos incrementados excedem o limite de 60
			if (min >= 60) {
				// Incrementa uma hora, pois 60 min são equivalentes a uma hora
				hora++;
				// Zera os minutos
				min = min - 60;
			}
		}

	}

	public void geraPartidasFaseGrupo() throws Exception {

		this.partidas = new LinkedHashMap<>();

//		ptida = (PersistePartidaIF) Naming.lookup(Connection.getUrl() + "partida");

		for (int i = 0; i < this.todasChaves.size(); i++) {
			Chave chave = this.todasChaves.get(i);
			ArrayList<Dupla> duplas = new ArrayList<Dupla>();

			Partida p = new Partida();
			Partida p1 = new Partida();
			Partida p2 = new Partida();

			duplas.add(chave.getDuplaList().get(0));
			duplas.add(chave.getDuplaList().get(1));
			p.setDuplaList(duplas);
			p.setNome(chave.getDuplaList().get(0).getNome() + "| x |" + chave.getDuplaList().get(1).getNome());
			duplas.clear();

			duplas.add(chave.getDuplaList().get(1));
			duplas.add(chave.getDuplaList().get(2));
			p1.setDuplaList(duplas);
			p1.setNome(chave.getDuplaList().get(1).getNome() + "| x |" + chave.getDuplaList().get(2).getNome());
			duplas.clear();

			duplas.add(chave.getDuplaList().get(0));
			duplas.add(chave.getDuplaList().get(2));
			p2.setDuplaList(duplas);
			p2.setNome(chave.getDuplaList().get(0).getNome() + "| x |" + chave.getDuplaList().get(2).getNome());
			duplas.clear();

			ArrayList<Partida> ps = new ArrayList<>();
			ps.add(p);
			ps.add(p1);
			ps.add(p2);

			partidas.put("Categoria " + chave.getDuplaList().get(0).getCategoria().getNome()
					+ " Fase de Grupo da Chave " + chave.getNome(), ps);

		}

	}

	public void alocaPartidasHorarios() {

		ArrayList<Partida> faseGps = new ArrayList<>();
		ArrayList<Partida> oitavas = new ArrayList<>();
		ArrayList<Partida> quartas = new ArrayList<>();
		ArrayList<Partida> semis = new ArrayList<>();
		ArrayList<Partida> finais = new ArrayList<>();

		alocaGradeHorarios();

		for (Map.Entry<String, ArrayList<Partida>> entrada : partidas.entrySet()) {
			ArrayList<Partida> p = entrada.getValue();
//			System.out.println(entrada.getKey());

			if (entrada.getKey().contains("Grupo")) {
				System.out.println("Grupo =" + entrada.getKey());
				for (Partida part : p) {
					faseGps.add(part);

				}
			} else if (entrada.getKey().contains("Oitavas")) {
				for (Partida part : p) {
					oitavas.add(part);
				}
			} else if (entrada.getKey().contains("Quartas")) {
				for (Partida part : p) {
					quartas.add(part);
				}
			} else if (entrada.getKey().contains("Semi")) {
				for (Partida part : p) {
					semis.add(part);
				}
			} else if (entrada.getKey().contains("Final")) {
				for (Partida part : p) {
					finais.add(part);
				}
			}
		}

		int index_datas = 0;
		int quadra = 0;

		Quadra quadra1 = new Quadra();
		quadra1.setId(1);
		Quadra quadra2 = new Quadra();
		quadra2.setId(2);
		Quadra quadra3 = new Quadra();
		quadra3.setId(3);
		ArrayList<Quadra> quadras = new ArrayList<>();
		quadras.add(quadra1);
		quadras.add(quadra2);
		quadras.add(quadra3);

		System.out.println(faseGps.size());

		System.out.println(oitavas.size());

		System.out.println(quartas.size());

		System.out.println(semis.size());

		System.out.println(finais.size());

		for (Partida p : faseGps) {

			p.setDataHora(datas.get(index_datas));
			p.setQuadra(quadras.get(quadra));

			if (quadra == 2) {
				index_datas++;
				quadra = -1;
			}
			quadra++;
		}

		for (Partida p : oitavas) {

			p.setDataHora(datas.get(index_datas));
			p.setQuadra(quadras.get(quadra));

			if (quadra == 2) {
				index_datas++;
				quadra = -1;
			}
			quadra++;
		}

		for (Partida p : quartas) {

			p.setDataHora(datas.get(index_datas));
			p.setQuadra(quadras.get(quadra));

			if (quadra == 2) {
				index_datas++;
				quadra = -1;
			}
			quadra++;
		}

		for (Partida p : semis) {

			p.setDataHora(datas.get(index_datas));
			p.setQuadra(quadras.get(quadra));

			if (quadra == 2) {
				index_datas++;
				quadra = -1;
			}
			quadra++;
		}

		for (Partida p : finais) {

			p.setDataHora(datas.get(index_datas));
			p.setQuadra(quadras.get(quadra));

			if (quadra == 2) {
				index_datas++;
				quadra = -1;
			}
			quadra++;
		}

		this.partidasProntas = new ArrayList<>();
		this.partidasProntas.addAll(faseGps);
		this.partidasProntas.addAll(oitavas);
		this.partidasProntas.addAll(quartas);
		this.partidasProntas.addAll(semis);
		this.partidasProntas.addAll(finais);

		for (Partida p : partidasProntas) {
			System.out.println(p.getDataHora() + " | " + p.getNome() + " | " + "Quadra " + p.getQuadra().getId());
		}

	}

	public int numeroChavesReduzir() {

		int numeroPartidas = getNumeroPartidasAtual(), numeroPartidasTotal = getNumeroPartidasAtual(), redutor = 0,
				numeroReduzir = 0;

		for (redutor = 0; numeroPartidas > getMaxJogosEtapa(); redutor++) {

			numeroReduzir = redutor;
			numeroPartidas = numeroPartidasTotal - ((redutor * 5) + 4);
//			System.out.println("Número de chaves a reduzir " + numeroReduzir);
//			System.out.println("Número de partidas reduzindo as " + numeroReduzir + " chaves = " + numeroPartidas);
		}

		return numeroReduzir;
	}

	public String redutorChaves(int numeroReduzir) throws RemoteException {

		HashMap<String, ArrayList<Chave>> chavesEliminadas = new LinkedHashMap();

		String indexCategoriaMaisChave = "", indexCategoriaMaisChave2 = "", indexCategoriaMaisChave3 = "";
		int numChave = 0, numChave2 = 0, numChave3 = 0;

		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesCat.entrySet()) {

			ArrayList<Chave> chaves = entrada.getValue();
//			System.out.println("Categoria " + entrada.getKey());
//			System.out.println("Chaves :");
//			System.out.println("Quantidade Chaves na Categoria " + entrada.getKey() + " = " + chaves.size());
			if (numChave < chaves.size()) {

				if (numChave2 <= numChave) {

					if (numChave3 <= numChave2) {
						indexCategoriaMaisChave3 = indexCategoriaMaisChave2;
						numChave3 = numChave2;
					}

					indexCategoriaMaisChave2 = indexCategoriaMaisChave;
					numChave2 = numChave;
				}
				indexCategoriaMaisChave = entrada.getKey();
				numChave = chaves.size();

			}
//			for (Chave c : chaves) {
//				System.out.println("\t" + c.getNome());
//			}
		}

//		System.out.println("Categoria com mais chaves " + indexCategoriaMaisChave + " com " + numChave + " Chaves");
//		System.out.println(
//				"Segunda Categoria com mais chaves " + indexCategoriaMaisChave2 + " com " + numChave2 + " Chaves");
//		System.out.println(
//				"Terceira Categoria com mais chaves " + indexCategoriaMaisChave3 + " com " + numChave3 + " Chaves");

		int divisao = 0;

		divisao = numeroReduzir / 3;

		ArrayList<Chave> chaves1 = new ArrayList<>();
		ArrayList<Chave> chaves2 = new ArrayList<>();
		ArrayList<Chave> chaves3 = new ArrayList<>();

//		System.out.println("Divisão =" + divisao);
		for (int i = 1; i <= divisao; i++) {
			chaves1.add(chavesCat.get(indexCategoriaMaisChave).get(numChave - i));
//			System.out.println(chavesCat.get(indexCategoriaMaisChave).get(numChave-i).getNome());
			chavesCat.get(indexCategoriaMaisChave).remove(numChave - i);

			chaves2.add(chavesCat.get(indexCategoriaMaisChave2).get(numChave2 - i));
//			System.out.println(chavesCat.get(indexCategoriaMaisChave2).get(numChave2-i).getNome());
			chavesCat.get(indexCategoriaMaisChave2).remove(numChave2 - i);

			chaves3.add(chavesCat.get(indexCategoriaMaisChave3).get(numChave3 - i));
//			System.out.println(chavesCat.get(indexCategoriaMaisChave3).get(numChave3-i).getNome());
			chavesCat.get(indexCategoriaMaisChave3).remove(numChave3 - i);
		}

		boolean erro = false;

		ArrayList<Chave> eliminadas = new ArrayList<>();
		eliminadas.addAll(chaves1);
		eliminadas.addAll(chaves2);
		eliminadas.addAll(chaves3);

//		if (pchave.removeChaves(eliminadas)) {
//			ControllerPrevisaoPartidas.alertDelete();
//		} else {
//			ControllerPrevisaoPartidas.alertNaoDelete();
//		}

		chavesEliminadas.put(indexCategoriaMaisChave, chaves1);
		chavesEliminadas.put(indexCategoriaMaisChave2, chaves2);
		chavesEliminadas.put(indexCategoriaMaisChave3, chaves3);

		String chavesReduzidas = "";
		for (Map.Entry<String, ArrayList<Chave>> entrada : chavesEliminadas.entrySet()) {

			ArrayList<Chave> chaves = entrada.getValue();

			chavesReduzidas = chavesReduzidas + "\nCategoria " + entrada.getKey() + "\n";

			for (Chave cs : chaves) {
				chavesReduzidas = chavesReduzidas + "\t Chaves: " + cs.getNome() + "\n";
			}

		}

		return chavesReduzidas;

	}

	public int getMaxJogosEtapa() {

		this.maxJogosEtapa = MAX_HORARIOS_ETAPA * numeroQuadras;
		return this.maxJogosEtapa;

	}

	public ArrayList<HashMap<String, ArrayList<Dupla>>> getMapa() {
		return mapa;
	}

	public void setMapa(ArrayList<HashMap<String, ArrayList<Dupla>>> mapa) {
		this.mapa = mapa;
	}
}
