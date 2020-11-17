package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.unipampa.padel.model.Categoria;

public interface PersisteCategoriaIF extends Remote {

	Categoria cadastroCategoria(Categoria a) throws RemoteException;

	ArrayList<Categoria> recuperaCategorias() throws RemoteException;

}