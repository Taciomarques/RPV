package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Gerente;

public interface PersisteGerenteIF extends Remote {

	Gerente cadastroGerente(Gerente g) throws RemoteException;

	ArrayList<Gerente> recuperaGerentes() throws RemoteException;

}