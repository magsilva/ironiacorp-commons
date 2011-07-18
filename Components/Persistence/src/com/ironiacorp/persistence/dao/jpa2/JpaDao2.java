package com.ironiacorp.persistence.dao.jpa2;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

import com.ironiacorp.persistence.dao.DAO;

public abstract class JpaDao2<PK extends Serializable, E> extends JpaDaoSupport implements DAO<PK, E>
{
    protected Class<E> entityClass;

    @SuppressWarnings("unchecked")
    public JpaDao2() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    }

    public void save(E entity) {
        getJpaTemplate().persist(entity);
    }

    public void delete(E entity) {
        getJpaTemplate().remove(entity);
    }

    public E merge(E entity) {
        return getJpaTemplate().merge(entity);
    }

    public void refresh(E entity) {
        getJpaTemplate().refresh(entity);
    }

    public E findById(PK id) {
        return getJpaTemplate().find(entityClass, id);
    }

    public void flush() {
        getJpaTemplate().flush();
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        Object res = getJpaTemplate().execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
                return q.getResultList();
            }
        });
        return (List<E>) res;
    }

    @SuppressWarnings("unchecked")
    public Integer removeAll() {
        return (Integer) getJpaTemplate().execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
            	Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h");
                return q.executeUpdate();
            }
        });
    }

}
