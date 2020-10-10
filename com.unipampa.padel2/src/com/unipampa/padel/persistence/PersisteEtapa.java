package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;
import com.unipampa.padel.model.Etapa;

import interfaces.PersisteCategoriaIF;
import interfaces.PersisteEtapaIF;

public class PersisteEtapa extends UnicastRemoteObject implements PersisteEtapaIF {

	protected PersisteEtapa() throws RemoteException {
		super();
	}

	@Override
	public Etapa cadastroEtapa(Etapa e) throws Exception {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(e);

			entityManager.getTransaction().commit();

			entityManager.close();

			return e;
		} catch (Exception error) {
			throw error;
		}
	}

	@Override
	public ArrayList<Etapa> recuperaEtapas() {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		String sql = "From " + Etapa.class.getName();
		Object result = entityManager.createQuery(sql).getResultList();
		ArrayList<Etapa> encontrada = (ArrayList<Etapa>) result;

		entityManager.close();

		return encontrada;
	}

}