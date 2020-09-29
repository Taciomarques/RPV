package com.unipampa.padel.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.unipampa.padel.model.*;

import connection.Connection;
import interfaces.*;;

public class CriaTabelas {
	public static void main(String[] args) throws Exception {
		try {
//		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
//		entityManager.getTransaction().begin();
//
//		// Check database version
//		String sql = "select version()";
//
//		String result = (String) entityManager.createNativeQuery(sql).getSingleResult();
//		System.out.println(result);
//
//		entityManager.getTransaction().commit();
//		entityManager.close();
//
//		JPAUtil.shutdown();
		
			populaBanco();
		} catch (NoSuchElementException e) {
			System.out.println("Fim do arquivo excel");
		}
		
	}
	
	private static void populaBanco() throws Exception {
		List<Categoria> listCategorias = cadastraCategorias();
		
		Gerente gerente = new Gerente("login", "senha", "Gerente Aqua");
		PersisteGerenteIF pGerente = (PersisteGerenteIF) Naming.lookup(Connection.getUrl() + "gerente");
		gerente = pGerente.cadastroGerente(gerente);
		
		
		Circuito circuito = new Circuito();
		circuito.setNome("Aqua Padel Cristal 19");
		circuito.setGerente(gerente);
		

		PersisteCircuitoIF pCircuito = (PersisteCircuitoIF) Naming.lookup(Connection.getUrl() + "circuito");
		circuito = pCircuito.cadastroCircuito(circuito);
		
		
		PersisteEtapaIF pe = (PersisteEtapaIF) Naming.lookup(Connection.getUrl() + "etapa");
		Etapa etapa = new Etapa();
		etapa.setNome("3ª Etapa");
		Date dataInicial = new Date();
		Date dataFinal = new Date(86400);
		etapa.setDataInicial(dataInicial);
		etapa.setDataFinal(dataFinal);
		etapa.setCircuito(circuito);
		
		etapa = pe.cadastroEtapa(etapa);
		
		
		String emailGenerico = "sem@email.com";
		
		PersisteAtletaIF pa = (PersisteAtletaIF) Naming.lookup(Connection.getUrl() + "atleta");
    	
    	File excelFile = new File("inscritos.xlsx");
        FileInputStream fis = new FileInputStream(excelFile);

        // we create an XSSF Workbook object for our XLSX Excel File
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        // we get first sheet
        XSSFSheet sheet = workbook.getSheetAt(0);

        DecimalFormat pattern = new DecimalFormat("##########");
        
        // we iterate on rows
        Iterator<Row> rowIt = sheet.iterator();
        
        rowIt.next(); // pular o cabeçalho

        while(rowIt.hasNext()) {
			Row row = rowIt.next();
			
			// iterate on cells for the current row
			Iterator<Cell> cellIterator = row.cellIterator();
			
	        Date horaInsc = cellIterator.next().getDateCellValue();
	        String nomeCategoria = cellIterator.next().getStringCellValue();
	        String nomeAtleta1 = cellIterator.next().getStringCellValue();
	        int pontosAtleta1 = (int) cellIterator.next().getNumericCellValue();
	        String cpfAtleta1 = pattern.format(cellIterator.next().getNumericCellValue());
	        cellIterator.next();
	        String nomeAtleta2 = cellIterator.next().getStringCellValue();
	        int pontosAtleta2 = (int) cellIterator.next().getNumericCellValue();
	        String cpfAtleta2 = pattern.format(cellIterator.next().getNumericCellValue());
	        cellIterator.next();
	        String impedimento = cellIterator.next().getStringCellValue();
	        
	        Categoria categoria = getCategoria(nomeCategoria, listCategorias);
	        
	        Dupla dupla = new Dupla();
	        Set<Dupla> listDuplas = new HashSet<Dupla>();
	        
	        // seta os dados dos atletas 1 e 2
			Atleta atleta1 = new Atleta();
			atleta1.setNome(nomeAtleta1);
			atleta1.setCpf(cpfAtleta1);
			atleta1.setEmail(emailGenerico);
			atleta1.setNumCel(cpfAtleta1);
			
			Atleta atleta2 = new Atleta();
			atleta2.setNome(nomeAtleta2);
			atleta2.setCpf(cpfAtleta2);
			atleta2.setEmail(emailGenerico);
			atleta2.setNumCel(cpfAtleta2);
			//
			
			// cadastra os dois atletas pra ter a referencia do BD
			atleta1 = pa.cadastroAtleta(atleta1);
			atleta2 = pa.cadastroAtleta(atleta2);
			
			// seta a lista de atletas na dupla
			ArrayList<Atleta> atletaList = new ArrayList<Atleta>();
			atletaList.add(atleta1);
			atletaList.add(atleta2);
			dupla.setAtletaList(atletaList);
			dupla.setCategoria(categoria);
			dupla.setImpedimento(impedimento);
			dupla.setNome(atleta1.getNome() + "/" + atleta2.getNome());
			dupla.setPontosRank(pontosAtleta1 + pontosAtleta2);
			dupla = pa.cadastroDupla(dupla);
			
			listDuplas.add(dupla);
			atleta1.setDuplas(listDuplas);
			atleta2.setDuplas(listDuplas);
			atleta1 = pa.atualizaAtleta(atleta1);
			atleta2 = pa.atualizaAtleta(atleta2);
			
			Ranking ranking = new Ranking();
			ranking.setRankingPK(new RankingPK(atleta1.getId(), circuito.getId()));
			ranking.setAtleta1(atleta1);
			ranking.setPontosrank(pontosAtleta1);
			ranking.setCategoria(categoria);
			ranking.setCircuito1(circuito);
	        
			Ranking ranking2 = new Ranking();
			ranking2.setRankingPK(new RankingPK(atleta2.getId(), circuito.getId()));
			ranking2.setAtleta1(atleta2);
			ranking2.setPontosrank(pontosAtleta2);
			ranking2.setCategoria(categoria);
			ranking2.setCircuito1(circuito);
			
			PersisteRankingIF pRanking = (PersisteRankingIF) Naming.lookup(Connection.getUrl() + "ranking");
			ranking = pRanking.cadastroRanking(ranking);
			ranking2 = pRanking.cadastroRanking(ranking2);
			
			Inscricao inscricao = new Inscricao();
			inscricao.setInscricaoPK(new InscricaoPK(dupla.getId(), etapa.getId()));
			inscricao.setDupla1(dupla);
			inscricao.setEtapa1(etapa);
			inscricao.setHorainsc(horaInsc);
			
			PersisteInscricaoIF pInscricao = (PersisteInscricaoIF) Naming.lookup(Connection.getUrl() + "inscricao");
			pInscricao.cadastroInscricao(inscricao);
			
	        System.out.println(horaInsc + "; " + nomeCategoria + "; " + nomeAtleta1 + "; " + cpfAtleta1 + "; " + nomeAtleta2 + "; " + cpfAtleta2 + "; " + impedimento + ";");
	        
	        
        }

        workbook.close();
        fis.close();
	}

