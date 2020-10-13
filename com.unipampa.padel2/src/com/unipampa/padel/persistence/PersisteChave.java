package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Chave;

import interfaces.PersisteChaveIF;

public class PersisteChave extends UnicastRemoteObject implements PersisteChaveIF {

	protected PersisteChave() throws RemoteException {
		super();
	}

	@Override
	public void cadastroChave(Chave a) throws Exception {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(a);

			entityManager.getTransaction().commit();

			entityManager.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public ArrayList<Chave> recuperaChaves() {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			String sql = "From " + Chave.class.getName();
			Object result = entityManager.createQuery(sql).getResultList();
			ArrayList<Chave> encontrada = (ArrayList<Chave>) result;

			entityManager.close();

			return encontrada;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void removeChaves(ArrayList<Chave> chaves) throws Exception{

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			for (Chave c : chaves) {
				c = entityManager.find(Chave.class, c.getId());

				entityManager.remove(c);

			}

			entityManager.getTransaction().commit();

			entityManager.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}