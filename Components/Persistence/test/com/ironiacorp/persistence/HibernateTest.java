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


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
	public void testBootstrap()
	{
		Session session = sessionFactory.openSession();
		assertNotNull(session);
		session.close();
	}
}