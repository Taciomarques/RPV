package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Inscricao;

import interfaces.PersisteInscricaoIF;

public class PersisteInscricao extends UnicastRemoteObject implements PersisteInscricaoIF {

	protected PersisteInscricao() throws RemoteException {
		super();
	}

	@Override
	public Inscricao cadastroInscricao(Inscricao i) throws RemoteException {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(i);

			entityManager.getTransaction().commit();

			entityManager.close();

			return i;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ArrayList<Inscricao> recuperaInscricoes() {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		String sql = "From " + Inscricao.class.getName();
		Object result = entityManager.createQuery(sql).getResultList();
		ArrayList<Inscricao> encontrada = (ArrayList<Inscricao>) result;

		entityManager.close();

		return encontrada;
	}

}