package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Gerente;

public interface PersisteGerenteIF extends Remote {

	public Gerente cadastroGerente(Gerente g) throws Exception;

	public ArrayList<Gerente> recuperaGerentes() throws RemoteException;

}