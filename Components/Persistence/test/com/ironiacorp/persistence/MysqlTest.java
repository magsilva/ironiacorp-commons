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

Copyright (C) 2005 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class MysqlTest
{
	@Test
	public void testDriver()
	{
		Class clazz = null;
		try { 
			clazz = Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
		}
		assertNotNull(clazz);
	}

	@Test
	public void testDriverInitialization()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver");
		} catch (ExceptionInInitializerError eiie) {
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testDriverLinkage()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver");
		} catch (LinkageError le) {
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testDriverClassNotFound()
	{
		try {
            Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			fail();
		} catch (Exception e) {
		}
	}

	@Ignore
	@Test
	public void testConnection()
	{
		Connection conn = null;
		try {
            Class.forName( "com.mysql.jdbc.Driver" );
			conn = DriverManager.getConnection( "jdbc:mysql://" +
				MysqlConstants.localHostname + "/" +
				MysqlConstants.localDatabase + "?" +
				"user=" + MysqlConstants.localUsername + "&" +
				"password=" + MysqlConstants.localPassword + "&" +
				"&useCompression=true&autoReconnect=true" +
				"&cacheCallableStmts=true&cachePrepStmts=true&cacheResultSetMetadata=true&" +
				"useFastIntParsing=true&useNewIO=true"
			);
			assertNotNull(conn);
		} catch (SQLException ex) {
			fail();
		} catch (Exception e) {
			fail();
		} finally {
			try {
				if (conn != null) conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	@Ignore
	@Test
	public void testSimpleQueryStatement()
	{
		ResultSet rs = null;
		Connection conn = null;
		try {
		    Class.forName( "com.mysql.jdbc.Driver" );
			conn = DriverManager.getConnection( "jdbc:mysql://" +
				MysqlConstants.localHostname + "/" +
				MysqlConstants.localDatabase + "?" +
				"user=" + MysqlConstants.localUsername + "&" +
				"password=" + MysqlConstants.localPassword + "&" +
				"&useCompression=true&autoReconnect=true" +
				"&cacheCallableStmts=true&cachePrepStmts=true&cacheResultSetMetadata=true&" +
				"useFastIntParsing=true&useNewIO=true"
			);
			Statement stmt = conn.createStatement();
			stmt.execute("SELECT 1");
			rs = stmt.getResultSet();
			assertNotNull(rs);
		} catch (SQLException ex) {
			fail();
		} catch (Exception e) {
			fail();
		} finally {
			try {
				if (rs != null) rs.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
			}
		}
	}

}