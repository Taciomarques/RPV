package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Gerente;

import interfaces.PersisteGerenteIF;

public class PersisteGerente extends UnicastRemoteObject implements PersisteGerenteIF {

	protected PersisteGerente() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Gerente cadastroGerente(Gerente g) throws Exception {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(g);

			entityManager.getTransaction().commit();

			entityManager.close();

			return g;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ArrayList<Gerente> recuperaGerentes() {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		String sql = "From " + Gerente.class.getName();
		Object result = entityManager.createQuery(sql).getResultList();
		ArrayList<Gerente> encontrada = (ArrayList<Gerente>) result;

		entityManager.close();

		return encontrada;
	}

}