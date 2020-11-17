package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Partida;

import interfaces.PersistePartidaIF;

public class PersistePartida extends UnicastRemoteObject implements PersistePartidaIF {

	protected PersistePartida() throws RemoteException {
		super();
	}

	@Override
	public void cadastroPartida(Partida a) throws RemoteException {

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
	public void cadastroPartidas(ArrayList<Partida> partidas) {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			for (Partida p : partidas) {
				entityManager.persist(p);

			}
			entityManager.getTransaction().commit();

			entityManager.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}
	}

	@Override
	public ArrayList<Partida> recuperaPartida() throws RemoteException{

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			String sql = "From " + Partida.class.getName();
			Object result = entityManager.createQuery(sql).getResultList();
			ArrayList<Partida> encontrada = (ArrayList<Partida>) result;

			entityManager.close();

			return encontrada;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}