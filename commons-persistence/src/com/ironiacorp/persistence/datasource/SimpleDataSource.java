/*
You are welcome to do whatever you want to with this source file provided
that you maintain this comment fragment (between the dashed lines). Modify
it, change the package name, change the class name ... personal or business
use ... sell it, share it ... add a copyright for the portions you add ...

My goal in giving this away and maintaining the copyright is to hopefully
direct developers back to JavaRanch.

The original source can be found at <a href="http://www.javaranch.com">JavaRanch</a>.
*/

package com.ironiacorp.persistence.datasource;

import javax.sql.DataSource;
import javax.naming.Reference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.PrintWriter;


/**
 * A very simple datasource. Creates a new Connection to the database
 * everytime it's ask for one so.
 */
class SimpleDataSource extends Reference implements DataSource
{
	/** Log stream */
    protected PrintWriter logWriter = null;
	
	String dbDriver;
	String dbServer;
	String dbLogin;
	String dbPassword;

	SimpleDataSource()
	{
		super(SimpleDataSource.class.getName());
	}

	/**
	 * Method getConnection creates Connection to the database.
	 * 
	 * 
	 * @return New Connection each time.
	 * 
	 * @throws java.sql.SQLException
	 * 
	 */
	public Connection getConnection() throws java.sql.SQLException
	{
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException cnfe) {
			throw new SQLException(cnfe.getMessage());
		}
		return DriverManager.getConnection(dbServer, dbLogin, dbPassword);
	}

	public Connection getConnection(String parm1, String parm2) throws java.sql.SQLException
	{
		return getConnection();
	}

	public PrintWriter getLogWriter() throws java.sql.SQLException
	{
		return logWriter;
	}

	public int getLoginTimeout() throws java.sql.SQLException
	{
		throw new UnsupportedOperationException("Method getLoginTimeout() not yet implemented.");
	}

	public void setLogWriter(PrintWriter logWriter) throws java.sql.SQLException
	{
		this.logWriter = logWriter;
	}

	public void setLoginTimeout(int parm1) throws java.sql.SQLException
	{
		throw new UnsupportedOperationException("Method setLoginTimeout() not yet implemented.");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException
	{
		return iface.isInstance(this);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException
	{
		try {
			return iface.cast(this);
		} catch (ClassCastException cce) {
			throw new SQLException("Unable to unwrap to " + iface.toString());
		}
	}
	
	
}
