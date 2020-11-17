package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Atleta;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;

import interfaces.PersisteAtletaIF;

public class PersisteAtleta extends UnicastRemoteObject implements PersisteAtletaIF {

	protected PersisteAtleta() throws RemoteException {
		super();
	}

	@Override
	public Atleta cadastroAtleta(Atleta a) {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(a);

			entityManager.getTransaction().commit();

			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return a;

	}

	@Override
	public Dupla cadastroDupla(Dupla d) {

		try {

			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			entityManager.persist(d);

			entityManager.getTransaction().commit();

			entityManager.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return d;

	}

	@Override
	public ArrayList<Dupla> recuperaDuplas() {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			String sql = "From " + Dupla.class.getName();
			Object result = entityManager.createQuery(sql).getResultList();
			ArrayList<Dupla> encontrada = (ArrayList<Dupla>) result;

			entityManager.close();

			return encontrada;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Atleta> recuperaAtleta() {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			String sql = "From " + Atleta.class.getName();
			Object result = entityManager.createQuery(sql).getResultList();
			ArrayList<Atleta> encontrada = (ArrayList<Atleta>) result;

			entityManager.close();

			return encontrada;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ArrayList<Dupla> recuperaDuplasPorCategoria(Categoria categoria) {

		try {
			EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

			entityManager.getTransaction().begin();

			String sql = "FROM " + Dupla.class.getName() + " WHERE id_categoria = :categoria";

			Query query = entityManager.createQuery(sql);
			query.setParameter("categoria", categoria.getId());
			ArrayList<Dupla> encontrada = (ArrayList<Dupla>) query.getResultList();

			entityManager.close();

			return encontrada;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Dupla atualizaDupla(Dupla d) {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();

		entityManager.merge(d);

		entityManager.getTransaction().commit();

		entityManager.close();

		return d;
	}

	@Override
	public Atleta atualizaAtleta(Atleta d) {

		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

		entityManager.getTransaction().begin();
//
		entityManager.merge(d);

		entityManager.getTransaction().commit();

		entityManager.close();

		return d;
	}

}
