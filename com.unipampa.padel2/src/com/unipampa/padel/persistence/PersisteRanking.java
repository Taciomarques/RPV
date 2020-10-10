package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Ranking;

import interfaces.PersisteRankingIF;

public class PersisteRanking extends UnicastRemoteObject implements PersisteRankingIF {

	protected PersisteRanking() throws RemoteException {
		super();
	}

	@Override
	public Ranking cadastroRanking(Ranking r) throws Exception {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(r);

			entityManager.getTransaction().commit();

			entityManager.close();

			return r;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ArrayList<Ranking> recuperaRankings() {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		String sql = "From " + Ranking.class.getName();
		Object result = entityManager.createQuery(sql).getResultList();
		ArrayList<Ranking> encontrada = (ArrayList<Ranking>) result;

		entityManager.close();

		return encontrada;
	}

}