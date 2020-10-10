package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Partida;

public interface PersistePartidaIF extends Remote {

	public void cadastroPartida(Partida a) throws Exception;

	public ArrayList<Partida> recuperaPartida() throws RemoteException;

	public boolean cadastroPartidas(ArrayList<Partida> partidas) throws RemoteException;

}