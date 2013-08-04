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


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;


import com.ironiacorp.configuration.Configuration;
import com.ironiacorp.configuration.ConfigurationMap;
import com.ironiacorp.persistence.SqlUtil;


//javax.sql.DataSource ds1 = (javax.sql.DataSource)ctx.lookup("java:comp/env/jdbc/database"); 

/**
 * Datasource for database access.
 * 
 * @author magsilva
 */
public class DbDataSource implements DataSource
{
    private String dbms;
    
    private String hostname;
    
    private String database;
    
    private String username;
    
    private String password;
    
    /**
     * The key (name) for the DBMS (JDBC) driver's configuration item.
     */
    public static final String DBMS = "dbms";

    /**
     * The key (name) for the hostname's configuration item.
     */
    public static final String HOSTNAME = "hostname";

    /**
     * The key (name) for the database's configuration item.
     */
    public static final String DATABASE = "database";
    
    /**
     * The key (name) for the username's configuration item.
     */
    public static final String USERNAME = "username";
    
    /**
     * The key (name) for the password's configuration item.
     */
    public static final String PASSWORD = "password";
        
    /**
    * Creates a new instance of DbDataSource.
    */
    public DbDataSource()
    {
    }

    /**
	 * Create a new instance of DbDataSource and load a configuration.
	 */
	public DbDataSource(ConfigurationMap conf)
	{
		setConfiguration(conf);
	}

    /**
     * Unload the database driver (if it's loaded).
     */
    private void unloadDriver()
    {
    	// We cannot unload an unknown driver.
    	if (dbms == null) {
    		return;
    	}
    
    	String driver = getDriverName(dbms);
    	SqlUtil.unloadDriver(driver);
    }

    /**
     * Load the database driver (if it hasn't been already loaded).
     */
    private void loadDriver()
    {
    	String driver = getDriverName(dbms);
    	SqlUtil.loadDriver(driver);
    }

    /**
     * Set a new DBMS. This must not be null or an empty string.
     * 
     * @param dbms The DBMS to be used.
     * @throws IllegalArgumentException
     */
	private void setDbms(String dbms)
	{
		if (dbms == null || dbms.trim().length() == 0) {
			throw new IllegalArgumentException("You cannot leave the DBMS empty");
		}
		this.dbms = dbms;
	}

    /**
     * Set a new hostname. This must not be null or an empty string.
     * 
     * @param hostname The hostname to be used.
     * @throws IllegalArgumentException
     */
	private void setHostname(String hostname)
	{
		if (hostname == null || hostname.trim().length() == 0) {
			throw new IllegalArgumentException("You cannot leave the hostname empty");
		}
		this.hostname = hostname;
	}
	
	/**
     * Set a new database. This must not be null or an empty string.
     * 
     * @param database The database to be used.
     * @throws IllegalArgumentException
     */
	private void setDatabase(String database)
	{
		if (database == null || database.trim().length() == 0) {
			throw new IllegalArgumentException("You cannot leave the database empty");
		}
		this.database = database;
	}

	/**
	 * Set a new password. If the password is null, it will be converted to
	 * an empty string.
	 * 
	 * @param password The password.
	 */
	private void setPassword(String password)
	{
		if (password == null) {
			password = "";
		}
		this.password = password;
	}

	/**
	 * Set a new username. If the username is null, it will be converted to
	 * an empty string.
	 * 
	 * @param username The username.
	 */
	private void setUsername(String username)
	{
		if (password == null) {
			password = "";
		}
		this.username = username;
	}

	/**
	 * Load a new configuration.
	 * 
	 * If there's a driver load, it tries to unload the driver before setting
	 * the new configuration.
	 * 
	 * After setting the new configuration, it will load the driver and connect
	 * to the database.
	 */
	public void setConfiguration(Configuration conf)
    {
		if (! (conf instanceof ConfigurationMap)) {
			throw new IllegalArgumentException("Invalid configuration");
		}
		ConfigurationMap confMap = (ConfigurationMap) conf;
		
   		unloadDriver();
    	
   		setDbms((String) confMap.getProperty(DBMS));
    	setHostname((String) confMap.getProperty(HOSTNAME));
        setDatabase((String) confMap.getProperty(DATABASE));
        setUsername((String) confMap.getProperty(USERNAME));
        setPassword((String) confMap.getProperty(PASSWORD));
        
        loadDriver();
    }
	
    /**
     * Get the driver's name for a specific DBMS.
     * 
     * @param dbms The database management system we need a suitable driver for.
     * 
     * @return The driver name (a Java class's name).
     */
    public static String getDriverName(String dbms)
    {
    	InputStream in = DbDataSource.class.getResourceAsStream("dbDriver.properties");
    	PropertyResourceBundle res = null;
    	try {
    		res = new PropertyResourceBundle(in);
    	} catch (IOException ioe) {
    	}
    	
    	Enumeration<String> knownDrivers = res.getKeys();
    	while (knownDrivers.hasMoreElements()) {
    		if (knownDrivers.nextElement().equals(dbms)) {
    			return res.getString(dbms);
    		}
    	}
    	
    	throw new RuntimeException("Unknown DBMS");   		
    }
    
    /**
     * Get the connection string (URL) for a specific DBMS.
     * 
     * @param dbms The database management system we need a suitable connection string for.
     * 
     * @return The connection string.
     */
    public static String getConnectionString(String dbms)
    {
    	InputStream in = DbDataSource.class.getResourceAsStream("dbConnString.properties");
    	PropertyResourceBundle res = null;
    	try {
    		res = new PropertyResourceBundle(in);
    	} catch (IOException ioe) {
    	}
    	
    	Enumeration<String> knownDrivers = res.getKeys();
    	while (knownDrivers.hasMoreElements()) {
    		if (knownDrivers.nextElement().equals(dbms)) {
    			return res.getString(dbms);
    		}
    	}
    	
    	throw new RuntimeException("Unknown SBGD");
    }
	
    /**
     * Connect to the database.
     */
	public Connection getConnection()
	{
    	Connection conn = null;
    	try {
    		String connString = getConnectionString(dbms);
    		conn = DriverManager.getConnection(
    				String.format(connString, hostname, database, username, password));
    		
    		// TODO: Work around a MySQL bug with PreparedStatements (bug 15).
    		/* FIX:
    		if (dbms.equals("mysql")) {
    			com.mysql.jdbc.Connection mysqlConn = (com.mysql.jdbc.Connection)conn;
    			if (mysqlConn.versionMeetsMinimum(5, 0, 0)) {
    				mysqlConn.setUseServerPrepStmts(false);
    			}
    		}
    		*/
    	} catch (SQLException e) {
    		// ExceptionUtil.dumpException(e);
    	}

		return conn;
	}

	/**
	 * Check if the DbDataSource is ready.
	 * 
	 * Actually, we cannot tell that before loading the configuration.
	 */
	public boolean isReady(boolean force)
	{
		return true;
	}

	/**
	 * Build
	 */
	public Object instance()
	{
		return this;
	}
}
