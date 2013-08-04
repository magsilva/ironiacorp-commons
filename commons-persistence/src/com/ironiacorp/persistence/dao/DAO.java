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

package com.ironiacorp.persistence.dao;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object. It handles the persistence of objects within an
 * application.
 * 
 * An DAO is transaction agnostic. It is highly discouraged (not to say
 * forbidden) to create, commit or rollback transactions with a DAO
 * object. If any transaction handling is done, it must be entirely under
 * control of the DAO, without any further requirement in the application.
 * We recommend that transaction be handled in the application scope, using
 * Spring or other related framework. If you really need a DAO that defines
 * transactional methods, extend one the classes and keep it in your
 * application.
 * 
 * We adopt the following convention in the interface (and implementation
 * classes): whenever we refer to an 'object', it is an unmanaged object
 * (ie., one that has been removed and is no more managed); if we refer to
 * entity, it is a managed object (ie., one that has been persisted or
 * retrieved using the DAO).
 *
 * @param <K> Primary key for the object.
 * @param <E> Object to be persisted.
 */
public interface DAO<K extends Serializable, E> extends Closeable
{
	/**
	 * Persist and manage an object.
	 * 
	 * @param entity Object to be persisted.
	 * @throws IllegalArgumentException if the entity already exists or cannot
	 * be managed.
	 * @throws UnsupportedOperationException on any error that takes place
	 * when storing the entity. It is usually an internal error that depends
	 * upon the particular DAO implementation. It will always have a cause
	 * exception.
	 */
	void persist(E entity);

	/**
	 * Create a managed object (entity) using the object's properties.
	 * 
	 * @param entity Object from which a managed object will be created and persisted.
	 * @return Managed object.
	 * 
	 * @throws UnsupportedOperationException on any error that takes place
	 * when storing the entity. It is usually an internal error that depends
	 * upon the particular DAO implementation. It will always have a cause
	 * exception.
	 */
	E merge(E object);

	/**
	 * Remove the object and stop managing it.
	 * 
	 * @param entity Object to be removed.
	 * 
	 * @throws UnsupportedOperationException on any error that takes place
	 * when storing the entity. It is usually an internal error that depends
	 * upon the particular DAO implementation. It will always have a cause
	 * exception.
	 */
	void remove(E entity);
	
	/**
	 * Reload data from entity from the storage device. This should not be required
	 * unless you change the object using another mechanism than this DAO or by
	 * another application.
	 * 
	 * @param entity Managed object to be refreshed.
	 * 
	 * @throws UnsupportedOperationException on any error that takes place
	 * when storing the entity. It is usually an internal error that depends
	 * upon the particular DAO implementation. It will always have a cause
	 * exception.
	 */
	void refresh(E entity);
	
	/**
	 * Flush all objects' data to the storage device.
	 * 
	 * @throws UnsupportedOperationException on any error that takes place
	 * when storing the entity. It is usually an internal error that depends
	 * upon the particular DAO implementation. It will always have a cause
	 * exception.
	 */
	void flush();
	
	/**
	 * Check whether the object has been persisted.
	 * 
	 * @param entity Entity to be checked.
	 * @return False if it does not exists in the persistence layer, true otherwise.
	 */
	boolean contains(E object);

	/**
	 * Find an object by its primary key.
	 * 
	 * @param id Primary key.
	 * @return Managed object (or null if none has been found).
	 */
	E findById(K id);
	
	/**
	 * Find an object using a query.
	 * 
	 * @param query Query (with parameter's placeholders).
	 * @param params Values for the parameters.
	 * @return Managed objects found (may be an empty list).
	 */
	List<E> find(String query, Object... params);
	
	/**
	 * Find objects whose property (key) matches the one required (value). 
	 * 
	 * @param key The property name.
	 * @param value The property value
	 * 
	 * @return Objects found .
	 */
	List<E> findByProperty(String name, Object value);

	/**
	 * Find objects that matches the properties and values specified. 
	 * 
	 * @param fields Mapping of properties and values. 
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<E> findByProperties(Map<String, Object> fields);
	
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
	 * Retrieves all the objects that have been stored.
	 * 
	 * @return List of all objects.
	 */
	List<E> findAll();
}
