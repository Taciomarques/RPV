package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Inscricao;

public interface PersisteInscricaoIF extends Remote {

	Inscricao cadastroInscricao(Inscricao i) throws RemoteException;

	ArrayList<Inscricao> recuperaInscricoes() throws RemoteException;

}