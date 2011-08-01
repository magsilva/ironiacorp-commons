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
 
Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.persistence.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.ironiacorp.configuration.Configuration;
import com.ironiacorp.introspector.ReflectionUtil;


/**
 * Helper class to initiate a Hibernate setup.
 */
public class HibernateDataSource extends GenericHibernateDataSource
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog(HibernateDataSource.class);

	/**
	 * The SessionFactory is responsable for the management of the persistence of the
	 * objects's state.
	 */
	private SessionFactory sessionFactory;
		
	/**
	 * Start the bootstrap.
	 * 
	 * @param home The application home directory.
	 */
	public HibernateDataSource()
	{
		log.debug( "Loading Hibernate configuration" );
		Class[] classes = null;
		AnnotationConfiguration config = new AnnotationConfiguration();
		
		this.hibernateConfig = config;
		config.addPackage("net.sf.ideais.objects");
		classes = ReflectionUtil.findClasses("net.sf.ideais.objects");
		for (Class clazz : classes) {
			log.debug("Mapping class" + clazz.getName());
			config.addAnnotatedClass(clazz);
		}
        
		config.configure();

		log.debug( "Initializing the Hibernate" );
		if ( sessionFactory != null ) {
			log.error( "Tried to initialize Hibernate, but it has already been initialized" );
			return;
		}
		sessionFactory = config.buildSessionFactory();
	}

	/**
	 * Stop the Hibernate persistence mechanism.
	 */
	public synchronized void close()
	{
		log.debug( "Stopping the Hibernate" );
		if ( sessionFactory == null ) {
			log.error( "Tried to stop Hibernate, but it is not running");
			return;
		}
		
		sessionFactory.close();
		sessionFactory = null;
	}
	
	public boolean isReady(boolean force)
	{
		return (sessionFactory == null);
	}

	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}
}