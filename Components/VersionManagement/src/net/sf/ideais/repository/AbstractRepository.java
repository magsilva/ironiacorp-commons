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

package net.sf.ideais.repository;

import java.io.File;
import java.io.IOException;

import net.sf.ideais.repository.RepositoryError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Repository interface. It define the basic information to identify
 * a repository.
 */
public abstract class AbstractRepository
{
	/**
	* Commons Logging instance.
	*/
	private static final Log log = LogFactory.getLog(AbstractRepository.class);


	private static final String DUMP_SUFFIX = ".dump";

	
	/**
	 * The repository address (an URL).
	 */
	private String location;
	
	/**
	 * Login.
	 */
	private String username;
	
	/**
	 * Password.
	 */
	private String password;
	
	
	/**
	 * Flag to set the repository as internal (managed by Wiki/RE).
	 */
	private boolean internal = false;
	
	/**
	 * Default constructor.
	 */
	public AbstractRepository()
	{
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#getLocation()
	 */
	public String getLocation()
	{
		return this.location;
	}
	
	/**
	 * @see safe.wikire.repository.RepositoryIF#setLocation(java.lang.String)
	 */
	public void setLocation( String location )
	{
		this.location = location;
		log.debug( "Setting repository " + this + " location to " + this.location );
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#getType()
	 */
	public abstract String getType();
	
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#getPassword()
	 */
	public String getPassword()
	{
		return password;
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#setPassword(java.lang.String)
	 */
	public void setPassword( String password )
	{
		this.password = password;
		// TODO: The password is not correctly masked.
		log.debug( "Setting repository " + this + " password to " + this.password.replaceAll( ".?s", "*" ) );
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#getUsername()
	 */
	public String getUsername()
	{
		return username;
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#setUsername(java.lang.String)
	 */
	public void setUsername( String username )
	{
		this.username = username;
		log.debug( "Setting repository " + this + " username to " + this.username );
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#init()
	 */
	public void init()
	{
		log.debug( "Initializating repository " + this );
		canManage();
	}
	
	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#dump()
	 */
	public File dump()
	{
		File dumpFilename = null;
		log.debug( "Dump repository's " + this + " data" );
		canManage();
		
		try {
			dumpFilename = File.createTempFile( getName(), AbstractRepository.DUMP_SUFFIX );
		} catch ( IOException e ) {
			throw new RepositoryError( "exception.repository.dump", e );
		}
	
		log.debug( "Dump repository's " + this + " data to file " + dumpFilename );
		canManage();
		
		return dumpFilename;
	
	}

	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#load(java.io.File)
	 */
	public void load( File dump )
	{
		log.debug( "Loading repository's " + this + "  data from dump file " + dump );
		canManage();
	}

	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#isInternal()
	 */
	public boolean isInternal()
	{
		return internal;
	}
	

	/* (non-Javadoc)
	 * @see safe.wikire.repository.RepositoryIF#setInternal(boolean)
	 */
	public void setInternal( boolean internal )
	{
		this.internal = internal;
	}

	/**
	 * Check if the repository can be managed by Wiki/RE.
	 * 
	 * @throws RepositoryError If the repository cannot be managed by Wiki/RE
	 */
	protected final void canManage()
	{
		if ( ! isInternal() ) {
			throw new RepositoryError( "exception.repository.cannotManage" );
		}
	}
	
	/**
	 * Check if the repository is ready for use.
	 * 
	 * @return True if ready, false otherwise.
	 * @throws Any exception for the case the implementation Repository is not ready.
	 */
	public static boolean isReady() throws Exception
	{
		return true;
	}
	
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public String getShortName()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public void setName(String name)
	{
		// TODO Auto-generated method stub
		
	}
}