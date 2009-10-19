package com.ironiacorp.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class GenericDAO<K, E> implements DAO<K, E>
{
	protected Class<K> keyClass;

	protected Class<E> entityClass;
	
	@SuppressWarnings("unchecked")
	public GenericDAO()
	{
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] t = superclass.getActualTypeArguments();
		this.keyClass = (Class<K>) t[0];
		this.entityClass = (Class<E>) t[1];
	}
}