	private static Categoria getCategoria(String nomeCategoria, List<Categoria> listCategorias) {
		for(Categoria ctg: listCategorias) {
		    if(ctg.getNome().trim().equalsIgnoreCase(nomeCategoria)) {
		    	return ctg;
		    }
		}
		
		return null;
	}

	private static List<Categoria> cadastraCategorias() throws NotBoundException, MalformedURLException, RemoteException {
		Categoria categoria1Livre = new Categoria();
    	categoria1Livre.setNome("1ª livre");
    	
    	Categoria categoria2Livre = new Categoria();
    	categoria2Livre.setNome("2ª livre");
    	
    	Categoria categoria3Livre = new Categoria();
    	categoria3Livre.setNome("3ª livre");

    	Categoria categoria4Livre = new Categoria();
    	categoria4Livre.setNome("4ª livre");
    	
    	Categoria categoria5Livre = new Categoria();
    	categoria5Livre.setNome("5ª livre");
    	
    	Categoria categoriaIniciantes = new Categoria();
    	categoriaIniciantes.setNome("Iniciantes livre");
    	
    	Categoria categoriaIniciantesFem = new Categoria();
    	categoriaIniciantesFem.setNome("Iniciantes feminina");
    	
    	Categoria categoria1Fem = new Categoria();
    	categoria1Fem.setNome("1ª feminina");
    	
    	Categoria categoria2Fem = new Categoria();
    	categoria2Fem.setNome("2ª feminina");
    	
    	Categoria categoria3Fem = new Categoria();
    	categoria3Fem.setNome("3ª feminina");

    	Categoria categoria4Fem = new Categoria();
    	categoria4Fem.setNome("4ª feminina");
    	
    	Categoria categoria5Fem = new Categoria();
    	categoria5Fem.setNome("5ª feminina");
    	
    	PersisteCategoriaIF pc = (PersisteCategoriaIF) Naming.lookup(Connection.getUrl() + "categoria");
    	
    	List<Categoria> listCategorias = new ArrayList<Categoria>();
    	
    	listCategorias.add(pc.cadastroCategoria(categoria1Livre));
    	listCategorias.add(pc.cadastroCategoria(categoria2Livre));
    	listCategorias.add(pc.cadastroCategoria(categoria3Livre));
    	listCategorias.add(pc.cadastroCategoria(categoria4Livre));
    	listCategorias.add(pc.cadastroCategoria(categoria5Livre));
    	listCategorias.add(pc.cadastroCategoria(categoriaIniciantes));
    	listCategorias.add(pc.cadastroCategoria(categoriaIniciantesFem));
//    	listCategorias.add(pc.cadastroCategoria(categoria1Fem));
    	listCategorias.add(pc.cadastroCategoria(categoria2Fem));
    	listCategorias.add(pc.cadastroCategoria(categoria3Fem));
    	listCategorias.add(pc.cadastroCategoria(categoria4Fem));
    	listCategorias.add(pc.cadastroCategoria(categoria5Fem));
    	
    	return listCategorias;
	}

}
