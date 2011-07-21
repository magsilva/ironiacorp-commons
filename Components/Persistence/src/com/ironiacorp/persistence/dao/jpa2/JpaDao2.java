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

    @Override
    public void persist(E entity) {
        getJpaTemplate().persist(entity);
    }

    @Override
    public void remove(E entity) {
        getJpaTemplate().remove(entity);
    }

    @Override
    public E merge(E entity) {
        return getJpaTemplate().merge(entity);
    }

    @Override
    public void refresh(E entity) {
        getJpaTemplate().refresh(entity);
    }

    @Override
    public E findById(PK id) {
        return getJpaTemplate().find(entityClass, id);
    }

    @Override
    public void flush(E entity) {
        getJpaTemplate().flush();
    }

    @Override
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
}