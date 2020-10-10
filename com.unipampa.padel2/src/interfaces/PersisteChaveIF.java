package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Chave;

public interface PersisteChaveIF extends Remote {

	public void cadastroChave(Chave a) throws Exception;

	public ArrayList<Chave> recuperaChaves() throws RemoteException;

	public boolean removeChaves(ArrayList<Chave> chaves) throws RemoteException;

}