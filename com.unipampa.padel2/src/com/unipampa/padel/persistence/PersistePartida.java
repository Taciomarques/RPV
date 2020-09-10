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
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean cadastroPartida(Partida a) {

        try {
        	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        	
            entityManager.getTransaction().begin();
            
            entityManager.persist(a);

            entityManager.getTransaction().commit();

            entityManager.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
	public boolean cadastroPartidas(ArrayList<Partida> partidas) {

        try {
        	EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        	
            entityManager.getTransaction().begin();

        	for (Partida p : partidas) {
                entityManager.persist(p);

        	}
            entityManager.getTransaction().commit();

            entityManager.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	@Override
	public ArrayList<Partida> recuperaPartida() {

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
            return null;
        }
    }
	
}