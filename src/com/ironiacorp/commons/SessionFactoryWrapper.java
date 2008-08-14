/*
Wiki/RE - A requirements engineering wiki
Copyright (C) 2005 Marco Aurélio Graciotto Silva

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
*/

package com.ironiacorp.commons;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Interceptor;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.engine.FilterDefinition;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.stat.Statistics;

/**
 * A SessionFactoryWrapper. Basically it delegates everything to a real
 * SessionFactory. The main difference is that it doesn't allow to
 * close the factory or flush the cache.
 * 
 * This class main purpose is to be shared safely with the projects (so
 * we can start Hibernate sessions).
 * 
 * @author Marco Aurélio Graciotto Silva
 */
@SuppressWarnings("unchecked")
public abstract class SessionFactoryWrapper implements SessionFactory
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( SessionFactoryWrapper.class ); 
			
	private static final long serialVersionUID = 1L;
	
	/**
	 * The SessionFactory we delegate most of the methods. 
	 */
	private SessionFactory sessionFactory;
	
	/**
	 * 
	 */
	public SessionFactoryWrapper( SessionFactory realSessionFactory )
	{
		super();
		if ( realSessionFactory == null ) {
			throw new IllegalArgumentException( "Must supply a valid Hibernate session factory" );
		}
		this.sessionFactory = realSessionFactory;
		log.debug( "SessionFactory wrapper for " + realSessionFactory + "is set" );
	}

	/**
	 * @see org.hibernate.SessionFactory#openSession(java.sql.Connection)
	 */
	public Session openSession( Connection arg0 )
	{
		return sessionFactory.openSession( arg0 );
	}

	/**
	 * @see org.hibernate.SessionFactory#openSession(org.hibernate.Interceptor)
	 */
	public Session openSession( Interceptor arg0 )
	{
		return sessionFactory.openSession( arg0 );
	}

	/**
	 * @see org.hibernate.SessionFactory#openSession(java.sql.Connection, org.hibernate.Interceptor)
	 */
	public Session openSession( Connection arg0, Interceptor arg1 )
	{
		return sessionFactory.openSession( arg0, arg1 );
	}

	/**
	 * @see org.hibernate.SessionFactory#openSession()
	 */
	public Session openSession()
	{
		return sessionFactory.openSession();
	}

	/**
	 * Operation not allowed.
	 * 
	 * @see org.hibernate.SessionFactory#getCurrentSession()
	 */
	public Session getCurrentSession()
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#getClassMetadata(java.lang.Class)
	 */
	public ClassMetadata getClassMetadata( Class arg0 )
	{
		return sessionFactory.getClassMetadata( arg0 );
	}

	/**
	 * @see org.hibernate.SessionFactory#getClassMetadata(java.lang.String)
	 */
	public ClassMetadata getClassMetadata( String arg0 )
	{
		return sessionFactory.getClassMetadata( arg0 );
	}
	
	/**
	 * @see org.hibernate.SessionFactory#getCollectionMetadata(java.lang.String)
	 */
	public CollectionMetadata getCollectionMetadata( String arg0 )
	{	
		return sessionFactory.getCollectionMetadata( arg0 );
	}

	/**
	 * @see org.hibernate.SessionFactory#getAllClassMetadata()
	 */
	public Map getAllClassMetadata()
	{
		return sessionFactory.getAllClassMetadata();
	}

	/**
	 * @see org.hibernate.SessionFactory#getAllCollectionMetadata()
	 */
	public Map getAllCollectionMetadata()
	{
		return sessionFactory.getAllCollectionMetadata();
	}

	/**
	 * @see org.hibernate.SessionFactory#getStatistics()
	 */
	public Statistics getStatistics()
	{
		return sessionFactory.getStatistics();
	}

	/**
	 * Operation not allowed.
	 * 
	 * @see org.hibernate.SessionFactory#close()
	 */
	public void close()
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#isClosed()
	 */
	public boolean isClosed()
	{
		return sessionFactory.isClosed();
	}

	/**
	 * @see org.hibernate.SessionFactory#evict(java.lang.Class)
	 */
	public void evict( Class arg0 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#evict(java.lang.Class, java.io.Serializable)
	 */
	public void evict( Class arg0, Serializable arg1 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#evictEntity(java.lang.String)
	 */
	public void evictEntity( String arg0 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#evictEntity(java.lang.String, java.io.Serializable)
	 */
	public void evictEntity( String arg0, Serializable arg1 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );

	}

	/**
	 * @see org.hibernate.SessionFactory#evictCollection(java.lang.String)
	 */
	public void evictCollection( String arg0 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#evictCollection(java.lang.String, java.io.Serializable)
	 */
	public void evictCollection( String arg0, Serializable arg1 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see org.hibernate.SessionFactory#evictQueries()
	 */
	public void evictQueries()
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/* (non-Javadoc)
	 * @see org.hibernate.SessionFactory#evictQueries(java.lang.String)
	 */
	public void evictQueries( String arg0 )
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}

	/**
	 * @see javax.naming.Referenceable#getReference()
	 */
	public Reference getReference() throws NamingException
	{
		return sessionFactory.getReference();
	}
	
	public FilterDefinition getFilterDefinition(String str)
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation" );
	}
	
	public Set getDefinedFilterNames()
	{
		throw new UnsupportedOperationException( "exception.sessionFactory.unsupportedOperation");
	}
}
