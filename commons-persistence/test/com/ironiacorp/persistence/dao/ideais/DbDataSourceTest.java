/*
 * Copyright (C) 2006 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.persistence.dao.ideais;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.configuration.ConfigurationMap;
import com.ironiacorp.configuration.HardCodedConfiguration;
import com.ironiacorp.persistence.dao.ideais.DbDataSource;

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

	@Ignore	
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

	@Ignore	
	@Test
	public void testGetConnectionString() {
		assertEquals(DbDataSource.getConnectionString(knownDbms), knownConnectionString);
	}

	@Test(expected=RuntimeException.class) 
	public void testGetConnectionStringForUnknownDriver()
	{
		DbDataSource.getConnectionString(unknownDbms);
	}
	
	@Ignore
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
