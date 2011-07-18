package com.ironiacorp.persistence.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DAO<K extends Serializable, E>
{
	void persist(E entity);
	
	E merge(E entity);

	void remove(E entity);
	
	void refresh(E entity);
	
	void flush(E entity);
	
	boolean contains(E entity);

	E findById(K id);
	
	List<E> find(String query, Object... params);
	
	/**
	 * Find objects whose property (key) matches the one required (value). 
	 * 
	 * @param key The property name.
	 * @param value The property value
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<E> findByProperty(String name, Serializable value);
	
	/**
	 * Find objects that share the same properties as example. 
	 * 
	 * @param example Example (an object instance with the desired values).
	 * The example properties whose values are 'null' will be ignored.
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<E> findByExample(E example);

	/**
	 * Find objects that matches the properties and values specified. 
	 * 
	 * @param fields Mapping of properties and values. 
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<E> findByExample(Map<String, Serializable> fields);

	
	List<E> findAll();
}
