package com.ironiacorp.persistence.dao.jpa;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ironiacorp.resource.Resource;
import com.ironiacorp.resource.ResourceFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/applicationContext.xml"})
public class ResourceFactoryTest
{
	@Inject
	protected ResourceFactory resourceFactory;

	@Before
	public void setUp()
	{
		resourceFactory.reset();
	}

	@Test
	public void testSetStartId_ValidValue()
	{
		resourceFactory.setStartId(1);
	}

	@Test
	public void testSetStartId_ValidValue_Equal()
	{
		resourceFactory.setStartId(0);
	}


	@Test(expected=IllegalArgumentException.class)
	public void testSetStartId_InvalidValue_Lower()
	{
		resourceFactory.setStartId(-1);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCreate_Fail_Uninitialized()
	{
		resourceFactory.setStartId(0);
		resourceFactory.create(Resource.class);
	}

	@Test
	public void testCreate_OneInstance()
	{
		resourceFactory.setStartId(0);
		Resource resource = resourceFactory.create(Resource.class);
		assertNotNull(resource);
	}

	@Test
	public void testCreate_ManyInstantes()
	{
		resourceFactory.setStartId(0);
		Resource resource1 = resourceFactory.create(Resource.class);
		Resource resource2 = resourceFactory.create(Resource.class);
		Resource resource3 = resourceFactory.create(Resource.class);
		assertEquals(0, resource1.getId());
		assertEquals(1, resource2.getId());
		assertEquals(2, resource3.getId());
	}
}
