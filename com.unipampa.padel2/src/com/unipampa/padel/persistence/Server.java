package com.unipampa.padel.persistence;

import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) throws AccessException, RemoteException {
		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("chave", new PersisteChave());
		registry.rebind("categoria", new PersisteCategoria());
		registry.rebind("partida", new PersistePartida());
		registry.rebind("atleta", new PersisteAtleta());
		registry.rebind("etapa", new PersisteEtapa());
		registry.rebind("circuito", new PersisteCircuito());
		registry.rebind("gerente", new PersisteGerente());
		registry.rebind("ranking", new PersisteRanking());
		registry.rebind("inscricao", new PersisteInscricao());
	}

}
