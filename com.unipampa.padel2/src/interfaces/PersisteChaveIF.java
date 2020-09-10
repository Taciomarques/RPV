package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Chave;

public interface PersisteChaveIF extends Remote {

	boolean cadastroChave(Chave a) throws RemoteException;

	ArrayList<Chave> recuperaChaves() throws RemoteException;
	
	boolean removeChaves(ArrayList<Chave> chaves) throws RemoteException;

}