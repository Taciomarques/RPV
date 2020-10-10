package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Inscricao;

public interface PersisteInscricaoIF extends Remote {

	public Inscricao cadastroInscricao(Inscricao i) throws Exception;

	public ArrayList<Inscricao> recuperaInscricoes() throws RemoteException;

}