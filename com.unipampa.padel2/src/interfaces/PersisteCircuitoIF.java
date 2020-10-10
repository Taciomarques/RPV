package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Circuito;

public interface PersisteCircuitoIF extends Remote {

	public Circuito cadastroCircuito(Circuito c) throws Exception;

	public ArrayList<Circuito> recuperaCircuitos() throws RemoteException;

}