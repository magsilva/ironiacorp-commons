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


public class HibernateEntityManagerDAO<T, I> extends GenericDAO<T, I>
{
	private HibernateEntityManagerDataSource ds;

	public HibernateEntityManagerDAO(HibernateEntityManagerDataSource ds)
	{
		this.ds = ds;
	}
	
	@Override
	public T create()
	{
		// ds.getConnection().persist(arg0)
		return null;
	}

	@Override
	public void delete(Object entity)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public List<T> findByExample(Map<String, Serializable> fields)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findById(I id)
	{
		// ds.getConnection().find(Object.class, objectId)
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Object entity)
	{
		// ds.getConnection().persist(arg0).flush().refresh(entity);
		
		// TODO Auto-generated method stub

	}

}
