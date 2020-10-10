package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Ranking;

public interface PersisteRankingIF extends Remote {

	public Ranking cadastroRanking(Ranking r) throws Exception;

	public ArrayList<Ranking> recuperaRankings() throws RemoteException;

}