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

package com.ironiacorp.commons;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class SqlUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private SqlUtil()
	{
	}
	
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog(SqlUtil.class);
			
    /**
     * Check if the database driver is loaded.
     * 
     * @return True if the driver is loaded, False otherwise.
     */   
    public static boolean isDriverLoaded(String driver)
    {
    	// Check if the driver has been already loaded
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while (drivers.hasMoreElements()) {
    		Driver d = drivers.nextElement();
    		if (d.getClass().getName().equals(driver)) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * Unload the database driver (if it's loaded).
     */
    public static void unloadDriver(String driver)
    {
    	if (! SqlUtil.isDriverLoaded(driver)) {
    		return;
    	}
    	
    	Enumeration<Driver> drivers = DriverManager.getDrivers();
    	while (drivers.hasMoreElements()) {
    		Driver d = drivers.nextElement();
    		if (d.getClass().getName().equals(driver)) {
    			try {
    				DriverManager.deregisterDriver(d);
    			} catch (SQLException e) {
    			}
    		}
    	}
    }

    /**
     * Load the database driver (if it hasn't been already loaded).
     */
    public static void loadDriver(String driver)
    {
    	if (SqlUtil.isDriverLoaded(driver)) {
    		return;
    	}
    	
		ReflectionUtil.loadClass(driver);
    }
	
	public static void dumpSQLException(SQLException e)
	{
		log.error("SQLState: " + e.getSQLState());
		log.error("VendorError: " + e.getErrorCode());
	}
}
