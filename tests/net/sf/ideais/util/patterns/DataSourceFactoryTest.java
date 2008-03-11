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

package tests.net.sf.ideais.util.patterns;

import static org.junit.Assert.*;

import net.sf.ideais.util.ArrayUtil;
import net.sf.ideais.util.conf.Configuration;
import net.sf.ideais.util.conf.HardCodedConfiguration;
import net.sf.ideais.util.patterns.DataSource;
import net.sf.ideais.util.patterns.DataSourceFactory;
import net.sf.ideais.util.patterns.DbDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataSourceFactoryTest
{
	private static String validDataSource = DbDataSource.class.getName();
	private static String invalidDataSource = "rubsrubs";
	
	public class DummyDataSource implements DataSource
	{
		public boolean isReady(boolean force)
		{
			return false;
		}

		public Object instance()
		{
			return null;
		}

		public void setConfiguration(Configuration conf)
		{		
		}	
	}

	private Configuration getValidConfiguration()
	{
		String knownDbms = "mysql";
		String knownHostname = "localhost";
		String knownDatabase = "dotproject-dev";
		String knownUsername = "test";
		String knownPassword = "test";

		HardCodedConfiguration conf = new HardCodedConfiguration();
		conf.setProperty(DbDataSource.DBMS, knownDbms);
		conf.setProperty(DbDataSource.HOSTNAME, knownHostname);
		conf.setProperty(DbDataSource.DATABASE, knownDatabase);
		conf.setProperty(DbDataSource.USERNAME, knownUsername);
		conf.setProperty(DbDataSource.PASSWORD, knownPassword);
		
		return conf;
	}

	
	@Before
	public void setUp() throws Exception
	{
		DataSourceFactory.reset();
	}

	@After
	public void tearDown() throws Exception
	{
		DataSourceFactory.reset();
	}

	
	@Test
	public void testManufacture()
	{
		Configuration conf = getValidConfiguration();
		DataSource ds = DataSourceFactory.manufacture(validDataSource, conf);
		assertNotNull(ds);
	}

	@Test
	public void testGetPortfolio()
	{
		DataSourceFactory.addProductLine(invalidDataSource);
		String[] portfolio = DataSourceFactory.getPortfolio();
		assertNotSame(ArrayUtil.find(portfolio, invalidDataSource), -1);
	}

	@Test
	public void testCanManufacture1()
	{
		assertTrue(DataSourceFactory.canManufacture(validDataSource));
	}

	@Test
	public void testCanManufacture2()
	{
		assertFalse(DataSourceFactory.canManufacture(invalidDataSource));
	}

	
	@Test
	public void testIsReady1()
	{
		assertTrue(DataSourceFactory.isReady());
	}

	@Test
	public void testIsReady2()
	{
		DataSourceFactory.addProductLine(invalidDataSource);
		assertFalse(DataSourceFactory.isReady());
	}
}
