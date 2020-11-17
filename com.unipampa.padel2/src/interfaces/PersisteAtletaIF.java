package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;

public interface PersisteAtletaIF extends Remote {

	Atleta cadastroAtleta(Atleta a) throws RemoteException, Exception;

	Dupla cadastroDupla(Dupla d) throws RemoteException, Exception;

	ArrayList<Dupla> recuperaDuplas() throws RemoteException;

	ArrayList<Atleta> recuperaAtleta() throws RemoteException;

	ArrayList<Dupla> recuperaDuplasPorCategoria(Categoria categoria) throws RemoteException;

	Dupla atualizaDupla(Dupla d) throws RemoteException;

	Atleta atualizaAtleta(Atleta d) throws RemoteException;

}