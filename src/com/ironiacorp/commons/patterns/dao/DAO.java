/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons.patterns.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * The implementation is based upon the ideas and examples from Michael Slattery
 * (http://jroller.com/page/MikeSlattery/20050811) and Christian
 * (http://blog.hibernate.org/cgi-bin/blosxom.cgi/2005/09/08#genericdao).
 * 
 * @param <T> The object class whose data will be accessed.
 * @param <I> The primary key for the object.
 */
public interface DAO<T, I>
{
	/**
	 * Create a new object.
	 * @return
	 */
	T create();
	
	/**
	 * Find an object with the given id.
	 * 
	 * @param id The object identificator.
	 * @return The object found or null if not found.
	 */
	T findById(I id);
	
	/**
	 * Find objects whose property (key) matches the one required (value). 
	 * 
	 * @param key The property name.
	 * @param value The property value
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<T> findByProperty(String name, Serializable value);
	
	/**
	 * Find objects that share the same properties as example. 
	 * 
	 * @param example Example (an object instance with the desired values).
	 * The example properties whose values are 'null' will be ignored.
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<T> findByExample(T example);

	/**
	 * Find objects that matches the properties and values specified. 
	 * 
	 * @param fields Mapping of properties and values. 
	 * 
	 * @return Objects found (may be an empty list).
	 */
	List<T> findByExample(Map<String, Serializable> fields);

	/**
	 * Update an object, recording it's data back to the datasource.
	 * 
	 * @param entity Object to be updated.
	 */
	void update(T entity);
	
	/**
	 * Remove an object from the datasource.
	 * 
	 * @param entity Object to be removed.
	 */
	void delete(T entity);

	/**
	 * Remove an object from the datasource.
	 * 
	 * @param entity Identificator of the object to be removed.
	 */
	void deleteById(I id);	
}