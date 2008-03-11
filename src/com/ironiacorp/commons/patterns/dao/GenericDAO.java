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
import java.util.HashMap;

import com.ironiacorp.commons.JavaBeanUtil;


/**
 * Implements a common set of methods for DAO classes.
 * 
 * @param <T> The business object implementation class.
 * @param <I> The primary key for the business object.
 */
public abstract class GenericDAO<T, I> implements DAO<T, I>
{
	/**
	 * Find objects whose property (key) matches the one required (value). 
	 * 
	 * @param key The property name.
	 * @param value The property value
	 * 
	 * @return Objects found (may be an empty list).
	 */
	public List<T> findByProperty(String key, Serializable value)
	{
		Map<String, Serializable> fields = new HashMap<String, Serializable>();
		fields.put(key, value);
		return findByExample(fields);
	}
	
	/**
	 * Find objects that share the same properties as example. 
	 * 
	 * @param example Example (an object instance with the desired values).
	 * The example properties whose values are 'null' will be ignored.
	 * 
	 * @return Objects found (may be an empty list).
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example)
	{
		return findByExample(JavaBeanUtil.mapBean(example));
	}
	
	/**
	 * Remove an object from the datasource.
	 * 
	 * @param entity Identificator of the object to be removed.
	 */
	public void deleteById(I id)
	{
		T object = findById(id);
		delete(object);
	}
}
