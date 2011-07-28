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

package com.ironiacorp.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.ironiacorp.persistence.JavaBeanUtil;


/**
 * Implements a common set of methods for DAO classes.
 * 
 * @param <T> The business object implementation class.
 * @param <I> The primary key for the business object.
 */
public abstract class GenericDAO<K extends Serializable, T> implements DAO<K, T>
{
	protected Class<K> keyClass;

	protected Class<T> entityClass;
	
	/**
	 * If you create a DAO using GenericDAO<String>, ie., if you define the generic parameter
	 * type in the class declaration (public class Dummy extends GenericDAO<String>) instead
	 * of defining it at the object instantiation (dummy = new GenericDAO<String>), you may
	 * use this constructor.
	 */
	@SuppressWarnings("unchecked")
	public GenericDAO()
	{
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] t = superclass.getActualTypeArguments();
		this.keyClass = (Class<K>) t[0];
		this.entityClass = (Class<T>) t[1];
	}
	
	/**
	 * This constructor can define an DAO for any supported Java type.
	 * 
	 * @param keyClass Primary key for the entity.
	 * @param entityClass Class of the entity.
	 */
	public GenericDAO(Class<K> keyClass, Class<T> entityClass)
	{
		this.keyClass = keyClass;
		this.entityClass = entityClass;
	}
	
	/**
	 * Find objects whose property (key) matches the one required (value). 
	 * 
	 * @param key The property name.
	 * @param value The property value
	 * 
	 * @return Objects found (may be an empty list).
	 */
	public List<T> findByProperty(String key, Object value)
	{
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put(key, value);
		return findByProperties(fields);
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
		return findByProperties(JavaBeanUtil.mapBean(example));
	}
	
	public abstract List<T> findByProperties(Map<String, Object> properties);
}
