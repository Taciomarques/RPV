package com.unipampa.padel.persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.boraji.tutorial.hibernate.JPAUtil;
import com.unipampa.padel.model.Categoria;
import com.unipampa.padel.model.Dupla;

import interfaces.PersisteCategoriaIF;

public class PersisteCategoria extends UnicastRemoteObject implements PersisteCategoriaIF {
	
	protected PersisteCategoria() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Categoria cadastroCategoria(Categoria a) {

        try {
        	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        	
            entityManager.getTransaction().begin();
            
            entityManager.persist(a);

            entityManager.getTransaction().commit();

            entityManager.close();
            
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return a;
        }
    }
	
	@Override
	public ArrayList<Categoria> recuperaCategorias() {

        try {
        	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        	
            entityManager.getTransaction().begin();
        	
        	String sql = "From " + Categoria.class.getName();
            Object result = entityManager.createQuery(sql).getResultList();
            ArrayList<Categoria> encontrada = (ArrayList<Categoria>) result;
            
            entityManager.close();
            
            return encontrada;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
}