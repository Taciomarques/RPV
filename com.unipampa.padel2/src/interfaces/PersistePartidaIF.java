package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Partida;

public interface PersistePartidaIF extends Remote {

	boolean cadastroPartida(Partida a) throws RemoteException;

	ArrayList<Partida> recuperaPartida() throws RemoteException;
	
	public boolean cadastroPartidas(ArrayList<Partida> partidas) throws RemoteException;


}