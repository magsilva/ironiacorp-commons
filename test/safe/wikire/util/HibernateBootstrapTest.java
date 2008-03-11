/*
Wiki/RE - A requirements engineering wiki
Copyright (C) 2005 Marco Aurélio Graciotto Silva

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
*/

package tests.safe.wikire.util;

import safe.wikire.util.HibernateBootstrap;
import junit.framework.TestCase;

/**
 * Tests the Hibernate bootstrap.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public class HibernateBootstrapTest extends TestCase
{
	HibernateBootstrap bootstrap;

	protected void setUp() throws Exception
	{
		super.setUp();
		bootstrap = new HibernateBootstrap();
	}

	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.createDB()'
	 */
	public void testCreateDB1()
	{
		try {
			bootstrap.createDB();
			// TODO: Check if the database has been created.
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.createDB()'
	 */
	public void testCreateDB2()
	{
		try {
			bootstrap.dropDB();
			bootstrap.createDB();
			// TODO: Check if the database has been created.
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	
	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.updateDB()'
	 */
	public void testUpdateDB1()
	{
		try {
			bootstrap.updateDB();
			// TODO: There is an easy way to check if the database was updated?
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.updateDB()'
	 */
	public void testUpdateDB2()
	{
		try {
			bootstrap.dropDB();
			bootstrap.updateDB();
			// TODO: There is an easy way to check if the database was updated?
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.dropDB()'
	 */
	public void testDropDB1()
	{
		try {
			bootstrap.dropDB();
			// TODO: Check if the database has no table.
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.dropDB()'
	 */
	public void testDropDB2()
	{
		try {
			bootstrap.dropDB();
			// TODO: Check if the database has no table.
		} catch ( RuntimeException e ) {
			fail();
		}
	}

	
	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.getDropDDLScript()'
	 */
	public void testGetDropDDLScript()
	{
		bootstrap.getDropDDLScript();
		// TODO: Compare the result with the expected script.
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.getCreateDDLScript()'
	 */
	public void testGetCreateDDLScript()
	{
		bootstrap.getCreateDDLScript();
		// TODO: Compare the result with the expected script.
	}

	/*
	 * Test method for 'safe.wikire.util.HibernateBootstrap.getUpdateDDLScript()'
	 */
	public void testGetUpdateDDLScript()
	{
		bootstrap.getUpdateDDLScript();
		// TODO: Compare the result with the expected script.
	}
}