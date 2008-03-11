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

package com.ironiacorp.commons.patterns;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.commons.configuration.ConfigurationMap;
import com.ironiacorp.commons.configuration.HardCodedConfiguration;
import com.ironiacorp.commons.patterns.dao.DbDataSource;


import java.sql.Connection;



public class DbDataSourceTest
{
	ConfigurationMap validConf = null;
	ConfigurationMap invalidConf = null;
	
	final private String unknownDbms = "abc123";
	
	final private String knownDbms = "mysql";
	final private String knownDriver = "com.mysql.jdbc.Driver";
	final private String knownConnectionString = "jdbc:mysql://%1$s/%2$s?user=%3$s&password=%4$s";
	final private String knownHostname = "localhost";
	final private String knownDatabase = "dotproject-dev";
	final private String knownUsername = "test";
	final private String knownPassword = "test";

	@Before
	public void setUp() throws Exception
	{
		HardCodedConfiguration conf = new HardCodedConfiguration();
		validConf = conf;
		conf.setProperty(DbDataSource.DBMS, knownDbms);
		conf.setProperty(DbDataSource.HOSTNAME, knownHostname);
		conf.setProperty(DbDataSource.DATABASE, knownDatabase);
		conf.setProperty(DbDataSource.USERNAME, knownUsername);
		conf.setProperty(DbDataSource.PASSWORD, knownPassword);
		
		conf = new HardCodedConfiguration();
		invalidConf = conf;
		conf.setProperty(DbDataSource.DBMS, unknownDbms);
	}
	
	@Test
	public void testGetDriverName()
	{
		assertEquals(DbDataSource.getDriverName(knownDbms), knownDriver);
	}

	@Test(expected=RuntimeException.class) 
	public void testGetUnknownDriverName()
	{
		DbDataSource.getDriverName(unknownDbms);
	}

	
	@Test
	public void testGetConnectionString() {
		assertEquals(DbDataSource.getConnectionString(knownDbms), knownConnectionString);
	}

	@Test(expected=RuntimeException.class) 
	public void testGetConnectionStringForUnknownDriver()
	{
		DbDataSource.getConnectionString(unknownDbms);
	}
	
	@Test
	public void testConfiguration()
	{
		DbDataSource ds = new DbDataSource();
		Connection conn = null;
		ds.setConfiguration(validConf);
		conn = ds.getConnection();
		assertNotNull(conn);
	}
}
