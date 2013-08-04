/*
 * Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.persistence.dao.jpa;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import com.ironiacorp.persistence.dao.GenericDAO;

public class JPA_DAO<K extends Serializable, E> extends GenericDAO<K, E>
{
	/**
	 * The entity manager is defined (wired) using a dependency injector (DI)
	 * framework (thus we do not have to instantiate an EntityManagerFactory
	 * or EntityManager: this is due to the DI framework).
	 */
	@PersistenceContext
	protected EntityManager entityManager;
	
	private boolean autoCommit = false;
	
	public static final FlushModeType DEFAULT_FLUSHMODE = FlushModeType.COMMIT;
	
	private FlushModeType flushMode = DEFAULT_FLUSHMODE;
	
	public JPA_DAO(Class<K> keyClass, Class<E> entityClass, FlushModeType flushMode)
	{
		super(keyClass, entityClass);
		this.flushMode = flushMode;
	}
	
	public JPA_DAO(Class<K> keyClass, Class<E> entityClass)
	{
		super(keyClass, entityClass);
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager)
	{
		this.entityManager = entityManager;
		entityManager.setFlushMode(flushMode);
	}
	
	public boolean isAutoCommit()
	{
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit)
	{
		this.autoCommit = autoCommit;
	}

	private void checkTransactionBegin()
	{
		EntityTransaction tx = entityManager.getTransaction();
		if (! tx.isActive()) {
			tx.begin();
		}
	}

	private void checkTransactionEnd()
	{
		EntityTransaction tx = entityManager.getTransaction();
		if (autoCommit) {
			try {
				tx.commit();
			} catch (Exception e) {
				tx.rollback();
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public void persist(E entity)
	{
		try {
			checkTransactionBegin();
			entityManager.persist(entity);
			checkTransactionEnd();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (EntityExistsException e) {
			throw new IllegalArgumentException("Object already exists.");
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void remove(E entity)
	{
		try {
			checkTransactionBegin();
			entityManager.remove(entity);
			checkTransactionEnd();
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public void refresh(E entity)
	{
		entityManager.refresh(entity);
	}

	@Override
	public E merge(E entity)
	{
		try {
			E result;
			checkTransactionBegin();
			result = entityManager.merge(entity);
			checkTransactionEnd();
			return result;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean contains(E entity)
	{
		try {
			return entityManager.contains(entity);
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Sets all the parameters of a query
	 */
	private void setParameters(Query query, Object... params)
	{
		if (params != null) {
			for (int i = 0; i < params.length; i++)
				query.setParameter(i + 1, params[i]);
		}
	}

	@Override
	public E findById(K id)
	{
		return entityManager.find(entityClass, id);
	}

	/**
	 * Retrieves a non-paged query. Use with care, this method could potentially instantiate large
	 * amounts of data.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> find(String queryString, Object... params)
	{
		Query query = entityManager.createQuery(queryString);
		setParameters(query, params);
		return query.getResultList();
	}

	@Override
	public List<E> findAll()
	{
		return (List<E>) find("from " + entityClass.getName());
	}

	@Override
	public void flush()
	{
		try {
			entityManager.flush();
		} catch (TransactionRequiredException e) {
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByProperties(Map<String, Object> properties)
	{
		Iterator<String> i = properties.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		Query query = null;
		String queryString;
		
		sb.append("from");
		sb.append(" ");
		sb.append(entityClass.getSimpleName());
		sb.append(" ");
		sb.append("e");
		sb.append(" ");
		sb.append("where");
		sb.append(" ");
		while (i.hasNext()) {
			String key = i.next();
			sb.append("e.");
			sb.append(key);
			sb.append(" ");
			sb.append("=");
			sb.append(" ");
			sb.append(":");
			sb.append(key);
			sb.append(" ");
		}
		i = properties.keySet().iterator();
		queryString = sb.toString();
		query = entityManager.createQuery(queryString);
		while (i.hasNext()) {
			String key = i.next();
			Object value = properties.get(key);
			query.setParameter(key, value);
		}
		
		return query.getResultList();
	}
	
	public void close()
	{
		try {
			// Must flush before clearing, otherwise changes will be lost
			entityManager.flush();
			entityManager.clear();
			entityManager.close();
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}
}
