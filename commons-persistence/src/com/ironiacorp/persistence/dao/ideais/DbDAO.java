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

Copyright (C) 2006 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.persistence.dao.ideais;

import java.io.Serializable;
import java.sql.Connection;

import com.ironiacorp.configuration.Configuration;
import com.ironiacorp.persistence.dao.GenericDAO;


/**
 * Data Transfer Object for a task available at a DotProject instance.
 * 
 */
public abstract class DbDAO<K extends Serializable, T> extends GenericDAO<K, T>
{
	protected Connection conn;
	
	protected DbDataSource ds;
	
	public DbDAO(Configuration conf)
	{
		DbDataSource ds = new DbDataSource();
		ds.setConfiguration(conf);
		conn = ds.getConnection();
	}

	public Connection getConn()
	{
		return conn;
	}

	public void setConn(Connection conn)
	{
		this.conn = conn;
	}

	public DbDataSource getDs()
	{
		return ds;
	}

	public void setDs(DbDataSource ds)
	{
		this.ds = ds;
		this.conn = ds.getConnection();
	}
	
	
}