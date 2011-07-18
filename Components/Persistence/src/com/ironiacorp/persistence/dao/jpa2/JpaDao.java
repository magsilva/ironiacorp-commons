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

	@Transactional
    public void save(E entity) {
        entityManager.persist(entity);
    }

	@Transactional
    public void delete(E entity) {
    	entityManager.remove(entity);
    }

	@Transactional
    public E merge(E entity) {
        return entityManager.merge(entity);
    }

	@Transactional
    public void refresh(E entity) {
    	entityManager.refresh(entity);
    }

	@Transactional
    public void flush() {
    	entityManager.flush();
    }

    public E findById(PK id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
	public List<E> findAll() {
    	return entityManager.createQuery("SELECT c from " +  entityClass.getSimpleName() + " c").getResultList();
    }
}
