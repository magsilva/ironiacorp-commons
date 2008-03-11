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

package com.ironiacorp.commons.patterns.dao;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ejb.Ejb3Configuration;

import com.ironiacorp.commons.ReflectionUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class HibernateEntityManagerDataSource extends GenericHibernateDataSource
{
	/**
	* Commons Logging instance.
	*/
	static Log log = LogFactory.getLog(HibernateEntityManagerDataSource.class);

	/**
	 * Configuration used by Hibernate, responsable for mapping the objects to an
	 * relational database.
	 */
	private Ejb3Configuration ejb3Config;
	
	/**
	 * The EntityManagerFactory is responsable for the management of the persistence of the
	 * objects's state.
	 */
	EntityManagerFactory emf;

	
	/**
	 * Start the bootstrap.
	 * 
	 * @param home The application home directory.
	 */
	public HibernateEntityManagerDataSource(List<String> packages)
	{
		log.debug("Loading Hibernate Entity Manager configuration");
		Class<?>[] classes = null;

		ejb3Config = new Ejb3Configuration();
		ejb3Config.configure("hibernate.cfg.xml");
		Iterator<String> i = packages.iterator();
		while (i.hasNext()) {
			String packageName = i.next();
			ejb3Config.addPackage(packageName);
			classes = ReflectionUtil.findClasses(packageName);
			for (Class<?> clazz : classes) {
				log.debug("Mapping class" + clazz.getName());
				ejb3Config.addAnnotatedClass(clazz);
			}
		}
		hibernateConfig = ejb3Config.getHibernateConfiguration();
		log.debug("Hibernate Entity Manager configuration loaded");
		
		
		log.debug("Initilizing Hibernate Entity Manager Factory");
		emf = ejb3Config.buildEntityManagerFactory();
	    log.debug("Hibernate Entity Manager Factore initialized");
	}

	/**
	 * Stop the Hibernate persistence mechanism.
	 */
	public synchronized void close()
	{
		log.debug( "Stopping the Hibernate" );
		if (emf == null ) {
			log.error( "Tried to stop Hibernate, but it is not running");
			return;
		}
		
		emf.close();
		emf = null;
	}
	
	public EntityManager getConnection()
	{
		log.debug("Loading Hibernate Entity Manager");
		EntityManager em = emf.createEntityManager();
	    log.debug("Hibernate Entity Manager loaded");
	    return em;
	}

	public boolean isReady(boolean force)
	{
		return (emf != null);
	}
}