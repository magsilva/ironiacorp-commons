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


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.persistence.datasource.HibernateDataSource;


public class HibernateTest
{
	SessionFactory sessionFactory = null;

	@Before
	public void setUp() throws Exception
	{
		HibernateDataSource bootstrap = new HibernateDataSource();
		Configuration config = bootstrap.getConfig();
		sessionFactory = config.buildSessionFactory();
	}

	@After
	public void tearDown() throws Exception
	{
		sessionFactory.close();
	}

	@Test
	@Ignore
	public void testBootstrap()
	{
		Session session = sessionFactory.openSession();
		assertNotNull(session);
		session.close();
	}
}