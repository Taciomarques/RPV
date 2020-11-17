package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Etapa;

public interface PersisteEtapaIF extends Remote {

	Etapa cadastroEtapa(Etapa e) throws RemoteException;

	ArrayList<Etapa> recuperaEtapas() throws RemoteException;

}