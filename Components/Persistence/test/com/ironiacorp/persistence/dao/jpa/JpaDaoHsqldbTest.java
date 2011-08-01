/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.persistence.dao.jpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.persistence.dao.DAO;
import com.ironiacorp.persistence.example.Person;

import static org.junit.Assert.*;

public class JpaDaoHsqldbTest
{
    private EntityManagerFactory emFactory;

    private EntityManager em;

    private Connection connection;

    @Before
    public void setUp() throws Exception {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during HSQL database startup.");
        }
        try {
            emFactory = Persistence.createEntityManagerFactory("testPU1");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception during JPA EntityManager instanciation.");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
        try {
            connection.createStatement().execute("SHUTDOWN");
        } catch (Exception ex) {}
    }

    @Test
    public void testTx_SanityCheck1() throws Exception
    {
    	EntityTransaction tx1 = em.getTransaction();
    	EntityTransaction tx2 = em.getTransaction();
    	assertSame(tx1, tx2);
    }

    @Test
    public void testTx_SanityCheck2() throws Exception
    {
    	EntityTransaction tx1 = em.getTransaction();
    	assertFalse(tx1.isActive());
    	tx1.begin();
    	assertTrue(tx1.isActive());
    	tx1.commit();
    	assertFalse(tx1.isActive());
    }

    @Test
    public void testTx_SanityCheck3() throws Exception
    {
    	EntityTransaction tx1 = em.getTransaction();
    	EntityTransaction tx2;
    	assertFalse(tx1.isActive());
    	tx1.begin();
    	tx1.commit();
    	tx2 = em.getTransaction();
    	tx2.begin();
    }
    

    
    @Test
    public void testPersistence() throws Exception
    {
    	try {
           em.getTransaction().begin();
           Person p = new Person();
           Person p2;
           p.setName("Marco Aurélio");
           p.setBirthday(new Date(1980, 10, 30));
           em.persist(p);
           assertTrue(em.contains(p));

           p2 = em.find(Person.class, 1);
           assertNotNull(p2);
           assertEquals(p, p2);
           assertSame(p,  p2);
           
           em.remove(p);
           assertFalse(em.contains(p));
           
           p2 = em.find(Person.class, 1);
           assertNull(p2);

           // em.getTransaction().commit();
        } finally {
            em.getTransaction().rollback();
        }
    }
    
    @Test
    public void testDAO() throws Exception
    {
        EntityTransaction tx = em.getTransaction();
    	try {
	    	DAO<Integer, Person> dao = new JPA_DAO<Integer, Person>(Integer.class, Person.class);
	    	JPA_DAO<Integer, Person> jpaDao = (JPA_DAO<Integer, Person>) dao;
	    	List<Person> people;
	        Person p = new Person();
	        Person p2;
	        
	        tx.begin();
	        p.setName("Marco Aurélio");
	        p.setBirthday(new Date(1980, 10, 30));
	        jpaDao.setEntityManager(em);
	        jpaDao.persist(p);
	        
	        // people = dao.findByProperty("name", "Marco Aurélio");
	        // assertEquals(1, people.size());
	        p2 = dao.findById(1);
	        assertNotNull(p2);
	        assertEquals(p, p2);
	        assertSame(p,  p2);
	        
	        people = dao.findAll();
	        assertEquals(1, people.size());
	        tx.commit();
        } catch (Exception e) {
            tx.rollback();
        }
    }
    
    @Test
    public void testDAO_AutoCommit() throws Exception
    {
    	DAO<Integer, Person> dao = new JPA_DAO<Integer, Person>(Integer.class, Person.class);
    	JPA_DAO<Integer, Person> jpaDao = (JPA_DAO<Integer, Person>) dao;
    	List<Person> people;
        Person p = new Person();
        Person p2;
        
        em.getTransaction().begin();
        p.setName("Marco Aurélio");
        p.setBirthday(new Date(1980, 10, 30));
        jpaDao.setEntityManager(em);
        jpaDao.setAutoCommit(true);
        dao.persist(p);
        
        // people = dao.findByProperty("name", "Marco Aurélio");
        // assertEquals(1, people.size());
        p2 = dao.findById(1);
        assertNotNull(p2);
        assertEquals(p, p2);
        assertSame(p,  p2);
        
        people = dao.findAll();
        assertEquals(1, people.size());
    }
}