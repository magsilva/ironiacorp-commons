package com.ironiacorp.persistence.dao.jpa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.persistence.dao.DAO;
import com.ironiacorp.persistence.dao.ideais.DataSource;

public class JPA_DAOTest
{

	@Before
	public void setUp() throws Exception
	{
	}

	@Ignore
	@Test
	public void testJPA_DAO()
	{
		DAO<Long, DataSource> dao = new JPA_DAO<Long, DataSource>();
		assertEquals(true, true);
	}

}
