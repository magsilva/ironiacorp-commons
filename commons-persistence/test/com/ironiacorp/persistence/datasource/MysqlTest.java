/*
 * Copyright (C) 2005 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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


package com.ironiacorp.persistence.datasource;

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