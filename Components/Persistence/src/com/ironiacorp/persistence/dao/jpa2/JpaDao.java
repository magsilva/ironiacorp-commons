package com.ironiacorp.persistence.dao.jpa2;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.ironiacorp.persistence.dao.DAO;

public abstract class JpaDao<PK extends Serializable, E> implements DAO<PK, E>
{
    protected Class<E> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public JpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
	@Transactional
    public void persist(E entity) {
        entityManager.persist(entity);
    }

    @Override
	@Transactional
    public void remove(E entity) {
    	entityManager.remove(entity);
    }

    @Override
	@Transactional
    public E merge(E entity) {
        return entityManager.merge(entity);
    }

    @Override
	@Transactional
    public void refresh(E entity) {
    	entityManager.refresh(entity);
    }

    @Override
	@Transactional
    public void flush(E entity) {
    	entityManager.flush();
    }

    @Override
    public E findById(PK id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    @SuppressWarnings("unchecked")
	public List<E> findAll() {
    	return entityManager.createQuery("SELECT c from " +  entityClass.getSimpleName() + " c").getResultList();
    }
}
