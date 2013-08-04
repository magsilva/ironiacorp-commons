package com.ironiacorp.persistence.dao.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPA_Util
{
	private static Map<String, EntityManagerFactory> emfs = new HashMap<String, EntityManagerFactory>();

	public synchronized EntityManagerFactory loadEntityManagerFactory(String emfName)
	{
		EntityManagerFactory emf;
		
		if (! emfs.containsKey(emfName)) {
			try {
				emf = Persistence.createEntityManagerFactory(emfName);
				if (emf == null) {
					throw new IllegalArgumentException(new NullPointerException());	
				}
				emfs.put(emfName, emf);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		} else {
			emf = emfs.get(emfName);
		}

		return emf;
	}
	
	public synchronized EntityManager getEntityManager(String emfName)
	{
		EntityManagerFactory emf;
		EntityManager em;
		
		emf = loadEntityManagerFactory(emfName);
		em = emf.createEntityManager();
		return em;
    }
	
	public synchronized void closeEntityManagerFactory(String emfName)
	{
		EntityManagerFactory emf;
		
		if (! emfs.containsKey(emfName)) {
			emf = emfs.get(emfName);
			emfs.remove(emfName);
			emf.close();
		}
    }
}
