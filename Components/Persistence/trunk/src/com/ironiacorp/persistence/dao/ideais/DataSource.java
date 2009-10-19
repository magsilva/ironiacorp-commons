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

package com.ironiacorp.persistence.dao.ideais;

import com.ironiacorp.configuration.Configuration;

/**
 * A DataSource is... a data source, a place where data can be fetch from.
 * It usually is a wrapper for a database.
 * 
 * The usage is quite simple. You inject the configuration and, whenever
 * necessary, you call the 'instance()" method to the datasource (for 
 * example, if it's a database data source, it can return a 'Connection'
 * object.
 */
public interface DataSource
{
	/**
	 * Check if the datasource is ready for commence.
	 * 
	 * @param force If the datasource requires loading some library, try to
	 * load it.
	 * @return True if the datasource is ready, False otherwise.
	 */
	boolean isReady(boolean force);
	
	/**
	 * The data source usually needs to be configured somehow.
	 * 
	 * @param conf Configuration to access the data source.
	 */
	void setConfiguration(Configuration conf);
	
	/**
	 * Get an instance of the data source.
	 * 
	 * @return The data source.
	 */
	Object instance();
}
