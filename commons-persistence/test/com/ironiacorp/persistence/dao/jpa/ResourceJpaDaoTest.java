package com.ironiacorp.persistence.dao.jpa;


import javax.inject.Inject;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ironiacorp.resource.Resource;
import com.ironiacorp.resource.resources.dao.ResourceDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:META-INF/applicationContext.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
public class ResourceJpaDaoTest
{
	@Inject
	// @javax.annotation.Resource
    private ResourceDao resourceDao;


	@Before
	public void setUp()
	{
	}

    private long createAndSaveResource() {
    	Resource resource = new Resource();
    	resource.setName("Test");
        resourceDao.persist(resource);
        resourceDao.flush();
        return resource.getId();
    }

    @Test
    @Rollback(true)
    public void testGetById() {
    	long id = createAndSaveResource();
        Resource resource = resourceDao.findById(id);
        assertNotNull(resource);
        assertEquals(id, resource.getId());
    }

    @Test
    @Rollback(true)
    public void testGetById_InvalidId() {
        Resource resource = resourceDao.findById((long) -1);
        assertNull(resource);
    }


    @Test
    @Rollback(true)
    public void testSave() {
        long id = createAndSaveResource();
        Resource resource = resourceDao.findById(id);
        assertEquals(id, resource.getId());
    }

    @Test
    @Rollback(true)
    public void testDelete() {
        long id = createAndSaveResource();
        Resource resource = resourceDao.findById(id);
        resourceDao.remove(resource);
        resourceDao.flush();
        resource = resourceDao.findById(id);
        assertNull(resource);
    }

    @Test
    @Rollback(true)
    public void testUpdate() {
        long id = createAndSaveResource();
        Resource resource = resourceDao.findById(id);
        assertEquals(id, resource.getId());

        resourceDao.persist(resource);
        resourceDao.flush();

        resourceDao.refresh(resource);
        assertEquals(id, resource.getId());
    }
}