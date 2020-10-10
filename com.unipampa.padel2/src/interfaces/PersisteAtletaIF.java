package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;

public interface PersisteAtletaIF extends Remote {

	public Atleta cadastroAtleta(Atleta a) throws RemoteException, Exception;

	public Dupla cadastroDupla(Dupla d) throws RemoteException, Exception;

	public ArrayList<Dupla> recuperaDuplas() throws RemoteException;

	public ArrayList<Atleta> recuperaAtleta() throws RemoteException;

	public ArrayList<Dupla> recuperaDuplasPorCategoria(Categoria categoria) throws RemoteException;

	public Dupla atualizaDupla(Dupla d) throws RemoteException;

	public Atleta atualizaAtleta(Atleta d) throws RemoteException;

}