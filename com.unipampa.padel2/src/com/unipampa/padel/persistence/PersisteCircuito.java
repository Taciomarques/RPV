package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Circuito;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;

import interfaces.PersisteCategoriaIF;
import interfaces.PersisteCircuitoIF;
import interfaces.PersisteEtapaIF;

public class PersisteCircuito extends UnicastRemoteObject implements PersisteCircuitoIF {

	protected PersisteCircuito() throws RemoteException {
		super();
	}

	@Override
	public Circuito cadastroCircuito(Circuito c) throws Exception {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(c);

			entityManager.getTransaction().commit();

			entityManager.close();

			return c;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ArrayList<Circuito> recuperaCircuitos() {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		String sql = "From " + Circuito.class.getName();
		Object result = entityManager.createQuery(sql).getResultList();
		ArrayList<Circuito> encontrada = (ArrayList<Circuito>) result;

		entityManager.close();

		return encontrada;
	}

}