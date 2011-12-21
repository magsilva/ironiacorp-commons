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

package com.ironiacorp.scm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ironiacorp.credentials.Credential;

/**
 * Repository interface. It define the basic information to identify
 * a repository.
 */
public abstract class AbstractRepository implements Repository
{
	/**
	* Commons Logging instance.
	*/
	private static final Logger log = LoggerFactory.getLogger(AbstractRepository.class);

	/**
	 * The repository address (an URL).
	 */
	private String location;
	

	/**
	 * Credential required to get access to the repository.
	 */
	private Credential credential;
	
	/**
	 * Default constructor.
	 */
	public AbstractRepository()
	{
	}
	
	@Override
	public String getLocation()
	{
		return this.location;
	}
	
	@Override
	public void setLocation( String location )
	{
		this.location = location;
		log.debug( "Setting repository " + this + " location to " + this.location );
	}

	@Override
	public Credential getCredential()
	{
		return credential;
	}

	@Override
	public void setCredential(Credential credential)
	{
		this.credential = credential;
	}
	
	
}